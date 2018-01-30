/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.integrador.envio;

import java.text.MessageFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import ec.incloud.ce.integrador.bean.AckSRI;
import ec.incloud.ce.integrador.bean.Documento;
import ec.incloud.ce.integrador.bean.Usuario;
import ec.incloud.ce.integrador.exception.IntegradorException;
import ec.incloud.ce.integrador.services.DocumentoServices;
import ec.incloud.ce.integrador.util.AckSRIUtil;
import ec.incloud.ce.integrador.util.EscenarioEnum;
import ec.incloud.ce.integrador.util.EstadoDocumentoEnum;
import ec.incloud.ce.integrador.util.Util;
import ec.incloud.ce.sri.services.RecepcionException;
import ec.incloud.ce.sri.services.RecepcionFactory;

/**
 *
 * @author Incloud Services S.A.C.
 */
public class EnvioRecepcionThread implements Runnable {

    private Documento documento;
    private DocumentoServices documentoServices;
    private final Logger log = Logger.getLogger(EnvioRecepcionThread.class);

    public EnvioRecepcionThread(Documento documento) {
        this.documento = documento;
    }
    
    public EnvioRecepcionThread(Documento documento, DocumentoServices documentoServices) {
        this.documento = documento;
        this.documentoServices = documentoServices;
    }
    
