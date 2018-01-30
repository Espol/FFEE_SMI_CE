/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.integrador.on;

import java.io.File;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import ec.incloud.ce.bean.common.Referencia;
import ec.incloud.ce.bean.credito.NotaCredito;
import ec.incloud.ce.firma.exception.FirmaException;
import ec.incloud.ce.firma.services.FirmaFactory;
import ec.incloud.ce.integrador.bean.AckSRI;
import ec.incloud.ce.integrador.bean.Documento;
import ec.incloud.ce.integrador.bean.Mensaje;
import ec.incloud.ce.integrador.bean.Respuesta;
import ec.incloud.ce.integrador.bean.Sociedad;
import ec.incloud.ce.integrador.bean.Usuario;
import ec.incloud.ce.integrador.exception.IntegradorException;
import ec.incloud.ce.integrador.services.DocumentoServices;
import ec.incloud.ce.integrador.services.ServicesFactory;
import ec.incloud.ce.integrador.util.TipoDocumentoEnum;
import ec.incloud.ce.integrador.util.Util;
import ec.incloud.ce.validacion.exception.ValidacionException;
import ec.incloud.ce.validacion.services.ValidacionFactory;
import ec.incloud.ce.xml.exception.XmlException;
import ec.incloud.ce.xml.services.XmlFactory;

/**
 *
 * @author Joel Povis Ocaña
 */
class NotaCreditoEmision implements Emision<NotaCredito> {

    private static Emision<NotaCredito> instance = null;
    private NotaCredito notaCredito;
    private Sociedad sociedad;
    private String pathAbsoluteXML;
    private String[] parametrosAdicionales;

    private final ClaveAccesoGen claveGenerador = new ClaveAccesoGen();
    private final MensajeServices msgServices = new MensajeServices();
    private final TipoDocumentoEnum tipo = TipoDocumentoEnum.CREDITO;
    private final Util util = Util.INSTANCE;
    private final DocumentoServices documentoServices = ServicesFactory.getFactory().createDocumentoServices();
    private final Logger log = Logger.getLogger(this.getClass());

    private NotaCreditoEmision() {
    }

    static Emision<NotaCredito> create() {
        synchronized (NotaCreditoEmision.class) {
            if (instance == null) {
                instance = new NotaCreditoEmision();
            }
            return instance;
        }
    }

