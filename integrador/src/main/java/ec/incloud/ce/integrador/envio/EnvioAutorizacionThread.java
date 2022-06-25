/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.integrador.envio;

import java.io.File;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import ec.gob.sri.comprobantes.ws.aut.Autorizacion;
import ec.gob.sri.comprobantes.ws.aut.Mensaje;
import ec.gob.sri.comprobantes.ws.aut.RespuestaComprobante;
import ec.incloud.ce.integrador.bean.AckSRI;
import ec.incloud.ce.integrador.bean.ComprobantePortal;
import ec.incloud.ce.integrador.bean.Documento;
import ec.incloud.ce.integrador.bean.Usuario;
import ec.incloud.ce.integrador.exception.IntegradorException;
import ec.incloud.ce.integrador.services.DocumentoServices;
import ec.incloud.ce.integrador.util.AckSRIUtil;
import ec.incloud.ce.integrador.util.EscenarioEnum;
import ec.incloud.ce.integrador.util.EstadoDocumentoEnum;
import ec.incloud.ce.integrador.util.TipoDocumentoEnum;
import ec.incloud.ce.integrador.util.Util;
import ec.incloud.ce.integrador.util.XStreamAutorizacion;
import ec.incloud.ce.integrador.util.XStreamAutorizacionOnline;
import ec.incloud.ce.sri.services.AutorizacionException;
import ec.incloud.ce.sri.services.AutorizacionFactory;

/**
 *
 * @author Incloud Services S.A.C.
 */
public class EnvioAutorizacionThread implements Runnable {

    private Documento documento;
    private DocumentoServices documentoServices;
    private final Logger log = Logger.getLogger(this.getClass());
    
    private ComprobantePortal comprobantePortal = null;

    public EnvioAutorizacionThread(Documento documento) {
        this.documento = documento;
    }

    public EnvioAutorizacionThread(Documento documento, DocumentoServices documentoServices) {
        this.documento = documento;
        this.documentoServices = documentoServices;
    }

