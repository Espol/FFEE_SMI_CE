/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.incloud.ce.integrador.test;

import ec.incloud.ce.integrador.util.DateUtil;
import java.util.Date;

/**
 *
 * @author Usuario
 */
public class FechaGeneracionTest {
    public static void main(String... arg){
        Date date = DateUtil.getDateFromString("15-08-2015");
        System.out.println("" + date);
    }
}
