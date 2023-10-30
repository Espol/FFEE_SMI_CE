/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.incloud.ce.firma.exception;

/**
 *
 * @author Usuario
 */
@SuppressWarnings("serial")
public class FirmaException extends Exception{
    
    private final String mensaje;
    
    public FirmaException(String message) {
        super(message);
        this.mensaje = message;
    }
    
    public String getMensaje(){
        return this.mensaje;
    }
}
