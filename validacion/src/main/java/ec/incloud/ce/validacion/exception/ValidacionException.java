/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.incloud.ce.validacion.exception;

/**
 *
 * @author Joel Povis Ocaña
 */
public class ValidacionException extends Exception{
    private final String mensaje;
    
    public ValidacionException(String mensaje) {
        super(mensaje);
        this.mensaje = mensaje;
    }
    
    public String getMensaje(){
        return this.mensaje;
    }
    
}
