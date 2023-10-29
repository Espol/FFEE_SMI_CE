/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.incloud.ce.bean.common;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author Usuario
 */
public class AplicacionUtil {

    @SuppressWarnings("null")
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