    @Override
    public Respuesta emitir(
    		
//    		NotaCredito notaCredito, 
//    		String fechaRegistro, 
//    		String usuarioSap, 
//    		String terminal, 
//    		String cliente,
//            String mail,
//            String docSap,
//            String clase,
//            String montoTotal

    		NotaCredito notaCredito, 
			Referencia referencia, 
			String usuarioSap, 
			String terminal, 
            String codInterlocutor,
			String mails,
			String emailPortal, 
			String montoTotal, 
			String userPortal, 
			String idUsuario, 
			String password,
			String obsComprobante
			
    		) {
        synchronized (NotaCreditoEmision.class) {
            Respuesta rpta = new Respuesta();
            this.notaCredito = notaCredito;
            
            String clase = referencia.getTipoDocReferencia();
            String docSap = referencia.getDocReferencia();            
            String fechaRegistro = Util.INSTANCE.getFechaRegistroTimeStamp();
            
            this.parametrosAdicionales = new String[]{clase, docSap};

            log.info(MessageFormat.format("Procesando Documento NotaCredito [establecimiento: {0}, puntoEmision: {1}, numero: {2}, docSap: {3}], "
                    + "Adicionales obtenidos [docSap: {4}, clase: {5}, montoTotal: {6}, usuarioSap: {7}, terminal: {8}, cliente: {9}, mail: {10}]",
                    notaCredito.getInfoTributaria() != null ? notaCredito.getInfoTributaria().getEstab() : "nulo",
                    notaCredito.getInfoTributaria() != null ? notaCredito.getInfoTributaria().getPtoEmi() : "nulo",
                    notaCredito.getInfoTributaria() != null ? notaCredito.getInfoTributaria().getSecuencial() : "nulo",
                    docSap,
                    docSap,
                    clase,
                    montoTotal,
                    usuarioSap,
                    terminal,
                    codInterlocutor,
                    mails));

            try {
                this.cargarConfiguracion();
                
             // se realiza la validación, el método validateFechaExtemporanea() retorna true si la fecha es válida. 
                if( !Util.INSTANCE.validateFechaExtemporanea( this.notaCredito.getInfoNotaCredito().getFechaEmision(), this.sociedad.getDiasFechaExtemporanea() ) ){                	
                	rpta.setEstado(ERROR);
            		rpta.addMensaje( new Mensaje(620, "I", "Documento con fecha extemporánea mayor a "+ this.sociedad.getDiasFechaExtemporanea() + " días.") );            		                    
                    return rpta;
            	}
                this.generarClave();
                this.generarXML();
                this.firmarXML();
                this.validarXML();

                Documento documento = documentoServices.getDocumento(this.getRuc(),
                        tipo.getCodigo(),
                        notaCredito.getInfoTributaria().getEstab(),
                        notaCredito.getInfoTributaria().getPtoEmi(),
                        notaCredito.getInfoTributaria().getSecuencial());

                if (documento != null && documento.getEstadoSri() != null) {
                    
                    if (!msgServices.getGenerar(documento.getEstadoSri())) {
                    	
	                	//caso en el que se reenvía el mismo documento con un distinto numero sap, es un error de usuario
	                	if( !referencia.getDocReferencia().equals( documento.getNumeroSap() ) ){
	                		rpta.addMensaje(new Mensaje(610,
	                                "I",
	                                MessageFormat.format(
	                                		"El comprobante {0}-{1}-{2} se encuentra Autorizado con el número sap " + documento.getNumeroSap(),
	                                        documento.getEstablecimiento(),
	                                        documento.getPuntoEmision(),
	                                        documento.getNumero()))
	                				);
	                        rpta.setEstado(ERROR);
	                	}else{
	                		rpta.addMensaje(new Mensaje(Integer.parseInt(msgServices.getCodigo(documento.getEstadoSri())),
	                                "I",
	                                MessageFormat.format(msgServices.getMensaje(documento.getEstadoSri()),
	                                        documento.getEstablecimiento(),
	                                        documento.getPuntoEmision(),
	                                        documento.getNumero())));
	                		
	                        rpta.setEstado(OK);
	                	}
	                	
	                    return rpta;
                    }else {
                        rpta.addMensaje(new Mensaje("I", MessageFormat.format(msgServices.getMensaje("0"),
                                notaCredito.getInfoTributaria().getEstab(),
                                notaCredito.getInfoTributaria().getPtoEmi(),
                                notaCredito.getInfoTributaria().getSecuencial())));
                    }
                } else {
                    rpta.addMensaje(new Mensaje("I", MessageFormat.format(msgServices.getMensaje("0"),
                            notaCredito.getInfoTributaria().getEstab(),
                            notaCredito.getInfoTributaria().getPtoEmi(),
                            notaCredito.getInfoTributaria().getSecuencial())));
                }
                
                this.guardarDocumento(fechaRegistro, usuarioSap, terminal,
                		codInterlocutor, mails, docSap, clase, montoTotal, obsComprobante, emailPortal);
                
                rpta.addMensaje( new Mensaje( this.sociedad.getEsquema()==0?100:101, "I", this.notaCredito.getInfoTributaria().getClaveAcceso() ) );
                rpta.setEstado(OK);

                log.info(MessageFormat.format("Documento NotaCredito [establecimiento: {0}, puntoEmision: {1}, numero: {2}, docSap: {3}] -> Procesado correctamente",
                        notaCredito.getInfoTributaria() != null ? notaCredito.getInfoTributaria().getEstab() : null,
                        notaCredito.getInfoTributaria() != null ? notaCredito.getInfoTributaria().getPtoEmi() : null,
                        notaCredito.getInfoTributaria() != null ? notaCredito.getInfoTributaria().getSecuencial() : null,
                        docSap));

                return rpta;
            } catch (XmlException ex) {
                rpta = new Respuesta();
                rpta.addMensaje(new Mensaje("E", ex.getMensaje()));
            } catch (FirmaException ex) {
                rpta = new Respuesta();
                rpta.addMensaje(new Mensaje("E", ex.getMensaje()));
            } catch (ValidacionException ex) {
                this.guardarDocumentoInconsistente(fechaRegistro, usuarioSap, terminal, codInterlocutor, mails, docSap, clase, ex.getMensaje());
                rpta = new Respuesta();
                rpta.addMensaje(new Mensaje("E", ex.getMensaje()));
            } catch (IntegradorException ex) {
                rpta = new Respuesta();
                rpta.addMensaje(new Mensaje("E", ex.getMessage()));
            }

            rpta.setEstado(ERROR);

            log.error(MessageFormat.format("Documento NotaCredito [establecimiento: {0}, puntoEmision: {1}, numero: {2}, docSap: {3}] -> Error [mensaje: {4}]",
                    notaCredito.getInfoTributaria() != null ? notaCredito.getInfoTributaria().getEstab() : null,
                    notaCredito.getInfoTributaria() != null ? notaCredito.getInfoTributaria().getPtoEmi() : null,
                    notaCredito.getInfoTributaria() != null ? notaCredito.getInfoTributaria().getSecuencial() : null,
                    docSap,
                    rpta.getLstMensaje() != null && !rpta.getLstMensaje().isEmpty() ? rpta.getLstMensaje().get(0).getDescripcion() : null));

            return rpta;
        }
    }

