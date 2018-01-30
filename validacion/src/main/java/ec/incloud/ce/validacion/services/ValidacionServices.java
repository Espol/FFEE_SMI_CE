/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.incloud.ce.validacion.services;

import ec.incloud.ce.validacion.exception.ValidacionException;

/**
 *
 * @author Joel Povis Ocaña
 * @param <T>
 */
public interface ValidacionServices<T> {

    /**
     * Validacion XSD de los comprobantes.
     *
     * @param comprobante
     * @throws ValidacionException
     */
    public void validar(T comprobante) throws ValidacionException;

}
