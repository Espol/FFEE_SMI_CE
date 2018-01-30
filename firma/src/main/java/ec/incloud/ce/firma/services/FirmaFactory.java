/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.incloud.ce.firma.services;

/**
 *
 * @author Usuario
 */
public class FirmaFactory {
    /**
     * 
     * @return 
     */
    public static FirmaServices createFirmaServices(){
        return FirmaServicesImpl.create();
    }
}
