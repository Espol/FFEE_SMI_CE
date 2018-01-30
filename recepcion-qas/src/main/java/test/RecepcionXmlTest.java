/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import ec.gob.sri.comprobantes.ws.rec.Comprobante;
import ec.gob.sri.comprobantes.ws.rec.Mensaje;
import ec.gob.sri.comprobantes.ws.rec.RecepcionComprobantesOffline;
import ec.gob.sri.comprobantes.ws.rec.RecepcionComprobantesOfflineService;
import ec.gob.sri.comprobantes.ws.rec.RespuestaSolicitud;

/**
 *
 * @author Usuario
 */
public class RecepcionXmlTest {

    public static void main(String... arg) throws IOException {
    	RecepcionComprobantesOfflineService services = new RecepcionComprobantesOfflineService();
    	RecepcionComprobantesOffline recepcion = services.getRecepcionComprobantesOfflinePort();
        String path = "D:/PruebasMirth/ComprobantesQuimpac/factura/factura-Z2FN-1290084007-001-102-000004226-25-11-2015-11-17-58.xml";
        
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
