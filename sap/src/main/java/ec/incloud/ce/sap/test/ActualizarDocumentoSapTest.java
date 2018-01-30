/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.incloud.ce.sap.test;

import ec.incloud.ce.sap.services.MensajeSap;
import ec.incloud.ce.sap.services.RespuestaSap;
import ec.incloud.ce.sap.services.SapServices;
import ec.incloud.ce.sap.services.SapFactory;

/**
 *
 * @author Usuario
 */
public class ActualizarDocumentoSapTest {

    public static void main(String... arg) {
        SapServices services = SapFactory.createSapServices();
//        services.setContrasena("password");
//        services.setIdSistema("DES");
//        services.setIdioma("ES");
//        services.setIpServidor("192.168.10.6");
//        services.setMandante("300");
//        services.setNumeroInstancia("00");
//        services.setSapRouter(null);
//        services.setUsuario("CSTI");
        
//        CONNECCIÓN DESARROLLO
        services.setContrasena("Csti2015");
        services.setIdSistema("20D");
        services.setIdioma("ES");
        services.setIpServidor("10.1.1.250");
        services.setMandante("400");
        services.setNumeroInstancia("00");
        services.setSapRouter(null);
        services.setUsuario("ABAP5");
//001-001-00000001
//ERROR EN LA ESTRUCTURA DE LA CLAVE DE ACCESO
        //ESTE PROCESO FUE REALIZADO EN EL AMBIENTE DE PRUEBAS
        MensajeSap mensajeSap = new MensajeSap();
        mensajeSap.setMensaje(58, "ERROR", "X", "X");
        mensajeSap.setMensaje(60, "INFORMATIVO", "XXX", null);

        //001-102-000000061
        RespuestaSap respuesta = services.actualizarDocumento(true,
//                "0990129185001",//0990129185001 ruc hivimar
                "0990344760001", // ruc ecuasal
                "05",
                "001-402-000000007", //001-101-00000369 
                "5600000029", //5400000515
                "RE",
                "2222222222222222222222222222222222222", //9999999999999999999999999999999999999
                "8888888888888888888888888888888888888888888888888",
                "09/09/2015 15:30:25",
                "CSTI",
                true,
                true,
                mensajeSap);
        System.out.println("" + respuesta.getTipo());
        System.out.println("" + respuesta.getDescripcion());
    }
}
