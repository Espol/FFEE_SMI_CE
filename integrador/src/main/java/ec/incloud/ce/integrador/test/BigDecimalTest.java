/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.incloud.ce.integrador.test;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author Usuario
 */
public class BigDecimalTest {
    public static void main(String... arg){
        BigDecimal bd = new BigDecimal("0.00");
        System.out.println("Numero : " + bd);
        bd.add(new BigDecimal("1.00"));
        System.out.println("Suma : " + DosDecimales(bd));
    }
    
    public static BigDecimal DosDecimales(BigDecimal numero) {
        BigDecimal respuesta;
        if (numero != null || !numero.toString().isEmpty()) {
            respuesta = numero.setScale(2, RoundingMode.HALF_UP);
        } else {
            respuesta = new BigDecimal("0.00");
        }
        return respuesta;
    }
}
