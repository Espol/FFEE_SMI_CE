/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.incloud.ce.notificacion;

import java.util.List;

/**
 *
 * @author Usuario
 */
public interface Notificacion {
    /**
     * 
     * @param mailProp
     * @param tipoDocumento
     * @param numeroSri
     * @param mensajeAdicional
     * @param from
     * @param cc
     * @param cco
     * @param archivoAdjunto
     * @return 
     */
    public boolean enviarCorreo(
					    		String Sociedad[],
					    		MailProperties mailProp, 
                                String tipoDocumento,
                                String numeroSri,
                                String mensajeAdicional,
                                String from,
                                String cc,
                                String cco,
                                List<String> archivoAdjunto);
}
