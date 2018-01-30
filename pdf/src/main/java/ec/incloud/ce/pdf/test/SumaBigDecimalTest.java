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
public class SumaBigDecimalTest {
    public static void main(String... arg){
        BigDecimal decimal1 = new BigDecimal("20");
        BigDecimal decimal2 = new BigDecimal("20");
        
        System.out.println("" + decimal1.add(decimal2));
    }
}
