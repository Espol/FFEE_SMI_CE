/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.incloud.ce.firma.test;

import ec.incloud.ce.firma.exception.FirmaException;
import ec.incloud.ce.firma.services.FirmaFactory;
import ec.incloud.ce.firma.services.FirmaServices;

/**
 *
 * @author Usuario
 */
public class ExampleFirmaTest {
    public static void main(String... arg) throws FirmaException{
        FirmaServices services = FirmaFactory.createFirmaServices();
        services.firma("C:\\test\\fa.xml", 
                "C:\\Program Files\\Incloud Middleware Service\\custom-lib\\certificate\\ecuasal_facturacion_electronica.p12", 
                "CRISSAL2016");
    }
}
