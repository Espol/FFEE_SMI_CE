/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.integrador.test;

import ec.incloud.ce.integrador.bean.SapSettings;
import ec.incloud.ce.integrador.util.SapSettingUtil;

/**
 *
 * @author Usuario
 */
public class SapSetTest {

    public static void main(String[] args) {
        SapSettings sapServices = new SapSettings();
        sapServices.setContrasena("password");
        sapServices.setIdSistema("DES");
        sapServices.setIdioma("ES");
        sapServices.setIpServidor("192.168.10.6");
        sapServices.setMandante("300");
        sapServices.setNumeroInstancia("00");
        sapServices.setSapRouter(null);
        sapServices.setUsuario("CSTI");
        
        String x = "<sapSetting><mandante>300</mandante><usuario>CSTI</usuario><contrasena>password</contrasena><idioma>ES</idioma><ipServidor>192.168.10.6</ipServidor><numeroInstancia>00</numeroInstancia><idSistema>DES</idSistema></sapSetting>";
        
        System.out.println("" + SapSettingUtil.getInstance().toObject(x));
    }
}
