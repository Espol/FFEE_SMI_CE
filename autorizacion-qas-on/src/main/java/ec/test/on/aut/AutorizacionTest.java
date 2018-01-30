/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.test.on.aut;

import java.util.List;

import ec.gob.sri.comprobantes.ws.on.aut.Autorizacion;
import ec.gob.sri.comprobantes.ws.on.aut.AutorizacionComprobantes;
import ec.gob.sri.comprobantes.ws.on.aut.AutorizacionComprobantesService;
import ec.gob.sri.comprobantes.ws.on.aut.RespuestaComprobante;

/**
 *
 * @author Usuario
 */
public class AutorizacionTest {
	
    public static void main(String... arg) {
    	AutorizacionComprobantesService services = new AutorizacionComprobantesService();
    	
        AutorizacionComprobantes autorizacion = services.getAutorizacionComprobantesPort();
        RespuestaComprobante respuesta = autorizacion.autorizacionComprobante("0810201501099034476000110010010000018610001860113");
        RespuestaComprobante.Autorizaciones listAutorizaciones = respuesta.getAutorizaciones();
        List<Autorizacion> lstAutorizacion = listAutorizaciones.getAutorizacion();
        if (lstAutorizacion != null) {
            for (Autorizacion x : lstAutorizacion) {
            	
            }
        }

    }
}
