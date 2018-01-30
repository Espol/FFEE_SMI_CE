/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.test;

import java.util.List;

import ec.gob.sri.comprobantes.ws.aut.Autorizacion;
import ec.gob.sri.comprobantes.ws.aut.AutorizacionComprobantesOffline;
import ec.gob.sri.comprobantes.ws.aut.AutorizacionComprobantesOfflineService;
import ec.gob.sri.comprobantes.ws.aut.RespuestaComprobante;

/**
 *
 * @author Usuario
 */
public class AutorizacionTest {
	
    public static void main(String... arg) {
    	AutorizacionComprobantesOfflineService services = new AutorizacionComprobantesOfflineService();
    	
        AutorizacionComprobantesOffline autorizacion = services.getAutorizacionComprobantesOfflinePort();
        RespuestaComprobante respuesta = autorizacion.autorizacionComprobante("2411201501099000724100110021010000040660004060114");
        RespuestaComprobante.Autorizaciones listAutorizaciones = respuesta.getAutorizaciones();
        List<Autorizacion> lstAutorizacion = listAutorizaciones.getAutorizacion();
        if (lstAutorizacion != null) {
            for (Autorizacion x : lstAutorizacion) {
            	
            }
        }

    }
}