    @Override
    public void run() {
    	
    	boolean flagErrorSRI =false; //error sri - 2015/11/25
    	boolean flagEnProcesamiento =false; //CLAVE DE ACCESO EN PROCESAMIENTO
    	
    	String errorSRI_1 = "ARCHIVO NO CUMPLE ESTRUCTURA XML";
    	String errorSRI_2 = "La normalizacion del xml no fue exitosa :[0][null]";    	
    	String enProcesamiento = "CLAVE DE ACCESO EN PROCESAMIENTO";
    	
    	String mailNotificacion = Util.INSTANCE.getMailByTypeDoc(documento);    	
    	documento.getSociedad().setMailNotificacion( mailNotificacion );
        
    	if( documento.getEsquemaProc() == 0){
    		//INICIO ON-LINE
    		try {
                ec.gob.sri.comprobantes.ws.on.rec.RespuestaSolicitud solicitud = ec.incloud.ce.sri.services.on.rec.RecepcionFactory.getFactory().
                        getSriServices().validarComprobante(documento.getXml());
                
                log.info(MessageFormat.format("[Recepcion] Respuesta SRI ON-LINE Documento: {0}, Rpta: {1}", documento.getNumeroSap(), solicitud.getEstado()));
                
                if (solicitud != null && solicitud.getEstado() != null) {
                    AckSRI ack = new AckSRI();
                    ack.setEstado(solicitud.getEstado());
                    
                    if (solicitud.getComprobantes() != null && solicitud.getComprobantes().getComprobante() != null && !solicitud.getComprobantes().getComprobante().isEmpty()) {
                        ec.gob.sri.comprobantes.ws.on.rec.RespuestaSolicitud.Comprobantes lstComprobante = solicitud.getComprobantes();
                        StringBuilder msg = new StringBuilder();
                        
                        for (ec.gob.sri.comprobantes.ws.on.rec.Comprobante x : lstComprobante.getComprobante()) {
                            ec.gob.sri.comprobantes.ws.on.rec.Comprobante.Mensajes mensajes = x.getMensajes();
                            if (mensajes.getMensaje() != null) {
                                for (ec.gob.sri.comprobantes.ws.on.rec.Mensaje m : mensajes.getMensaje()) {
                                    ack.addMensaje(Integer.parseInt(m.getIdentificador()),
                                            m.getTipo(),
                                            m.getInformacionAdicional(),
                                            m.getMensaje());
                                    
                                    msg.append(m.getMensaje());
                                    msg.append("\n");
                                    msg.append((m.getInformacionAdicional() != null) ? m.getInformacionAdicional() : "");
                                    msg.append("\n");
                                    
                                    
                                    if( m.getMensaje()!=null && m.getInformacionAdicional()!=null){                                    	
                                    	
                                    	//erorr del sri
                                    	if( m.getMensaje().contains( errorSRI_1) && m.getInformacionAdicional().contains( errorSRI_2 ) )
                                    		flagErrorSRI = true;
                                    		
                                    	//en procesamiento
                                    	if( m.getMensaje().contains( enProcesamiento) )
                                    		flagEnProcesamiento = true;
                                    	
                                    }
                                    
                                }
                            }
                        }
                        this.documento.setMensaje(msg.toString());
                    }

                    log.info(MessageFormat.format("[Recepcion] Documento: {0}, Ack obtenido: {1}",
                            documento,
                            ack));
                    
                    this.documento.setEstadoSri(EstadoDocumentoEnum.NO_RECEPCIONADO.getCodigo());
                    
                    if (solicitud.getEstado().equals("RECIBIDA")) {
                        this.documento.setEstadoSri(EstadoDocumentoEnum.RECEPCIONADO.getCodigo());
                        
                        //No se genera pdf, Ni se envía correo al cliente en la versión ONLINE
//                        if (this.documentoServices.generarPdf(documento, ack)) {
//                            documento.setPdf(documento.getXml().replace(".xml", ".pdf"));
//                        }
//                        
//                        log.debug(MessageFormat.format("Enviar documento recepcionado {0} al cliente {1}", documento.getSerieCorrelativo(), documento.getMailDestino()));
//                        documentoServices.notificaAutorizadoCliente(documento);
                        
                    } else if( flagErrorSRI || flagEnProcesamiento){
                        log.error(MessageFormat.format("[Recepcion] Documento: {0} Error SRI, o en procesamiento", documento.getNumeroSap()));
                        documentoServices.actualizarEstadoEnvioErrorSriRecepcion(documento);
                        return ;
                	}else {
                        this.documento.setEscenario(EscenarioEnum.RECHAZADO.getCodigo());
                        log.warn(MessageFormat.format("Notificar documento {0} no recibido por el SRI ONLINE", documento.getSerieCorrelativo()));
                        documento.setEnvioMail(this.documentoServices.notificaRechazoAdministrador(documento) ? 1 : 0);	                        
                    }
                    this.documento.setAckSri(AckSRIUtil.getInstance().toXml(ack));
                    documentoServices.actualizarEstadoEnvioSriRecepcion(documento);
                } else {
                    log.error(MessageFormat.format("[Recepcion] Documento: {0} No tiene respuesta ONLINE", documento.getNumeroSap()));
                    documentoServices.actualizarEstadoEnvioErrorSriRecepcion(documento);
                }
            } catch (ec.incloud.ce.sri.services.on.rec.RecepcionException ex) {
                log.error("Error al enviar a recepcion offline", ex);
                try {
                    documentoServices.actualizarEstadoEnvioErrorSriRecepcion(documento);
                } catch (IntegradorException e) {
                    log.error(e);
                }
            } catch (NumberFormatException | IntegradorException e) {
                log.error(e);
            }
    		
    		//FIN ON-LINE
    	}else{
    		//---------------------------------------------------------------------------------------------
    		//INICIO OFF-LINE
    		try {
                ec.gob.sri.comprobantes.ws.rec.RespuestaSolicitud solicitud = RecepcionFactory.getFactory().
                        getSriServices().
                        validarComprobante(documento.getXml());
                
                Date fechaNoRecepcionado = new Date();//fecha no recepcionado
                
                log.info(MessageFormat.format("[Recepcion] Respuesta SRI OFFLINE Documento: {0}, Rpta: {1}", documento.getNumeroSap(), solicitud.getEstado()));
                
                if (solicitud != null && solicitud.getEstado() != null) {
                    AckSRI ack = new AckSRI();
                    ack.setEstado(solicitud.getEstado());
                    
                    if (solicitud.getComprobantes() != null && solicitud.getComprobantes().getComprobante() != null && !solicitud.getComprobantes().getComprobante().isEmpty()) {
                        ec.gob.sri.comprobantes.ws.rec.RespuestaSolicitud.Comprobantes lstComprobante = solicitud.getComprobantes();
                        StringBuilder msg = new StringBuilder();
                        
                        for (ec.gob.sri.comprobantes.ws.rec.Comprobante x : lstComprobante.getComprobante()) {
                            ec.gob.sri.comprobantes.ws.rec.Comprobante.Mensajes mensajes = x.getMensajes();
                            if (mensajes.getMensaje() != null) {
                                for (ec.gob.sri.comprobantes.ws.rec.Mensaje m : mensajes.getMensaje()) {
                                    ack.addMensaje(Integer.parseInt(m.getIdentificador()),
                                            m.getTipo(),
                                            m.getInformacionAdicional(),
                                            m.getMensaje());
                                    
                                    msg.append(m.getMensaje());
                                    msg.append("\n");
                                    msg.append((m.getInformacionAdicional() != null) ? m.getInformacionAdicional() : "");
                                    msg.append("\n");
                                    

                                    if( m.getMensaje()!=null && m.getInformacionAdicional()!=null){                                    	
                                    	
                                    	//erorr del sri
                                    	if( m.getMensaje().contains( errorSRI_1) && m.getInformacionAdicional().contains( errorSRI_2 ) )
                                    		flagErrorSRI = true;
                                    		
                                    	//en procesamiento
                                    	if( m.getMensaje().contains( enProcesamiento) )
                                    		flagEnProcesamiento = true;
                                    	
                                    }
                                }
                            }
                        }
                        this.documento.setMensaje(msg.toString());
                    }

                    log.info(MessageFormat.format("[Recepcion] Documento: {0}, Ack obtenido: {1}",
                            documento,
                            ack));
                    
                    this.documento.setEstadoSri(EstadoDocumentoEnum.NO_RECEPCIONADO.getCodigo());
                    if (solicitud.getEstado().equals("RECIBIDA") || solicitud.getEstado().equalsIgnoreCase("CLAVE ACCESO REGISTRADA")) {
                        this.documento.setEstadoSri(EstadoDocumentoEnum.RECEPCIONADO.getCodigo());
                        
                      //parametro que indica al reporte mostrar como nro autorizacion la clave de acceso
                        ack.setNumeroAutorizacion("RECEPCION");
                        //PORTAL
                        Usuario userPortal = null;
                        userPortal = documentoServices.getClavePortal(documento.getRucCliente());
//                        if(documento.getSociedad().getPortalImpl() == 1){
//                        	userPortal = documentoServices.getClavePortal(documento.getRucCliente());
//                        }
                        //PORTAL
                        if (this.documentoServices.generarPdf(documento, ack, userPortal)) {
                            documento.setPdf(documento.getXml().replace(".xml", ".pdf"));
                        }
                        
                        log.debug(MessageFormat.format("Enviar documento recepcionado {0} al cliente {1}", documento.getSerieCorrelativo(), documento.getMailDestino()));
                        documentoServices.notificaAutorizadoCliente(documento, userPortal);
                        
                    }  else if( flagErrorSRI || flagEnProcesamiento){
                        log.error(MessageFormat.format("[Recepcion] Documento: {0} Error SRI, o en procesamiento", documento.getNumeroSap()));
                        documentoServices.actualizarEstadoEnvioErrorSriRecepcion(documento);
                        return ;
                	}else {
                		Runnable hiloEnvioCorreo = new HiloCorreoByMinutes(documentoServices, documento, fechaNoRecepcionado );                		
                		new Thread(hiloEnvioCorreo).start();
                		
                        this.documento.setEscenario(EscenarioEnum.RECHAZADO.getCodigo());
                        log.warn(MessageFormat.format("Notificar documento {0} no recibido por el SRI", documento.getSerieCorrelativo()));
                        documento.setEnvioMail(this.documentoServices.notificaRechazoAdministrador(documento) ? 1 : 0);	                        
                    }
                    this.documento.setAckSri(AckSRIUtil.getInstance().toXml(ack));
                    documentoServices.actualizarEstadoEnvioSriRecepcion(documento);
                } else {
                    log.error(MessageFormat.format("[Recepcion] Documento: {0} No tiene respuesta", documento.getNumeroSap()));
                    documentoServices.actualizarEstadoEnvioErrorSriRecepcion(documento);
                }
            } catch (RecepcionException ex) {
                log.error("Error al enviar a recepcion offline", ex);
                try {
                    documentoServices.actualizarEstadoEnvioErrorSriRecepcion(documento);
                } catch (IntegradorException e) {
                    log.error(e);
                }
            } catch (NumberFormatException | IntegradorException e) {
                log.error(e);
            }
    		//FIN OFF-LINE
    	}
    }

    public DocumentoServices getDocumentoServices() {
        return documentoServices;
    }
}
