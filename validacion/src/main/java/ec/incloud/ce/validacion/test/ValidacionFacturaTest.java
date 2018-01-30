/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.incloud.ce.validacion.test;

import ec.incloud.ce.bean.common.InfoTributaria;
import ec.incloud.ce.bean.factura.Factura;
import ec.incloud.ce.validacion.exception.ValidacionException;
import ec.incloud.ce.validacion.services.ValidacionFactory;

/**
 *
 * @author Joel Povis Ocaña
 */
public class ValidacionFacturaTest {
    public static void main(String... arg) throws ValidacionException{
        
        Factura factura = new Factura();
            InfoTributaria it = new InfoTributaria();
        factura.setInfoTributaria(it);
        ValidacionFactory.createValidacionFacturaServices().validar(factura);
    }
}
