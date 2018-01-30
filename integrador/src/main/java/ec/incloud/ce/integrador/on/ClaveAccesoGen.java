/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.integrador.on;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Joel Povis Ocaña
 */
class ClaveAccesoGen {

    private static ClaveAccesoGen instance;

    public ClaveAccesoGen() {
    }

    public static ClaveAccesoGen create() {
        synchronized (ClaveAccesoGen.class) {
            if (instance == null) {
                instance = new ClaveAccesoGen();
            }
            return instance;
        }
    }

    public String generarClaveAcceso(Date fechaEmision,
            String tipoComprobante,
            String ruc,
            String ambiente,
            String serie,
            String numeroComprobante,
            String codigoNumerico,
            String tipoEmision) {
        int verificador = 0;

        if (ruc != null && ruc.length() < 13) {
            ruc = String.format("%013d", new Object[]{ruc});
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        String fecha = dateFormat.format(fechaEmision);
        String clave = new StringBuilder()
                .append(fecha).append(tipoComprobante).append(ruc)
                .append(ambiente)
                .append(serie)
                .append(numeroComprobante)
                .append(codigoNumerico)
                .append(tipoEmision).toString();

        verificador = this.generarDigitoModulo11(clave);
        clave = clave + verificador;

        return clave;
    }

    private int generarDigitoModulo11(String cadena) {
        int baseMultiplicador = 7;
        int aux[] = new int[cadena.length()];
        int multiplicador = 2;
        int total = 0;
        int verificador = 0;
        for (int i = aux.length - 1; i >= 0; i--) {
            aux[i] = Integer.parseInt((new StringBuilder()).append("").append(cadena.charAt(i)).toString());
            aux[i] = aux[i] * multiplicador;
            if (++multiplicador > baseMultiplicador) {
                multiplicador = 2;
            }
            total += aux[i];
        }

        if (total == 0 || total == 1) {
            verificador = 0;
        } else {
            verificador = 11 - total % 11 != 11 ? 11 - total % 11 : 0;
        }
        if (verificador == 10) {
            verificador = 1;
        }
        return verificador;
    }
}
