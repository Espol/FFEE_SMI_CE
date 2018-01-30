package ec.incloud.ce.pdf.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class FormatNumberUtil {
	
	public static String formatMilDecimal(String numeroDecimal) {
        DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
        String resultado;
        simbolo.setDecimalSeparator('.');
        simbolo.setGroupingSeparator(',');
        String decimal = obtenerCountDecimal(numeroDecimal);
        DecimalFormat formateador = new DecimalFormat("###,##0." + decimal, simbolo);
        try {
            double numero = Double.parseDouble(numeroDecimal);
            resultado = formateador.format(numero);
        } catch (Exception e) {
            resultado = numeroDecimal;
        }
        return resultado;
    }
    
    private static String obtenerCountDecimal(String numero) {
        String decimal = "";
        String punto = "\\.";
        String[] part = numero.split(punto);
        int count = 0;
        if (part.length > 1) {
            count = part[1].length();
        } else {
            decimal = "#";
        }
        for (int i = 0; i < count; i++) {
            decimal += "0";
        }
        return decimal;
    }

}
