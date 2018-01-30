/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.incloud.ce.integrador.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.log4j.Logger;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

import ec.gob.sri.comprobantes.ws.on.aut.Autorizacion;
import ec.gob.sri.comprobantes.ws.on.aut.Mensaje;
import ec.gob.sri.comprobantes.ws.on.aut.RespuestaComprobante;
import ec.incloud.ce.integrador.bean.AckSRI;

/**
 *
 * @author Usuario
 */
public class XStreamAutorizacionOnline {
    private XStream stream;
    
    private static XStreamAutorizacionOnline instance;
    private final Logger log = Logger.getLogger(XStreamAutorizacionOnline.class);
    
    private XStreamAutorizacionOnline(){
        stream = new XStream(
        new XppDriver() {
            @Override
	    public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
			    boolean cdata = false;
	                    @Override
			    public void startNode(String name, Class clazz){
					super.startNode(name, clazz);
					cdata = (name.equals("comprobante"));
			    }
	                    @Override
			    protected void writeText(QuickWriter writer, String text) {
					if(cdata) {
					    writer.write("<![CDATA[");
					    writer.write(text);
					    writer.write("]]>");
					} else {
					    writer.write(text);
					}
			    }
			};
	    }
	}
        );

//        stream.useAttributeFor(FechaConvert.class, "fechaAutorizacion");
//        stream.aliasField("fechaAutorizacion", Autorizacion.class, "fechaAutorizacion");
//      stream.useAttributeFor(Autorizacion.class, "fechaAutorizacion");
//      stream.registerConverter(new FechaConvert());
//      stream.registerLocalConverter(Autorizacion.class, "org.apache.xerces.jaxp.datatype.XMLGregorianCalendarImpl", new FechaConvert());
//      stream.alias("fechaAutorizacion", Autorizacion.class);
        
//      stream.registerLocalConverter(Autorizacion.class, "fechaAutorizacion", new FechaConvert());      
//        stream.omitField(Autorizacion.class, "class");  
        stream.alias("respuestaComprobante", RespuestaComprobante.class);
        stream.alias("autorizacion", Autorizacion.class);
        stream.alias("mensaje", Mensaje.class);
    }
    
    public static XStreamAutorizacionOnline getInstance(){
        synchronized(XStreamAutorizacionOnline.class){
            if(instance == null) {
                instance = new XStreamAutorizacionOnline();
            }
            return instance;
        }
    }
    
    public void generarXml(AckSRI ackSRI,RespuestaComprobante respuesta, String pathAbsolute){
        try {    
            FileOutputStream fis = new FileOutputStream(new File(pathAbsolute));
            fis.write("<?xml version='1.0' encoding='UTF-8' ?>".getBytes());
            OutputStreamWriter osw = new OutputStreamWriter(fis, "UTF-8");
            
            stream.toXML(ackSRI, osw);
        } catch (Exception ex) {
            log.error("Error al escribir respuesta del SRI", ex);
        }
    }
    
    public void generarXml(RespuestaComprobante respuesta, String pathAbsolute){
        try {    
            FileOutputStream fis = new FileOutputStream(new File(pathAbsolute));
            fis.write("<?xml version='1.0' encoding='UTF-8' ?>".getBytes());
            OutputStreamWriter osw = new OutputStreamWriter(fis, "UTF-8");
            
            stream.toXML(respuesta, osw);
        } catch (Exception ex) {
            log.error("Error al escribir respuesta del SRI", ex);
        }
    }
}