    @Override
    public void run() {

    	String mailNotificacion = Util.INSTANCE.getMailByTypeDoc(documento);    	
    	documento.getSociedad().setMailNotificacion( mailNotificacion );
    	
    	System.setProperty("javax.net.ssl.keyStore", "C:\\Program Files\\Java\\jdk1.7.0_51\\jre\\lib\\security\\cacerts");
        System.setProperty("javax.net.ssl.keyStorePassword", "changeit");
        System.setProperty("javax.net.ssl.trustStore", "C:\\Program Files\\Java\\jdk1.7.0_51\\jre\\lib\\security\\cacerts");
        System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
    	
    	CertificadosSSL.instalarCertificados();
    	
    	if( documento.getEsquemaProc() == 0){
    		//INICIO ON-LINE
    		
            try {
            	
            	ec.gob.sri.comprobantes.ws.on.aut.RespuestaComprobante respuesta = ec.incloud.ce.sri.services.on.aut.AutorizacionFactory.getFactory().getSriServices().
                        autorizarComprobante(documento.getClaveAcceso());
            	
                log.info(MessageFormat.format("[Autorizacion] Respuesta SRI Documento: {0}, Rpta: {1}",
                        documento.getNumeroSap(), respuesta));
                
                if (respuesta != null && respuesta.getAutorizaciones() != null && !respuesta.getAutorizaciones().getAutorizacion().isEmpty()) {
                    StringBuilder msg = new StringBuilder();
                    AckSRI ack = new AckSRI();
                    ack.setClaveAccesoConsultada(respuesta.getClaveAccesoConsultada());
                    
                    Date fechaAutorizacion = null;
                    
                    int i=0;
                    for (ec.gob.sri.comprobantes.ws.on.aut.Autorizacion x : respuesta.getAutorizaciones().getAutorizacion()) {
                        log.info("[Autorizacion] Estado de Autorizacion: " + MessageFormat.format("Documento: {0}, Estado: {1}", documento.getNumeroSap(), x.getEstado()));
                        
                        fechaAutorizacion = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").parse(x.getFechaAutorizacion()));
                        
                        ack.setEstado(x.getEstado());
                        ack.setNumeroAutorizacion(x.getNumeroAutorizacion());
//                        ack.setFechaAutorizacion(Util.INSTANCE.getStringFromDateWithTime(x.getFechaAutorizacion().toGregorianCalendar().getTime()));

                        String fechaAutorizacionTxt = Util.INSTANCE.getStringFromDateWithTime((new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").parse(x.getFechaAutorizacion())));
                        ack.setFechaAutorizacion(fechaAutorizacionTxt);
                    	respuesta.getAutorizaciones().getAutorizacion().get(i++).setFechaAutorizacion(fechaAutorizacionTxt);
                    	
                        if (x.getMensajes() != null) {
                            for (ec.gob.sri.comprobantes.ws.on.aut.Mensaje m : x.getMensajes().getMensaje()) {
                                ack.addMensaje(Integer.parseInt(m.getIdentificador()),
                                        m.getTipo(),
                                        m.getInformacionAdicional(),
                                        m.getMensaje());

                                msg.append(m.getMensaje());
                                msg.append("\n");
                                msg.append((m.getInformacionAdicional() != null)
                                        ? m.getInformacionAdicional() : "");
                                msg.append("\n");
                            }
                        }
                    }
                    
                    log.info(MessageFormat.format("[Autorizacion] Documento: {0}, Ack obtenido: {1}",
                            documento,
                            ack));
                    
                    documento.setMensaje(msg.toString());
                    
                    if (ack.getEstado() != null) {
                        if (ack.getEstado().equals("AUTORIZADO")) {
                            documento.setEscenario(EscenarioEnum.AUTORIZADO.getCodigo());
                            documento.setNroAutorizacion(ack.getNumeroAutorizacion());
                            documento.setFechaAutorizacion(ack.getFechaAutorizacion());
                            documento.setEstadoSri(EstadoDocumentoEnum.AUTORIZADO.getCodigo());
                            
                            //obtener data del xml para el portal
                            comprobantePortal = Util.INSTANCE.getDataPortal(documento.getTipoDocumento(), documento.getSubtipoDoc(),  documento.getPathXml() );
                            
                            
                            // portal
                            Usuario usuario = null;
                            if(comprobantePortal != null && documento.getSociedad().getPortalImpl() == 1){
                            	log.info(MessageFormat.format("Guardado Portal {0}", comprobantePortal.getRucCliente()));
                            	documentoServices.guardarPersonaPortal(documento, comprobantePortal);
                            	documentoServices.guardarUsuarioPortal(documento, comprobantePortal);
                            }
                            usuario = this.documentoServices.getClavePortal(documento.getRucCliente());
                            
                            //se genera el pdf con la fecha de autorización
                            if (this.documentoServices.generarPdf(documento, ack, usuario)) {
                                documento.setPdf(documento.getXml().replace(".xml", ".pdf"));
                            }
                            
                            //actualizar xml, se añade la rpta del sri
                            XStreamAutorizacionOnline.getInstance().generarXml(respuesta, documento.getXml() );
                            
                            log.debug(MessageFormat.format("Enviar documento autorizado {0} al cliente {1}", documento.getSerieCorrelativo(), documento.getMailDestino()));
                            documento.setEnvioMail(documentoServices.notificaAutorizadoClienteOnline(documento, usuario) ? 1 : 0);
                            
                        } else if (ack.getEstado().equals("EN PROCESO")) {
                            log.error(MessageFormat.format("[Recepcion] Documento: {0} Está en procesamiento", documento.getNumeroSap()));
                            documentoServices.actualizarEstadoEnvioErrorSriAutorizacion(documento);
                        }else {
                            Util util = Util.INSTANCE;
                            
                            StringBuilder p = new StringBuilder();
                            p.append( (new File(documento.getXml())).getParent());
                            p.append(File.separator);
                            p.append(TipoDocumentoEnum.getTipo(documento.getTipoDocumento()).getDescripcion());
                            p.append("-");
                            p.append(documento.getClaseDocumento());
                            p.append("-");
                            p.append(documento.getNumeroSap());
                            p.append("-");
                            p.append(documento.getSerieCorrelativo());
                            p.append("-");
                            p.append(util.getStringFromDateXmlName(new Date()));
                            p.append(".xml");
                            
                            XStreamAutorizacionOnline.getInstance().generarXml(respuesta, documento.getXml() );
                            
                            documento.setEscenario(EscenarioEnum.RECHAZADO.getCodigo());
                            documento.setEstadoSri(EstadoDocumentoEnum.RECHAZADO.getCodigo());
                            log.debug(MessageFormat.format("Enviar notificacion del documento {0} al administrador", documento.getSerieCorrelativo()));
                            documento.setEnvioMail(documentoServices.notificaRechazoAdministrador(documento) ? 1 : 0);                            
                        }
                        
                        documento.setAckSri( AckSRIUtil.getInstance().toXml(ack) );
                        documentoServices.actualizarEstadoEnvioSriAutorizacion(this.documento);
                    }
                } else {
                    log.error(MessageFormat.format("[Autorizacion] Documento: {0} No tiene respuesta", documento.getNumeroSap()));
                    documentoServices.actualizarEstadoEnvioErrorSriAutorizacion(documento);
                }
            } catch (ec.incloud.ce.sri.services.on.aut.AutorizacionException e) {
                log.error("Error al enviar a autorizacion ", e);
                try {
                    documentoServices.actualizarEstadoEnvioErrorSriAutorizacion(documento);
                } catch (IntegradorException ex) {
                    log.error(ex);
                }
            } catch (Exception e) {
                log.error(e);
            }
    		
    		//FIN ON-LINE
    	}else{
    		
    		//INICIO OFF-LINE
            try {
                RespuestaComprobante respuesta = AutorizacionFactory.getFactory().getSriServices().
                        autorizarComprobante(documento.getClaveAcceso());

                log.info(MessageFormat.format("[Autorizacion] Respuesta SRI Documento: {0}, Rpta: {1}",
                        documento.getNumeroSap(), respuesta));
                
                if (respuesta != null && respuesta.getAutorizaciones() != null && !respuesta.getAutorizaciones().getAutorizacion().isEmpty()) {
                    StringBuilder msg = new StringBuilder();
                    AckSRI ack = new AckSRI();
                    ack.setClaveAccesoConsultada(respuesta.getClaveAccesoConsultada());
                    
                    Date fechaAutorizacion = null;
                    
                    int i=0;
                    for (Autorizacion x : respuesta.getAutorizaciones().getAutorizacion()) {
                        log.info("[Autorizacion] Estado de Autorizacion: " + MessageFormat.format("Documento: {0}, Estado: {1}", documento.getNumeroSap(), x.getEstado()));
                        
                        fechaAutorizacion = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").parse(x.getFechaAutorizacion()));
                        
                        ack.setEstado(x.getEstado());
                        ack.setNumeroAutorizacion(x.getNumeroAutorizacion());
//                        ack.setFechaAutorizacion(Util.INSTANCE.getStringFromDateWithTime(x.getFechaAutorizacion().toGregorianCalendar().getTime()));

                        String fechaAutorizacionTxt = Util.INSTANCE.getStringFromDateWithTime((new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").parse(x.getFechaAutorizacion())));
                        ack.setFechaAutorizacion(fechaAutorizacionTxt);
                    	respuesta.getAutorizaciones().getAutorizacion().get(i++).setFechaAutorizacion(fechaAutorizacionTxt);
                    	
                        if (x.getMensajes() != null) {
                            for (Mensaje m : x.getMensajes().getMensaje()) {
                                ack.addMensaje(Integer.parseInt(m.getIdentificador()),
                                        m.getTipo(),
                                        m.getInformacionAdicional(),
                                        m.getMensaje());

                                msg.append(m.getMensaje());
                                msg.append("\n");
                                msg.append((m.getInformacionAdicional() != null)
                                        ? m.getInformacionAdicional() : "");
                                msg.append("\n");
                            }
                        }
                    }
                    
                    log.info(MessageFormat.format("[Autorizacion] Documento: {0}, Ack obtenido: {1}",
                            documento,
                            ack));
                    
                    documento.setMensaje(msg.toString());
                    
                    if (ack.getEstado() != null) {
                        if (ack.getEstado().equals("AUTORIZADO")) {
                            documento.setEscenario(EscenarioEnum.AUTORIZADO.getCodigo());
                            documento.setNroAutorizacion(ack.getNumeroAutorizacion());
                            documento.setFechaAutorizacion(ack.getFechaAutorizacion());
                            documento.setEstadoSri(EstadoDocumentoEnum.AUTORIZADO.getCodigo());
                            documento.setEstadoNotifRechazado(1);
                            
                          //obtener data del xml para el portal
                          comprobantePortal = Util.INSTANCE.getDataPortal(documento.getTipoDocumento(), documento.getSubtipoDoc(),  documento.getPathXml() );
                          
                            
	                        //portal
                          Usuario usuario = null;
	                        if(comprobantePortal != null && documento.getSociedad().getPortalImpl() == 1){
	                        	log.info(MessageFormat.format("Guardado Portal {0}", comprobantePortal.getRucCliente()));
	                         	documentoServices.guardarPersonaPortal(documento, comprobantePortal);
	                          	documentoServices.guardarUsuarioPortal(documento, comprobantePortal);
	                        }
	                        usuario = this.documentoServices.getClavePortal(documento.getRucCliente());
	                        
                            //se genera el pdf con la fecha de autorización
                            if (this.documentoServices.generarPdf(documento, ack, usuario)) {
                                documento.setPdf(documento.getXml().replace(".xml", ".pdf"));
                            }
                            
                            //actualizar xml, se añade la rpta del sri
                            XStreamAutorizacion.getInstance().generarXml(respuesta, documento.getXml() );
                              
                            log.debug(MessageFormat.format("Enviar documento autorizado {0} al cliente {1}", documento.getSerieCorrelativo(), documento.getMailDestino()));                            
                            documento.setEnvioMail( documentoServices.notificaAutorizadoConfirmacionCliente(documento,usuario) ? 1 : 0);

                        } else if (ack.getEstado().equals("EN PROCESO")) {
                            log.error(MessageFormat.format("[Recepcion] Documento: {0} Está en procesamiento", documento.getNumeroSap()));
                            documentoServices.actualizarEstadoEnvioErrorSriAutorizacion(documento);
                        } else {
                            Util util = Util.INSTANCE;                            
                            
                            StringBuilder p = new StringBuilder();
                            p.append( (new File(documento.getXml())).getParent());
                            p.append(File.separator);
                            p.append(TipoDocumentoEnum.getTipo(documento.getTipoDocumento()).getDescripcion());
                            p.append("-");
                            p.append(documento.getClaseDocumento());
                            p.append("-");
                            p.append(documento.getNumeroSap());
                            p.append("-");
                            p.append(documento.getSerieCorrelativo());
                            p.append("-");
                            p.append(util.getStringFromDateXmlName(new Date()));
                            p.append(".xml");
                            
                            //sgte línea añadida
                            XStreamAutorizacion.getInstance().generarXml(respuesta, documento.getXml() );
//                            documento.setXml(p.toString());
                            documento.setEscenario(EscenarioEnum.RECHAZADO.getCodigo());
                            documento.setEstadoSri(EstadoDocumentoEnum.RECHAZADO.getCodigo());
                            log.debug(MessageFormat.format("Enviar notificacion del documento {0} al administrador", documento.getSerieCorrelativo()));
                            documento.setEnvioMail(documentoServices.notificaRechazoAdministrador(documento) ? 1 : 0);
                            
                    		Runnable hiloEnvioCorreo = new HiloCorreoByMinutes(documentoServices, documento, fechaAutorizacion );                		
                    		new Thread(hiloEnvioCorreo).start();
                    		
                            documentoServices.notificaNoAutorizadoCliente(documento);
                        }
                        
                        documento.setAckSri( AckSRIUtil.getInstance().toXml(ack) );
                        documentoServices.actualizarEstadoEnvioSriAutorizacion(this.documento);
                    }
                } else {
                    log.error(MessageFormat.format("[Autorizacion] Documento: {0} No tiene respuesta", documento.getNumeroSap()));
                    documentoServices.actualizarEstadoEnvioErrorSriAutorizacion(documento);
                }
            } catch (AutorizacionException e) {
                log.error("Error al enviar a autorizacion ", e);
                try {
                    documentoServices.actualizarEstadoEnvioErrorSriAutorizacion(documento);
                } catch (IntegradorException ex) {
                    log.error(ex);
                }
            } catch (Exception e) {
            	log.error(e);
            	try {
                    documentoServices.actualizarEstadoEnvioErrorSriAutorizacion(documento);
                } catch (IntegradorException ex) {
                    log.error(ex);
                }
            }
            
    		//FIN OFF-LINE
    	}
    }

    public void setDocumentoServices(DocumentoServices documentoServices) {
        this.documentoServices = documentoServices;
    }

}
