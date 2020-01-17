package ec.incloud.ce.integrador.on;

import java.io.File;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import ec.incloud.ce.bean.common.Referencia;
import ec.incloud.ce.bean.liquidacionCompra.LiquidacionCompra;
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

public class LiquidacionCompraEmision implements Emision<LiquidacionCompra> {
	
	private static Emision<LiquidacionCompra> instance = null;
	private LiquidacionCompra liquidacionCompra;
	private Sociedad sociedad;
	private String pathAbsoluteXML;
	private String pathBackupXML;
    private String[] parametrosAdicionales;
    private final ClaveAccesoGen claveGenerador = new ClaveAccesoGen();
    private final MensajeServices msgServices = new MensajeServices();
    private final TipoDocumentoEnum tipo = TipoDocumentoEnum.LIQUIDACIONCOMPRA;
    private final Util util = Util.INSTANCE;
    private final DocumentoServices documentoServices = ServicesFactory.getFactory().createDocumentoServices();
    private final Logger log = Logger.getLogger(this.getClass());
	
	
	public LiquidacionCompraEmision() {
		
	}
	
	public static Emision<LiquidacionCompra> create() {
		synchronized (LiquidacionCompraEmision.class) {
			if(instance == null) {
				instance = new LiquidacionCompraEmision();
			}
			return instance;
		}
	}

