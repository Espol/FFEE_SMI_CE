/*
 * Copyright (c) Inclod Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.integrador.util;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ec.incloud.ce.bean.credito.NotaCredito;
import ec.incloud.ce.bean.debito.NotaDebito;
import ec.incloud.ce.bean.factura.Factura;
import ec.incloud.ce.bean.facturaExportacion.FacturaExportacion;
import ec.incloud.ce.bean.facturaReembolso.FacturaReembolso;
import ec.incloud.ce.bean.guia.GuiaRemision;
import ec.incloud.ce.bean.retencion.ComprobanteRetencion;
import ec.incloud.ce.integrador.bean.ComprobantePortal;
import ec.incloud.ce.integrador.bean.Documento;
import ec.incloud.ce.xml.exception.XmlException;
import ec.incloud.ce.xml.services.XmlFactory;
import ec.incloud.ce.xml.services.XmlServices;

/**
 *
 * @author Inclod Services S.A.C.
 */
public enum Util {
	
    INSTANCE;
	
	public static int PROCESO_CADUCIDAD_FIRMA_OFF = 0;
	public static int PROCESO_CADUCIDAD_FIRMA_ON = 1;
	
	public String getMailByTypeDoc(Documento documento){
		String mailNotif = null;
		
		if( documento.getTipoDocumento().equals("01") )
			mailNotif = documento.getSociedad().getMailFactura();
		else if( documento.getTipoDocumento().equals("04") )
			mailNotif = documento.getSociedad().getMailCredito();
		else if( documento.getTipoDocumento().equals("05") )
			mailNotif = documento.getSociedad().getMailDebito();
		else if( documento.getTipoDocumento().equals("06") )
			mailNotif = documento.getSociedad().getMailGuia();
		else if( documento.getTipoDocumento().equals("07") )
			mailNotif = documento.getSociedad().getMailRetencion();
		
		
		return mailNotif;
	}
	
	public boolean validateFechaExtemporanea(String fechaEmision, int diasFechaExtemporanea){

        Calendar dateEmision = Calendar.getInstance();
        dateEmision.setTime( getDateFromString(fechaEmision) );
        
        Calendar dateValido = Calendar.getInstance();
        dateValido.setTime( new Date() );
        dateValido.add(Calendar.DATE, diasFechaExtemporanea * -1);
        
        Calendar dateValidoBefore = Calendar.getInstance();
        dateValidoBefore.setTime( new Date() );
        dateValidoBefore.add(Calendar.DATE, diasFechaExtemporanea);
        
        return dateEmision.after(dateValido ) && dateEmision.before(dateValidoBefore);
	}
	
    public String getFechaRegistroTimeStamp() {    	
    	return getStringFromDate( Calendar.getInstance().getTime() , "yyyy-MM-dd HH:mm:ss.SSS" );
    	
    }

