/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.incloud.ce.pdf.test;

import java.math.BigDecimal;

/**
 *
 * @author Usuario
 */
public class EquasTest {
    public static void main(String... arg){
       BigDecimal[] impuesto = {new BigDecimal(0), new BigDecimal(0), new BigDecimal(0)};
        for (BigDecimal bd : impuesto) {
            
            System.out.println("" + bd.toString());
        }
    }
}
