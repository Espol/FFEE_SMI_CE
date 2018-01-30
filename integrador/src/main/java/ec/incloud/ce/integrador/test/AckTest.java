/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.integrador.test;

import ec.incloud.ce.integrador.bean.AckSRI;
import ec.incloud.ce.integrador.util.AckSRIUtil;

/**
 *
 * @author Usuario
 */
public class AckTest {

    public static void main(String[] args) {
        String x = "<ackSRI>\n"
                + "  <estado>NO AUTORIZADO</estado>\n"
                + "  <claveAccesoConsultada>1509201504099012918500110014020000000070000000419</claveAccesoConsultada>\n"
                + "  <fechaAutorizacion>16/09/2015 10:58:00</fechaAutorizacion>\n"
                + "  <mensajes>\n"
                + "    <mensaje>\n"
                + "      <identificador>58</identificador>\n"
                + "      <tipo>ERROR</tipo>\n"
                + "      <descripcion>ERROR EN LA ESTRUCTURA DE LA CLAVE DE ACCESO</descripcion>\n"
                + "      <adicional>El tipo de comprobante 04 contenido en la clave de acceso no corresponde al de la etiqueta 5</adicional>\n"
                + "    </mensaje>\n"
                + "    <mensaje>\n"
                + "      <identificador>60</identificador>\n"
                + "      <tipo>INFORMATIVO</tipo>\n"
                + "      <descripcion>ESTE PROCESO FUE REALIZADO EN EL AMBIENTE DE PRUEBAS</descripcion>\n"
                + "    </mensaje>\n"
                + "  </mensajes>\n"
                + "</ackSRI>";

        AckSRI ack = AckSRIUtil.getInstance().toObject(x);
        System.out.println("" + ack);
    }
}
