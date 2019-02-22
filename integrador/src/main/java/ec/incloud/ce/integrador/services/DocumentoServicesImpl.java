/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.integrador.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import ec.incloud.ce.bean.common.TotalImpuesto;
import ec.incloud.ce.bean.credito.NotaCredito;
import ec.incloud.ce.bean.debito.NotaDebito;
import ec.incloud.ce.bean.factura.Factura;
import ec.incloud.ce.bean.facturaExportacion.FacturaExportacion;
import ec.incloud.ce.bean.facturaReembolso.FacturaReembolso;
//import com.microsoft.sqlserver.jdbc.SQLServerException;
import ec.incloud.ce.integrador.bean.AckSRI;
import ec.incloud.ce.integrador.bean.ComprobantePortal;
import ec.incloud.ce.integrador.bean.Documento;
import ec.incloud.ce.integrador.bean.Impuesto;
import ec.incloud.ce.integrador.bean.MailSetting;
import ec.incloud.ce.integrador.bean.Usuario;
import ec.incloud.ce.integrador.exception.IntegradorException;
import ec.incloud.ce.integrador.mapper.DocumentoMapper;
import ec.incloud.ce.integrador.mapper.ImpuestoMapper;
import ec.incloud.ce.integrador.mapper.SociedadMapper;
import ec.incloud.ce.integrador.util.DestinoDocumentoEnum;
import ec.incloud.ce.integrador.util.EstadoEnvioEnum;
import ec.incloud.ce.integrador.util.MailSettingUtil;
import ec.incloud.ce.integrador.util.SqlConfig;
import ec.incloud.ce.integrador.util.TipoDocumentoEnum;
import ec.incloud.ce.integrador.util.Util;
import ec.incloud.ce.notificacion.EnviarNotificacion;
import ec.incloud.ce.notificacion.MailProperties;
import ec.incloud.ce.notificacion.Notificacion;
import ec.incloud.ce.pdf.services.PdfFactory;
import ec.incloud.ce.pdf.services.PdfServices;
import ec.incloud.ce.xml.exception.XmlException;
import ec.incloud.ce.xml.services.XmlFactory;
import ec.incloud.ce.xml.services.XmlServices;

/**
 *
 * @author Incloud Services S.A.C.
 */
class DocumentoServicesImpl implements DocumentoServices {
	
    private static DocumentoServices instance = null;
    private final Logger logger = Logger.getLogger(this.getClass());
    private final PropertiesServices confServices = ServicesFactory.getFactory().createPropertiesServices();
    private static final String EXTENSION_PDF = ".pdf";
    
    private Impuesto impuesto = null;

    private DocumentoServicesImpl() {
    }
    
    public static DocumentoServices create() {
        synchronized (DocumentoServicesImpl.class) {
            if (instance == null) {
                instance = new DocumentoServicesImpl();
            }
            return instance;
        }
    }
    
    

    @Override
	public Usuario getClavePortal(String rucCliente) throws IntegradorException {
		// TODO Auto-generated method stub
    	try {
    		return SqlConfig.getSqlSessionManager().getMapper(DocumentoMapper.class).getClavePortal(rucCliente);
		} catch (Exception e) {
//			// TODO: handle exception
	         return null;
		}
	}

	@Override
    public void guardar(Documento documento) throws IntegradorException {
        try {
            SqlConfig.getSqlSessionManager().getMapper(DocumentoMapper.class).
                    insertarDocumento(documento);
        } catch (Exception e) {
            logger.error(e);
            throw this.getErrorCode(e);
        }
    }
    
    @Override
    public void guardarInconsistencia(Documento documento) throws IntegradorException {
        try {
            SqlConfig.getSqlSessionManager().getMapper(DocumentoMapper.class).
                    insertarDocumentoInconsistencia(documento);
        } catch (Exception e) {
            logger.error(e);
            throw this.getErrorCode(e);
        }
    }