    private void validarXML() throws ValidacionException {
        try{
        ValidacionFactory.createValidacionNotaCreditoServices().validar(this.notaCredito);
        }catch (ValidacionException ex){
            log.warn("Obteniendo datos para enviar la notificacion de inconsistencia");
            
            Documento documento = new Documento();
            documento.setSociedad(this.sociedad);
            documento.setTipoDocumento(TipoDocumentoEnum.CREDITO.getCodigo());
            documento.setMensaje(ex.getMensaje());
            documento.setSerieCorrelativo( (this.notaCredito.getInfoTributaria() == null) ? "0-0-0":
                                            MessageFormat.format("{0}-{1}-{2}", 
                                            this.notaCredito.getInfoTributaria().getEstab(),
                                            this.notaCredito.getInfoTributaria().getPtoEmi(),
                                            this.notaCredito.getInfoTributaria().getSecuencial()));
            documento.setXml(this.pathAbsoluteXML);
            this.documentoServices.notificaInconsistenteAdministrador(documento);
            throw ex;
        }
    }

    private void cargarConfiguracion() throws IntegradorException {
        this.sociedad = ServicesFactory.getFactory().createSociedadServices().getSociedad(this.getRuc());
        this.sociedad.setMailNotificacion( this.sociedad.getMailCredito() );
    }

    private void generarClave() {
        String clave = this.claveGenerador.generarClaveAcceso(util.getDateFromString(this.notaCredito.getInfoNotaCredito().getFechaEmision()),
                tipo.getCodigo(),
                this.getRuc(),
                this.notaCredito.getInfoTributaria().getAmbiente(),
                this.notaCredito.getInfoTributaria().getEstab() + this.notaCredito.getInfoTributaria().getPtoEmi(),
                this.notaCredito.getInfoTributaria().getSecuencial(),
                this.notaCredito.getInfoTributaria().getSecuencial().substring(2, 8) + tipo.getCodigo(),
                this.notaCredito.getInfoTributaria().getTipoEmision());
        this.notaCredito.getInfoTributaria().setClaveAcceso(clave);
    }

    private void generarXML() throws XmlException {
        util.createDirectory(this.getXMLPath());
        this.pathAbsoluteXML = this.getXMLPath() + File.separator + this.getXMLName() + PUNTO_XML;
        XmlFactory.getNotaCreditoXmlServices().generarXml(this.notaCredito, this.pathAbsoluteXML);
    }

    private void firmarXML() throws FirmaException {
        FirmaFactory.createFirmaServices().firma(this.pathAbsoluteXML,
                this.sociedad.getPathCertificado(),
                this.sociedad.getClaveCertificado());
    }

