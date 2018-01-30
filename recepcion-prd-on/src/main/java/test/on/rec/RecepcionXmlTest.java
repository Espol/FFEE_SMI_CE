/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.on.rec;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import ec.gob.sri.comprobantes.ws.on.rec.Comprobante;
import ec.gob.sri.comprobantes.ws.on.rec.Mensaje;
import ec.gob.sri.comprobantes.ws.on.rec.RecepcionComprobantes;
import ec.gob.sri.comprobantes.ws.on.rec.RecepcionComprobantesService;
import ec.gob.sri.comprobantes.ws.on.rec.RespuestaSolicitud;

/**
 *
 * @author Usuario
 */
public class RecepcionXmlTest {

    public static void main(String... arg) throws IOException {
    	RecepcionComprobantesService services = new RecepcionComprobantesService();
    	RecepcionComprobantes recepcion = services.getRecepcionComprobantesPort();
//        String path = "D:/PruebasMirth/ComprobantesQuimpac/factura/factura-F2-5400066108-001-001-000001851-12-10-2015-16-59-56.xml";
//    	String path = "xml1.xml";
//    	String path = "xml_quimpac_pendiente.xml";
//    	String path = "autorizado_quimpac.xml";

    	String path = "t1.xml";
    	
    	
        
        byte[] data = Files.readAllBytes(Paths.get(path));
        RespuestaSolicitud solicitud = recepcion.validarComprobante(data);

        RespuestaSolicitud.Comprobantes lstComprobante = solicitud.getComprobantes();
        if (lstComprobante != null && lstComprobante.getComprobante() != null) {
            for (Comprobante x : lstComprobante.getComprobante()) {
                String claveAcceso = x.getClaveAcceso();
                Comprobante.Mensajes mensajes = x.getMensajes();

                if (mensajes.getMensaje() != null) {
                    for (Mensaje mensaje : mensajes.getMensaje()) {

                        mensaje.getIdentificador();
                        mensaje.getInformacionAdicional();
                        mensaje.getMensaje();
                        mensaje.getTipo();

                    }
                }

            }
        }
    }
}