    @Override
    public Documento getDocumentoById(String rucEmisor, String tipoDoc, String establecimiento, String puntoEmision, String numero, int secuencia) {
        return SqlConfig.getSqlSessionManager().getMapper(DocumentoMapper.class)
                .getDocumentoById(rucEmisor,
                        tipoDoc,
                        establecimiento,
                        puntoEmision,
                        numero,
                        secuencia);
    }

    @Override
    public Documento getDocumento(String rucEmisor, String tipoDoc, String establecimiento, String puntoEmision, String numero) {
        return SqlConfig.getSqlSessionManager().getMapper(DocumentoMapper.class)
                .getDocumento(rucEmisor,
                        tipoDoc,
                        establecimiento,
                        puntoEmision,
                        numero);
    }
    
    private void getImpuesto(String codigo, String codPorcentaje){
    	impuesto = SqlConfig.getSqlSessionManager().getMapper(ImpuestoMapper.class).getImpuesto(Integer.parseInt(codigo), Integer.parseInt(codPorcentaje));
    }
    
    private String getIvaDinamico(){
    	if(impuesto != null){
    		return "" + impuesto.getPorcentaje();
    	}
    	return "";
    }
    
    private void getImpuestoIvaByDocumento(Documento documento, XmlServices xmlServices){
    	
    	Object comprobante;
    	
    	String codigo = "";
    	String codPorcentaje="";
    	
		try {
			comprobante = xmlServices.getComprobanteDePathArchivo(documento.getPathXml());
			switch (documento.getTipoDocumento()) {
	    	case "01":
	        	if( documento.getSubtipoDoc()!=2 && documento.getSubtipoDoc()!=3){
	                Factura factura = (Factura) comprobante;
		    		for(TotalImpuesto imp : factura.getInfoFactura().getTotalConImpuestos()){
		    			if(imp.getCodigo().equalsIgnoreCase("2")){
		    				codigo = imp.getCodigo();
		    				codPorcentaje = imp.getCodigoPorcentaje();
		    			}
		    		}
	                
	                getImpuesto(codigo,codPorcentaje);
	        	}else if(documento.getSubtipoDoc()==2){
	                FacturaExportacion exportacion = (FacturaExportacion) comprobante;
	                
	                for(TotalImpuesto imp : exportacion.getInfoFactura().getTotalConImpuestos()){
		    			if(imp.getCodigo().equalsIgnoreCase("2")){
		    				codigo = imp.getCodigo();
		    				codPorcentaje = imp.getCodigoPorcentaje();
		    			}
		    		}
	                
	                getImpuesto(codigo,codPorcentaje);
	        	}else if(documento.getSubtipoDoc()==3){
	                FacturaReembolso reembolso = (FacturaReembolso) comprobante;
	                
	                for(TotalImpuesto imp : reembolso.getInfoFactura().getTotalConImpuestos()){
		    			if(imp.getCodigo().equalsIgnoreCase("2")){
		    				codigo = imp.getCodigo();
		    				codPorcentaje = imp.getCodigoPorcentaje();
		    			}
		    		}
	                
	                getImpuesto(codigo,codPorcentaje);
	        	}
	            break;
	        case "04":
	            NotaCredito credito = (NotaCredito) comprobante;
	            for(TotalImpuesto imp : credito.getInfoNotaCredito().getTotalConImpuestos()){
	    			if(imp.getCodigo().equalsIgnoreCase("2")){
	    				codigo = imp.getCodigo();
	    				codPorcentaje = imp.getCodigoPorcentaje();
	    			}
	    		}
	            getImpuesto(codigo,codPorcentaje);
	            break;
	        case "05":
	            NotaDebito debito = (NotaDebito) comprobante;
	            for(ec.incloud.ce.bean.common.Impuesto imp : debito.getInfoNotaDebito().getImpuestos()){
	    			if(imp.getCodigo().equalsIgnoreCase("2")){
	    				codigo = imp.getCodigo();
	    				codPorcentaje = imp.getCodigoPorcentaje();
	    			}
	    		}
	            getImpuesto(codigo,codPorcentaje);
	            break;
	        default:
	            break;
		}
		} catch (XmlException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Override
    public boolean generarPdf(Documento documento, AckSRI ackAutorizacion,Usuario usuario) throws IntegradorException {
        XmlServices xmlServices = null;
        PdfServices pdfServices = null;
        String ruc = documento.getSociedad().getRuc();
        
        switch (documento.getTipoDocumento()) {
            case "01":
            	if( documento.getSubtipoDoc()!=2 && documento.getSubtipoDoc()!=3){
                    xmlServices = XmlFactory.getFacturaXmlServices();
                    pdfServices = PdfFactory.createPdfFacturaServices(  );            		
            	}else if(documento.getSubtipoDoc()==2){
                    xmlServices = XmlFactory.getFacturaExportacionXmlServices();
                    pdfServices = PdfFactory.createPdfFacturaExportacionServices(  );             		
            	}else if(documento.getSubtipoDoc()==3){
                    xmlServices = XmlFactory.getFacturaReembolsoXmlServices();
                    pdfServices = PdfFactory.createPdfFacturaReembolsoServices(  );   
            	}
                break;
            case "04":
                xmlServices = XmlFactory.getNotaCreditoXmlServices();
                pdfServices = PdfFactory.createPdfNotaCreditoServices(  );
                break;
            case "05":
                xmlServices = XmlFactory.getNotaDebitoXmlServices();
                pdfServices = PdfFactory.createPdfNotaDebitoServices(  );
                break;
            case "06":
                xmlServices = XmlFactory.getGuiaRemisionXmlServices();
                pdfServices = PdfFactory.createPdfGuiaRemisionServices();
                break;
            case "07":
                xmlServices = XmlFactory.getRetencionServices();
                pdfServices = PdfFactory.createPdfComprobanteRetencionServices();
                break;
            default:
                break;
        }
        if (xmlServices != null && pdfServices != null) {
            try {
//                Sociedad sociedad = ServicesFactory.getFactory().createSociedadServices().getParamPDFSociedad( 
//		                				documento.getSociedad().getIdSociedad() 
//		                			);
                
//                String [] sociedad = { ruc, documento.getSociedad().getUrl() };
            	
            	this.getImpuestoIvaByDocumento(documento, xmlServices);//{+ MML
                
                String[ ] sociedad = new String[5];
                sociedad[0] = ruc;
                sociedad[1] = documento.getSociedad().getUrl();
                sociedad[2] = documento.getSociedad().getTextoRide();
                sociedad[3] = "";
                sociedad[4] = "";
                if(usuario != null && usuario.getClave().equalsIgnoreCase(usuario.getClaveInicial())) {
                	sociedad[3] = usuario.getUsername();
                    sociedad[4] = usuario.getUsuarioSap();
                }
                
                String [] documentoParam = { documento.getObsComprobante()==null?"":documento.getObsComprobante() };
            	
                pdfServices.generarPdf(xmlServices.getComprobanteDePathArchivo(documento.getPathXml()),
                        FilenameUtils.removeExtension(documento.getXml()) + EXTENSION_PDF,
                        ackAutorizacion.getNumeroAutorizacion(),
                        ackAutorizacion.getFechaAutorizacion(),
                        sociedad,
                        documentoParam,
                        this.getIvaDinamico()
                        );
                return true;
            } catch (Exception ex) {
                logger.error("Error al generar PDF",ex);
            }
        }
        return false;
    }

    @Override
    public void actualizarIntentoEnvioRecepcion(Documento documento) throws IntegradorException {
        try {
            SqlConfig.getSqlSessionManager().getMapper(DocumentoMapper.class).
                    actualizarIntentoEnvioRecepcion(documento);
        } catch (Exception e) {
            logger.error(e);
            throw this.getErrorCode(e);
        }
    }

    @Override
    public void actualizarIntentoEnvioAutorizacion(Documento documento) throws IntegradorException {
        try {
            SqlConfig.getSqlSessionManager().getMapper(DocumentoMapper.class).
                    actualizarIntentoEnvioAutorizacion(documento);
        } catch (Exception e) {
            logger.error(e);
            throw this.getErrorCode(e);
        }
    }

    @Override
    public void actualizarIntentoEnvioSap(Documento documento) throws IntegradorException {
        try {
            SqlConfig.getSqlSessionManager().getMapper(DocumentoMapper.class).
                    actualizarIntentoEnvio(documento, DestinoDocumentoEnum.SAP.getDestino());
        } catch (Exception e) {
            logger.error(e);
            throw this.getErrorCode(e);
        }
    }

    @Override
    public void actualizarEstadoEnvioSriRecepcion(Documento documento) throws IntegradorException {
        try {
            SqlConfig.getSqlSessionManager().getMapper(DocumentoMapper.class).
                    actualizarAckRecepcion(documento);
        } catch (Exception e) {
            logger.error(e);
            throw this.getErrorCode(e);
        }
    }

    @Override
    public void actualizarEstadoEnvioSriAutorizacion(Documento documento) throws IntegradorException {
        try {
            SqlConfig.getSqlSessionManager().getMapper(DocumentoMapper.class).
                    actualizarAckAutorizacion(documento);
        } catch (Exception e) {
            logger.error(e);
            throw this.getErrorCode(e);
        }
    }

    @Override
    public void actualizarEstadoEnvioSap(Documento documento) throws IntegradorException {
        try {
            SqlConfig.getSqlSessionManager().getMapper(DocumentoMapper.class).
                    actualizarEstadoEnvioSap(documento);
        } catch (Exception e) {
            logger.error(e);
            throw this.getErrorCode(e);
        }
    }

    @Override
    public void actualizarEstadoEnvioErrorSriRecepcion(Documento documento) throws IntegradorException {
        try {
            SqlConfig.getSqlSessionManager().getMapper(DocumentoMapper.class).
                    actualizarEstadoEnvioErrorRecepcion(documento);
        } catch (Exception e) {
            logger.error(e);
            throw this.getErrorCode(e);
        }
    }

    @Override
    public void actualizarEstadoEnvioErrorSriAutorizacion(Documento documento) throws IntegradorException {
        try {
            SqlConfig.getSqlSessionManager().getMapper(DocumentoMapper.class).
                    actualizarEstadoEnvioErrorAutorizacion(documento);
        } catch (Exception e) {
            logger.error(e);
            throw this.getErrorCode(e);
        }
    }

    @Override
    public void actualizarEstadoEnvioErrorSap(Documento documento) throws IntegradorException {
        try {
            SqlConfig.getSqlSessionManager().getMapper(DocumentoMapper.class).
                    actualizarEstadoEnvioError(documento, DestinoDocumentoEnum.SAP.getDestino());
        } catch (Exception e) {
            logger.error(e);
            throw this.getErrorCode(e);
        }
    }

    @Override
    public void actualizarFechaNoAutorizado(Documento documento) throws IntegradorException {
        try {
        	
        	// actualizar fecha no autorizado, agregar campo en la tabla documento
            SqlConfig.getSqlSessionManager().getMapper(DocumentoMapper.class).
                    actualizarEstadoEnvioError(documento, DestinoDocumentoEnum.SAP.getDestino());
        } catch (Exception e) {
            logger.error(e);
            throw this.getErrorCode(e);
        }
    }
    
    @Override
    public List<Documento> getlstDocumentoPorEnviarSriRecepcion() {
        return SqlConfig.getSqlSessionManager().getMapper(DocumentoMapper.class).
                getlstDocumentoPorEnviar(DestinoDocumentoEnum.SRI_RECEPCION.getDestino(),
                        EstadoEnvioEnum.PENDIENTE.getCodigo(),
                        confServices.getPoolMaxEnvioSri());
    }

    @Override
    public List<Documento> getlstDocumentoPorReenviarSriRecepcion() {
        return SqlConfig.getSqlSessionManager().getMapper(DocumentoMapper.class).
                getlstDocumentoPorEnviar(DestinoDocumentoEnum.SRI_RECEPCION.getDestino(),
                        EstadoEnvioEnum.ERROR.getCodigo(),
                        confServices.getPoolMaxEnvioSri());
    }

    @Override
    public List<Documento> getlstDocumentoPorEnviarSriAutorizacion() {
        return SqlConfig.getSqlSessionManager().getMapper(DocumentoMapper.class).
                getlstDocumentoPorEnviar(DestinoDocumentoEnum.SRI_AUTORIZACION.getDestino(),
                        EstadoEnvioEnum.PENDIENTE.getCodigo(),
                        confServices.getPoolMaxEnvioSri());
    }

    @Override
    public List<Documento> getlstDocumentoPorReenviarSriAutorizacion() {
        return SqlConfig.getSqlSessionManager().getMapper(DocumentoMapper.class).
                getlstDocumentoPorEnviar(DestinoDocumentoEnum.SRI_AUTORIZACION.getDestino(),
                        EstadoEnvioEnum.ERROR.getCodigo(),
                        confServices.getPoolMaxEnvioSri());
    }

    @Override
    public List<Documento> getlstDocumentoPorEnviarSap() {
        return SqlConfig.getSqlSessionManager().getMapper(DocumentoMapper.class).
                getlstDocumentoPorEnviar(DestinoDocumentoEnum.SAP.getDestino(),
                        EstadoEnvioEnum.PENDIENTE.getCodigo(),
                        confServices.getPoolMaxEnvioSap());
    }

    @Override
    public List<Documento> getlstDocumentoRechazadoSriAutorizacion() {
        return SqlConfig.getSqlSessionManager().getMapper(DocumentoMapper.class).
        		getlstDocumentoRechazadoSriAutorizacion(
                        confServices.getPoolMaxRechazadoAutorizacion()
                );
    }
    
    

    private IntegradorException getErrorCode(Exception e) {
        final Throwable cause = e.getCause();
        if (cause instanceof SQLException) {
        	SQLException ex = (SQLException) cause;
            return new IntegradorException(ex.getErrorCode() + "", ex.getMessage());
        }
        return new IntegradorException("99999", e.getMessage());
    }

    @Override
    public boolean notificaNoAutorizadoCliente(Documento documento) {
        Notificacion notifica = EnviarNotificacion.NO_AUTORIZADO;
        MailSetting ms = MailSettingUtil.getInstance()
                        .toObject(documento.getSociedad().getMailSettings());
       
        List<String> adjunto = null;
        
        String sociedad [] = { documento.getSociedad().getRuc(), documento.getSociedad().getRazonSocial() };
        // arregle sociedad= ruc_sociedad, razon social sociedad, userName accesos portal, clave portal
        
        return notifica.enviarCorreo(
        							sociedad,
        							this.getMailProperties(ms), 
                                    TipoDocumentoEnum.getTipo(documento.getTipoDocumento()).getDescripcion(), 
                                    documento.getSerieCorrelativo(), 
                                    documento.getNumeroSap(), 
                                    Util.getCorreosValidos(documento.getMailDestino()), 
                                    null, 
                                    null, 
                                    adjunto);
    }

    @Override
    public boolean notificaAutorizadoConfirmacionCliente(Documento documento, Usuario usuario) {
        Notificacion notifica = EnviarNotificacion.AUTORIZADO_CONFIRMACION;
        MailSetting ms = MailSettingUtil.getInstance()
                        .toObject(documento.getSociedad().getMailSettings());
       
        List<String> adjunto = new ArrayList<>();
        adjunto.add(documento.getXml());
        adjunto.add(documento.getPdf());
        
        String[ ] sociedad = new String[5];
        sociedad[0] = documento.getSociedad().getRuc();
        sociedad[1] = documento.getSociedad().getRazonSocial();
        sociedad[2] = "";
        sociedad[3] = "";
        sociedad[4] = "none";
        if(usuario != null && usuario.getClave().equalsIgnoreCase(usuario.getClaveInicial())) {
        	sociedad[2] = usuario.getUsername();
            sociedad[3] = usuario.getUsuarioSap();
            sociedad[4] = "block";
        }
        
        
        return notifica.enviarCorreo(
        							sociedad,
									this.getMailProperties(ms), 
                                    TipoDocumentoEnum.getTipo(documento.getTipoDocumento()).getDescripcion(), 
                                    documento.getSerieCorrelativo(), 
                                    documento.getNumeroSap(), 
                                    Util.getCorreosValidos(documento.getMailDestino()), 
                                    null, 
                                    null, 
                                    adjunto);
    }
    
    @Override
    public boolean notificaAutorizadoClienteOnline(Documento documento, Usuario usuario) {
        Notificacion notifica = EnviarNotificacion.AUTORIZADO_ONLINE;
        MailSetting ms = MailSettingUtil.getInstance()
                        .toObject(documento.getSociedad().getMailSettings());
       
        List<String> adjunto = new ArrayList();
        adjunto.add(documento.getXml());
        adjunto.add(documento.getPdf());
        
        String[ ] sociedad = new String[5];
        sociedad[0] = documento.getSociedad().getRuc();
        sociedad[1] = documento.getSociedad().getRazonSocial();
        sociedad[2] = "";
        sociedad[3] = "";
        sociedad[4] = "none";
        if(usuario != null && usuario.getClave().equalsIgnoreCase(usuario.getClaveInicial())) {
        	sociedad[2] = usuario.getUsername();
            sociedad[3] = usuario.getUsuarioSap();
            sociedad[4] = "block";
        }
        
        return notifica.enviarCorreo(
        						sociedad,
									this.getMailProperties(ms), 
                                    TipoDocumentoEnum.getTipo(documento.getTipoDocumento()).getDescripcion(), 
                                    documento.getSerieCorrelativo(), 
                                    documento.getNumeroSap(), 
                                    Util.getCorreosValidos(documento.getMailDestino()), 
                                    null, 
                                    null, 
                                    adjunto);
    }
    
    @Override
    public boolean notificaAutorizadoCliente(Documento documento, Usuario usuario) {
        Notificacion notifica = EnviarNotificacion.AUTORIZADO;
        MailSetting ms = MailSettingUtil.getInstance()
                        .toObject(documento.getSociedad().getMailSettings());
       
        List<String> adjunto = new ArrayList();
        adjunto.add(documento.getXml());
        adjunto.add(documento.getPdf());
        
//        String sociedad [] = { documento.getSociedad().getRuc(), documento.getSociedad().getRazonSocial() };
        
        String[ ] sociedad = new String[5];
        sociedad[0] = documento.getSociedad().getRuc();
        sociedad[1] = documento.getSociedad().getRazonSocial();
        sociedad[2] = "";
        sociedad[3] = "";
        sociedad[4] = "none";
        if(usuario != null && usuario.getClave().equalsIgnoreCase(usuario.getClaveInicial())) {
        	sociedad[2] = usuario.getUsername();
            sociedad[3] = usuario.getUsuarioSap();
            sociedad[4] = "block";
        }
        
        return notifica.enviarCorreo(
        						sociedad,
									this.getMailProperties(ms), 
                                    TipoDocumentoEnum.getTipo(documento.getTipoDocumento()).getDescripcion(), 
                                    documento.getSerieCorrelativo(), 
                                    documento.getNumeroSap(), 
                                    Util.getCorreosValidos(documento.getMailDestino()), 
                                    null, 
                                    null, 
                                    adjunto);
    }
   
    @Override
    public boolean notificaRechazoAdministrador(Documento documento) {
        Notificacion notifica = EnviarNotificacion.RECHAZADO;
        MailSetting ms = MailSettingUtil.getInstance()
                        .toObject(documento.getSociedad().getMailSettings());
        
        String mailAdministrador = Util.getCorreosValidos(documento.getSociedad().getMailNotificacion());
        
        String sociedad [] = { documento.getSociedad().getRuc(), documento.getSociedad().getRazonSocial() };
        
        List<String> adjunto = new ArrayList();
        adjunto.add(documento.getXml());
        return notifica.enviarCorreo(
        							sociedad,
									this.getMailProperties(ms), 
                                    TipoDocumentoEnum.getTipo(documento.getTipoDocumento()).getDescripcion(), 
                                    documento.getSerieCorrelativo(), 
                                    documento.getMensaje(), 
                                    mailAdministrador, 
                                    null, 
                                    null, 
                                    adjunto);
    }
   
    @Override
    public boolean renotificaRechazoAdministrador(Documento documento, String timeAvailableCorrecion) {
        Notificacion notifica = EnviarNotificacion.NOTIFICACION_RECHAZADO;
        MailSetting ms = MailSettingUtil.getInstance()
                        .toObject(documento.getSociedad().getMailSettings());
        
        String mailAdministrador = Util.getCorreosValidos(documento.getSociedad().getMailNotificacion());
        
        String sociedad [] = { documento.getSociedad().getRuc(), documento.getSociedad().getRazonSocial(), timeAvailableCorrecion };
        
        return notifica.enviarCorreo(
        							sociedad,
									this.getMailProperties(ms), 
                                    TipoDocumentoEnum.getTipo(documento.getTipoDocumento()).getDescripcion(), 
                                    documento.getSerieCorrelativo(), 
                                    documento.getMensaje(), 
                                    mailAdministrador, 
                                    null,
                                    null,
                                    null);//adjunto
    }

    @Override
    public boolean notificaInconsistenteAdministrador(Documento documento) {
        try {
            Notificacion notifica = EnviarNotificacion.INCONSISTENTE;

            MailSetting ms = MailSettingUtil.getInstance()
                    .toObject(documento.getSociedad().getMailSettings());

            String mailAdministrador = Util.getCorreosValidos(documento.getSociedad().getMailNotificacion());
            
            String sociedad [] = { documento.getSociedad().getRuc(), documento.getSociedad().getRazonSocial() };
            
            List<String> adjunto = new ArrayList();
            adjunto.add(documento.getXml());
            
            return notifica.enviarCorreo(
            		sociedad,
					this.getMailProperties(ms),
                    TipoDocumentoEnum.getTipo(documento.getTipoDocumento()).getDescripcion(),
                    documento.getSerieCorrelativo(),
                    documento.getMensaje(),
                    mailAdministrador,
                    null,
                    null,
                    adjunto);
        } catch (Exception ex) {
            logger.error("Error al enviar la notificacion Administrador", ex);
            return false;
        }
    }

    private MailProperties getMailProperties(final MailSetting ms) {
        return new MailProperties() {

            @Override
            public String getHost() {
                return ms.getHost();
            }

            @Override
            public String getPort() {
                return ms.getPort();
            }

            @Override
            public String getUsuario() {
                return ms.getUser();
            }

            @Override
            public String getPassword() {
                return ms.getPassword();
            }
        };
    }
    
    @Override
    public void guardarPersonaPortal(Documento documento, ComprobantePortal comprobantePortal) throws IntegradorException {
        try {
            SqlConfig.getSqlSessionManager().getMapper(DocumentoMapper.class).
            	insertarPersonaPortal(documento,comprobantePortal);
        } catch (Exception e) {
            logger.error(e);
            throw this.getErrorCode(e);
        }
    }
    
    @Override
    public void guardarUsuarioPortal(Documento documento, ComprobantePortal comprobantePortal) throws IntegradorException {
        try {
            SqlConfig.getSqlSessionManager().getMapper(DocumentoMapper.class).
            	insertarUsuarioPortal(documento,comprobantePortal);
        } catch (Exception e) {
            logger.error(e);
            throw this.getErrorCode(e);
        }
    } 
}