    private void guardarDocumento(String fechaRegistro,
            String usuarioSap,
            String terminal,
            String cliente,
            String mail,
            String docSap,
            String clase,
            String montoTotal,
            String obsComprobante,
            String emailPortal) throws IntegradorException {
        Documento documento = new Documento();
        documento.getSociedad().setRuc(this.getRuc());
        documento.getSociedad().setUrl(this.sociedad.getUrl());
        documento.getSociedad().setTextoRide(this.sociedad.getTextoRide());
        documento.setTipoDocumento(tipo.getCodigo());
        documento.setEstablecimiento(this.notaCredito.getInfoTributaria().getEstab());
        documento.setPuntoEmision(this.notaCredito.getInfoTributaria().getPtoEmi());
        documento.setNumero(this.notaCredito.getInfoTributaria().getSecuencial());
        documento.setFechaEmision(util.getDateFromString(this.notaCredito.getInfoNotaCredito().getFechaEmision()));
        documento.setFechaReferencia(fechaRegistro);
        documento.setCodigoCliente(cliente);
        documento.setClaveAcceso(this.notaCredito.getInfoTributaria().getClaveAcceso());
        documento.setXml(pathAbsoluteXML);
        documento.setNumeroSap(docSap);
        documento.setUsuarioSap(usuarioSap);
        documento.setTerminal(terminal);
        documento.setMailDestino(mail);
        documento.setImporteTotal(new BigDecimal(this.notaCredito.getInfoNotaCredito().getValorModificacion()));
        documento.setClaseDocumento(clase);
        documento.setNombreCliente(this.notaCredito.getInfoNotaCredito().getRazonSocialComprador());
        documento.setObsComprobante(obsComprobante);
        documento.setEmailPortal(emailPortal);
        documento.setRucCliente(this.notaCredito.getInfoNotaCredito().getIdentificacionComprador());
        
        //Generar PDF
        AckSRI ack = new AckSRI();
        ack.setFechaAutorizacion(null);
        
        //parametro que indica al reporte mostrar como nro autorizacion en blanco
        ack.setNumeroAutorizacion("PENDIENTE");
        
      //Marcelo Moyano - generacion de PDF - CON DATOS ACCESO PORTAL.
        Usuario usuario = null;
        if(this.sociedad.getPortalImpl() == 1){
        	usuario = this.documentoServices.getClavePortal(documento.getRucCliente());
        }
        if (this.documentoServices.generarPdf(documento, ack, usuario)) {
            documento.setPdf(documento.getXml().replace(".xml", ".pdf"));
        }
        //fin Generar PDF
        
        documentoServices.guardar(documento);
    }

    
    private void guardarDocumentoInconsistente(String fechaRegistro,
            String usuarioSap,
            String terminal,
            String cliente,
            String mail,
            String docSap,
            String clase,
            String mensaje) {
        try {
                Documento documento = new Documento();
                documento.getSociedad().setRuc(this.getRuc());
                documento.setTipoDocumento(tipo.getCodigo());
                documento.setEstablecimiento(this.notaCredito.getInfoTributaria().getEstab());
                documento.setPuntoEmision(this.notaCredito.getInfoTributaria().getPtoEmi());
                documento.setNumero(this.notaCredito.getInfoTributaria().getSecuencial());
                documento.setFechaEmision(util.getDateFromString(this.notaCredito.getInfoNotaCredito().getFechaEmision()));
                documento.setFechaReferencia(fechaRegistro);
                documento.setCodigoCliente(cliente);
                documento.setClaveAcceso(this.notaCredito.getInfoTributaria().getClaveAcceso());
                documento.setXml(pathAbsoluteXML);
                documento.setNumeroSap(docSap);
                documento.setUsuarioSap(usuarioSap);
                documento.setTerminal(terminal);
                documento.setMailDestino(mail);
                documento.setImporteTotal(new BigDecimal(this.notaCredito.getInfoNotaCredito().getValorModificacion()));
                documento.setClaseDocumento(clase);
                documento.setNombreCliente(this.notaCredito.getInfoNotaCredito().getRazonSocialComprador());
                documento.setMensaje(mensaje);
                documentoServices.guardarInconsistencia(documento);
        }catch(Exception ex){
            log.error("Error al insertar inconsistencia", ex);
        }

    }

    
    public String getRuc() {
        return this.notaCredito.getInfoTributaria().getRuc();
    }

    public String getXMLPath() {
        return new StringBuilder(sociedad.getPathRoot()).
                append(File.separator).
                append(tipo.getDirectorio()).toString();
    }

    public String getXMLName() {
        return new StringBuilder(tipo.getPrefijoXml()).append("-").
                append(parametrosAdicionales[0]).append("-").
                append(parametrosAdicionales[1]).append("-").
                append(notaCredito.getInfoTributaria().getEstab()).append("-").
                append(notaCredito.getInfoTributaria().getPtoEmi()).append("-").
                append(notaCredito.getInfoTributaria().getSecuencial()).append("-").
                append(util.getStringFromDateXmlName(new Date())).toString();
    }
}
