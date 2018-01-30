/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.incloud.ce.firma.services;

import ec.incloud.ce.firma.exception.FirmaException;

/**
 *
 * @author Usuario
 */
public interface FirmaServices {
    public void firma(String pathAbsoluteXml, String certificado, String clave) throws FirmaException;
}