    public String getCurrentDateToString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return sdf.format(new Date());
    }

    public Date getDateFromString(String fecha) {
        try {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            return df.parse(fecha);
        } catch (ParseException ex) {
            return null;
        }
    }

    public String getStringFromDate(Date date) {
        return getStringFromDate(date, "dd/MM/yyyy");
    }

    public String getStringFromDateWithTime(Date date) {
        return getStringFromDate(date, "dd/MM/yyyy HH:mm:ss");
    }

    public synchronized String getStringFromDateXmlName(Date date) {
        return getStringFromDate(date, "dd-MM-yyyy-HH-mm-ss");
    }

    public String getStringFromDate(Date date, String formato) {
        DateFormat df = new SimpleDateFormat(formato);
        return df.format(date);
    }

    public synchronized void createDirectory(String path) {
        File directory = new File(path);
        if (directory.isFile()) {
            directory = directory.getParentFile();
        }
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
    
    public ComprobantePortal getDataPortal(String tipoDocumento, int subTipoDocumento, String beanXml) throws XmlException{    
	    XmlServices xmlServices = null;
	    ComprobantePortal comprobantePortal=new ComprobantePortal();
	    switch (tipoDocumento) {
		    case "01":
		    	if( subTipoDocumento!=2 && subTipoDocumento!=3){//2=exportacion, 3=reembolso
		            xmlServices = XmlFactory.getFacturaXmlServices();
		            Factura comprobante = (Factura) xmlServices.getComprobanteDePathArchivo( beanXml );
		            comprobantePortal.setRazonSocialsCliente(comprobante.getInfoFactura().getRazonSocialComprador());
		            comprobantePortal.setRucCliente( comprobante.getInfoFactura().getIdentificacionComprador() );
		            comprobantePortal.setTipoDocCliente( comprobante.getInfoFactura().getTipoIdentificacionComprador() );
		    	}else if(subTipoDocumento==2){
		            xmlServices = XmlFactory.getFacturaExportacionXmlServices();
		            FacturaExportacion comprobante = (FacturaExportacion) xmlServices.getComprobanteDePathArchivo( beanXml );
		            comprobantePortal.setRazonSocialsCliente(comprobante.getInfoFactura().getRazonSocialComprador());
		            comprobantePortal.setRucCliente( comprobante.getInfoFactura().getIdentificacionComprador() );
		            comprobantePortal.setTipoDocCliente( comprobante.getInfoFactura().getTipoIdentificacionComprador() );
		    	}else if(subTipoDocumento==3){
		            xmlServices = XmlFactory.getFacturaReembolsoXmlServices();
		            FacturaReembolso comprobante = (FacturaReembolso) xmlServices.getComprobanteDePathArchivo( beanXml );
		            comprobantePortal.setRazonSocialsCliente(comprobante.getInfoFactura().getRazonSocialComprador());
		            comprobantePortal.setRucCliente( comprobante.getInfoFactura().getIdentificacionComprador() );
		            comprobantePortal.setTipoDocCliente( comprobante.getInfoFactura().getTipoIdentificacionComprador() );
		    	}
		        break;
		    case "04":
		        xmlServices = XmlFactory.getNotaCreditoXmlServices();
	            NotaCredito notaCredito = (NotaCredito) xmlServices.getComprobanteDePathArchivo( beanXml );
	            comprobantePortal.setRazonSocialsCliente(notaCredito.getInfoNotaCredito().getRazonSocialComprador());
	            comprobantePortal.setRucCliente( notaCredito.getInfoNotaCredito().getIdentificacionComprador() );
	            comprobantePortal.setTipoDocCliente( notaCredito.getInfoNotaCredito().getTipoIdentificacionComprador() );
		        break;
		    case "05":
		        xmlServices = XmlFactory.getNotaDebitoXmlServices();
	            NotaDebito notaDebito = (NotaDebito) xmlServices.getComprobanteDePathArchivo( beanXml );
	            comprobantePortal.setRazonSocialsCliente(notaDebito.getInfoNotaDebito().getRazonSocialComprador());
	            comprobantePortal.setRucCliente( notaDebito.getInfoNotaDebito().getIdentificacionComprador() );
	            comprobantePortal.setTipoDocCliente( notaDebito.getInfoNotaDebito().getTipoIdentificacionComprador() );
		        break;
		    case "06":
		        xmlServices = XmlFactory.getGuiaRemisionXmlServices();
	            GuiaRemision guiaRemision = (GuiaRemision) xmlServices.getComprobanteDePathArchivo( beanXml );
	            comprobantePortal.setRazonSocialsCliente(guiaRemision.getDestinatarios().get(0).getRazonSocialDestinatario());
	            comprobantePortal.setRucCliente( guiaRemision.getDestinatarios().get(0).getIdentificacionDestinatario() );	            
	            comprobantePortal.setTipoDocCliente( 
	            		guiaRemision.getDestinatarios().get(0).getIdentificacionDestinatario().length()==10?"05":"04"
	            );	            
		        break;
		    case "07":
		        xmlServices = XmlFactory.getRetencionServices();
	            ComprobanteRetencion retencion = (ComprobanteRetencion) xmlServices.getComprobanteDePathArchivo( beanXml );
	            comprobantePortal.setRazonSocialsCliente(retencion.getInfoCompRetencion().getRazonSocialSujetoRetenido());
	            comprobantePortal.setRucCliente( retencion.getInfoCompRetencion().getIdentificacionSujetoRetenido() );
	            comprobantePortal.setTipoDocCliente( retencion.getInfoCompRetencion().getTipoIdentificacionSujetoRetenido() );
		        break;
		    default:
		    	return null;
	    }
	    
	    return comprobantePortal;
    }
    
    private static final String PATROM_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    
    
    
    public static String getCorreosValidos(String cadenaEmail) {
    	String[] correos = cadenaEmail.split(","); 
    	Pattern pattern = Pattern.compile(PATROM_EMAIL);
    	String correoValidos ="";
    	for(String email : correos) {
    		Matcher mather = pattern.matcher(email);
    		if(mather.matches()){
    			if(correoValidos.isEmpty()){
    				correoValidos = email;
    			} else {
    				correoValidos = correoValidos + "," + email;
    			}
    		}
    	}
    	
    	return correoValidos;
    }
    
    public static void main(String[] args) {
    	String emails = "";
    	System.out.println("email correctos: " + Util.getCorreosValidos(emails));
    }
}   
