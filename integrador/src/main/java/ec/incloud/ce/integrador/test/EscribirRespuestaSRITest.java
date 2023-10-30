/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.incloud.ce.integrador.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

import ec.gob.sri.comprobantes.ws.aut.Autorizacion;
import ec.gob.sri.comprobantes.ws.aut.AutorizacionComprobantesOffline;
import ec.gob.sri.comprobantes.ws.aut.AutorizacionComprobantesOfflineService;
import ec.gob.sri.comprobantes.ws.aut.Mensaje;
import ec.gob.sri.comprobantes.ws.aut.RespuestaComprobante;

/**
 *
 * @author Usuario
 */
public class EscribirRespuestaSRITest {
   public static void main(String... arg) {
//        AutorizacionComprobantesService services = new AutorizacionComprobantesService();
//        AutorizacionComprobantes autorizacion = services.getAutorizacionComprobantesPort();
//        RespuestaComprobante respuesta = autorizacion.autorizacionComprobante("0909201501099012918500110010010000010040001000114");
	   
	   AutorizacionComprobantesOfflineService services = new AutorizacionComprobantesOfflineService();
   	   AutorizacionComprobantesOffline autorizacion = services.getAutorizacionComprobantesOfflinePort();
        RespuestaComprobante respuesta = autorizacion.autorizacionComprobante("1608201801099286576800120011010000166150016610114");
//        RespuestaComprobante.Autorizaciones listAutorizaciones = respuesta.getAutorizaciones();
//        List<Autorizacion> lstAutorizacion = listAutorizaciones.getAutorizacion();
        
        
        
        
        
        XStream stream = new XStream(
        new XppDriver() {
	    public HierarchicalStreamWriter createWriter(Writer out) {
		return new PrettyPrintWriter(out) {
		    boolean cdata = false;
		    @SuppressWarnings("rawtypes")
			public void startNode(String name, Class clazz){
			super.startNode(name, clazz);
			cdata = (name.equals("comprobante"));
		    }
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
        stream.alias("respuestaComprobante", RespuestaComprobante.class);
        stream.alias("autorizacion", Autorizacion.class);
        stream.alias("mensaje", Mensaje.class);
        
        
        String pathAbsolute = "C:\\Csti\\rspuesta_sri4.xml";
        try {
            
            FileOutputStream fis = new FileOutputStream(new File(pathAbsolute));
            fis.write("<?xml version='1.0' encoding='UTF-8' ?>".getBytes());
            OutputStreamWriter osw = new OutputStreamWriter(fis, "UTF-8");
            stream.toXML(respuesta, osw);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