	@Override
	public Respuesta emitir(
			LiquidacionCompra liquidacionCompra, 
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
			String observacion
			) {
		synchronized (LiquidacionCompra.class) {
			Respuesta rpta = new Respuesta();
			this.liquidacionCompra =  liquidacionCompra;
			String clase = referencia.getTipoDocReferencia();
            String docSap = referencia.getDocReferencia();
            
            String fechaRegistro = Util.INSTANCE.getFechaRegistroTimeStamp();

            this.parametrosAdicionales = new String[]{ clase, docSap };
            
            log.info(MessageFormat.format("Procesando Documento Factura [establecimiento: {0}, puntoEmision: {1}, numero: {2}, docSap: {3}], "
                    + "Adicionales obtenidos [docSap: {4}, clase: {5}, montoTotal: {6}, terminal: {7}, cliente: {8}, mail: {9}]",
                    liquidacionCompra.getInfoTributaria() != null ? liquidacionCompra.getInfoTributaria().getEstab() : "nulo",
                    liquidacionCompra.getInfoTributaria() != null ? liquidacionCompra.getInfoTributaria().getPtoEmi() : "nulo",
                    liquidacionCompra.getInfoTributaria() != null ? liquidacionCompra.getInfoTributaria().getSecuencial() : "nulo",
                    		referencia.getDocReferencia(),
                    		referencia.getTipoDocReferencia(),
                    montoTotal,
                    terminal,
                    codInterlocutor,
                    mails));
            try {
            	this.cargarConfiguracion();
            	
            	// se realiza la validación, el método validateFechaExtemporanea() retorna true si la fecha es válida.
                if( !Util.INSTANCE.validateFechaExtemporanea( liquidacionCompra.getInfoLiquidacionCompra().getFechaEmision(), this.sociedad.getDiasFechaExtemporanea() ) ){                	
                	rpta.setEstado(ERROR);
            		rpta.addMensaje( new Mensaje(620, "I", "Documento con fecha extemporánea mayor a "+ this.sociedad.getDiasFechaExtemporanea() + " días.") );            		                    
                    return rpta;
            	}
                
                this.generarClave();
                this.generarXML();
                this.firmarXML();
                this.validarXML();
                
                Documento documento = documentoServices.getDocumento(
                        this.getRuc(),
                        tipo.getCodigo(),
                        liquidacionCompra.getInfoTributaria().getEstab(),
                        liquidacionCompra.getInfoTributaria().getPtoEmi(),
                        liquidacionCompra.getInfoTributaria().getSecuencial());
                
                if(documento != null && documento.getEstadoSri() != null) {
                	
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
                		
                	} else {
                		rpta.addMensaje(new Mensaje("I", MessageFormat.format(msgServices.getMensaje("0"),
                				liquidacionCompra.getInfoTributaria().getEstab(),
                				liquidacionCompra.getInfoTributaria().getPtoEmi(),
                				liquidacionCompra.getInfoTributaria().getSecuencial())));
                	}
                } else {
                	rpta.addMensaje(new Mensaje("I", MessageFormat.format(msgServices.getMensaje("0"),
            				liquidacionCompra.getInfoTributaria().getEstab(),
            				liquidacionCompra.getInfoTributaria().getPtoEmi(),
            				liquidacionCompra.getInfoTributaria().getSecuencial())));
                }
                
                this.guardarDocumento(
                		fechaRegistro, 
                		usuarioSap, 
                		terminal,
                		codInterlocutor, 
                		mails, 
                		referencia.getDocReferencia() , 
                		referencia.getTipoDocReferencia(), 
                		montoTotal,
                		observacion, 
                		emailPortal);
                
                rpta.addMensaje( new Mensaje( this.sociedad.getEsquema()==0?100:101, "I", this.liquidacionCompra.getInfoTributaria().getClaveAcceso() ) );
                rpta.setEstado(OK);
                
                log.info(MessageFormat.format("Documento Factura [establecimiento: {0}, puntoEmision: {1}, numero: {2}, docSap: {3}] -> Procesado correctamente",
                        liquidacionCompra.getInfoTributaria() != null ? liquidacionCompra.getInfoTributaria().getEstab() : null,
                        liquidacionCompra.getInfoTributaria() != null ? liquidacionCompra.getInfoTributaria().getPtoEmi() : null,
                        liquidacionCompra.getInfoTributaria() != null ? liquidacionCompra.getInfoTributaria().getSecuencial() : null,
                        referencia.getDocReferencia())
                );
                
                return rpta;
                
			} catch (XmlException ex) {
                rpta = new Respuesta();
                rpta.addMensaje(new Mensaje("E", ex.getMensaje()));
            } catch (FirmaException ex) {
                rpta = new Respuesta();
                rpta.addMensaje(new Mensaje("E", ex.getMensaje()));
            } catch (ValidacionException ex) {
                this.guardarDocumentoInconsistente(fechaRegistro, usuarioSap, terminal, codInterlocutor, mails, docSap, clase, ex.getMensaje() );
                rpta = new Respuesta();
                rpta.addMensaje(new Mensaje("E", ex.getMensaje()));
            } catch (IntegradorException ex) {
                rpta = new Respuesta();
                rpta.addMensaje(new Mensaje("E", ex.getMessage()));
            }
            
			return rpta;
		}
	}
	
	private void cargarConfiguracion() throws IntegradorException {
        this.sociedad = ServicesFactory.getFactory().createSociedadServices().getSociedad(this.getRuc());
        this.sociedad.setMailNotificacion( this.sociedad.getMailLiquidacionCompra() );
    }
	
	private void generarClave() {
        String clave = this.claveGenerador.generarClaveAcceso(util
                .getDateFromString(this.liquidacionCompra.getInfoLiquidacionCompra().getFechaEmision()),
                tipo.getCodigo(),
                this.getRuc(),
                this.liquidacionCompra.getInfoTributaria().getAmbiente(),
                this.liquidacionCompra.getInfoTributaria().getEstab() + this.liquidacionCompra.getInfoTributaria().getPtoEmi(),
                this.liquidacionCompra.getInfoTributaria().getSecuencial(),
                this.liquidacionCompra.getInfoTributaria().getSecuencial().substring(2, 8) + tipo.getCodigo(),
                this.liquidacionCompra.getInfoTributaria().getTipoEmision());
        this.liquidacionCompra.getInfoTributaria().setClaveAcceso(clave);
    }
	
	
	
	private void generarXML() throws XmlException {
        util.createDirectory(this.getXMLPath());
        this.pathAbsoluteXML = this.getXMLPath() + File.separator + this.getXMLName() + PUNTO_XML;
        XmlFactory.getLiquidacionCompraService().generarXml(this.liquidacionCompra, this.pathAbsoluteXML);
        
        util.createDirectory(this.getXMLPathBackup());
        this.pathBackupXML = this.getXMLPathBackup() + File.separator + this.getXMLName() + PUNTO_XML;
        XmlFactory.getLiquidacionCompraService().generarXml(this.liquidacionCompra, this.pathBackupXML);
    }

    private void firmarXML() throws FirmaException {
        FirmaFactory.createFirmaServices().firma(this.pathAbsoluteXML,
                this.sociedad.getPathCertificado(),
                this.sociedad.getClaveCertificado());
        
        FirmaFactory.createFirmaServices().firma(this.pathBackupXML,
                this.sociedad.getPathCertificado(),
                this.sociedad.getClaveCertificado());
    }
    
    
    private void validarXML() throws ValidacionException {
        try{
            ValidacionFactory.createValidacionLiquicacionCompra().validar(this.liquidacionCompra);
        }catch (ValidacionException ex){
            log.warn("Obteniendo datos para enviar la notificacion de inconsistencia");
            
            Documento documento = new Documento();
            documento.setSociedad(this.sociedad);
            documento.setTipoDocumento(TipoDocumentoEnum.LIQUIDACIONCOMPRA.getCodigo());
            documento.setMensaje(ex.getMensaje());
            documento.setSerieCorrelativo( (this.liquidacionCompra.getInfoTributaria() == null) ? "0-0-0":
                                            MessageFormat.format("{0}-{1}-{2}", 
                                            this.liquidacionCompra.getInfoTributaria().getEstab(),
                                            this.liquidacionCompra.getInfoTributaria().getPtoEmi(),
                                            this.liquidacionCompra.getInfoTributaria().getSecuencial()));
            documento.setXml(this.pathAbsoluteXML);
            this.documentoServices.notificaInconsistenteAdministrador(documento);
            throw ex;
        }
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
        documento.setEstablecimiento(this.liquidacionCompra.getInfoTributaria().getEstab());
        documento.setPuntoEmision(this.liquidacionCompra.getInfoTributaria().getPtoEmi());
        documento.setNumero(this.liquidacionCompra.getInfoTributaria().getSecuencial());
        documento.setFechaEmision(util.getDateFromString(this.liquidacionCompra.getInfoLiquidacionCompra().getFechaEmision()));
        documento.setFechaReferencia(fechaRegistro);
        documento.setCodigoCliente(cliente);
        documento.setClaveAcceso(this.liquidacionCompra.getInfoTributaria().getClaveAcceso());
        documento.setXml(pathAbsoluteXML);
        documento.setPathXml(pathBackupXML);
        documento.setNumeroSap(docSap);//doc referencia
        documento.setUsuarioSap(usuarioSap);//clave portal
        documento.setTerminal(terminal);
        documento.setMailDestino(mail);
        documento.setImporteTotal(new BigDecimal(this.liquidacionCompra.getInfoLiquidacionCompra().getImporteTotal()));
        documento.setClaseDocumento(clase);
        documento.setNombreCliente(this.liquidacionCompra.getInfoLiquidacionCompra().getRazonSocialComprador());
        documento.setObsComprobante(obsComprobante);
        documento.setEmailPortal(emailPortal);//correo para el portal
        documento.setRucCliente(this.liquidacionCompra.getInfoLiquidacionCompra().getIdentificacionProveedor());//portal
        
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
                documento.setEstablecimiento(this.liquidacionCompra.getInfoTributaria().getEstab());
                documento.setPuntoEmision(this.liquidacionCompra.getInfoTributaria().getPtoEmi());
                documento.setNumero(this.liquidacionCompra.getInfoTributaria().getSecuencial());
                documento.setFechaEmision(util.getDateFromString(this.liquidacionCompra.getInfoLiquidacionCompra().getFechaEmision()));
                documento.setFechaReferencia(fechaRegistro);
                documento.setCodigoCliente(cliente);
                documento.setClaveAcceso(this.liquidacionCompra.getInfoTributaria().getClaveAcceso());
                documento.setXml(pathAbsoluteXML);
                documento.setNumeroSap(docSap);
                documento.setUsuarioSap(usuarioSap);
                documento.setTerminal(terminal);
                documento.setMailDestino(mail);
                documento.setImporteTotal(new BigDecimal(this.liquidacionCompra.getInfoLiquidacionCompra().getImporteTotal()));
                documento.setClaseDocumento(clase);
                documento.setNombreCliente(this.liquidacionCompra.getInfoLiquidacionCompra().getRazonSocialComprador());
                documento.setMensaje(mensaje);
                documentoServices.guardarInconsistencia(documento);
        }catch(Exception ex){
            log.error("Error al insertar inconsistencia", ex);
        }

    }
    
	
	public String getRuc() {
    	log.warn("Ruc:" + this.liquidacionCompra.getInfoTributaria().getRuc());
        return (this.liquidacionCompra != null && this.liquidacionCompra.getInfoTributaria() != null)?
                this.liquidacionCompra.getInfoTributaria().getRuc() : "nulo";
    }
	
	
	public String getXMLPath() {
        return new StringBuilder(sociedad.getPathRoot()).
                append(File.separator).
                append(tipo.getDirectorio()).toString();
    }
	
    
    public String getXMLPathBackup() {
        return new StringBuilder(sociedad.getPathRoot()).
                append(File.separator).
                append(tipo.getDirectorio()).
                append(File.separator).
                append("backup").toString();
    }
    
    public String getXMLName() {
        return new StringBuilder(tipo.getPrefijoXml()).append("-").
                append(parametrosAdicionales[0]).append("-").
                append(parametrosAdicionales[1]).append("-").
                append(liquidacionCompra.getInfoTributaria().getEstab()).append("-").
                append(liquidacionCompra.getInfoTributaria().getPtoEmi()).append("-").
                append(liquidacionCompra.getInfoTributaria().getSecuencial()).append("-").
                append(util.getStringFromDateXmlName(new Date())).toString();
    }

}
