/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.integrador.on;

import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author Incloud Services S.A.C.
 */
class MensajeServices {

    private final String MSG_XML = "conf_integrador/mensaje.xml";
    private final Logger log = Logger.getLogger(MensajeServices.class);
    private static MensajeServices INSTANCE;
    private String[] CODE;
    private String[] EST;
    private String[] MSG;
    private String[] GEN;

    public MensajeServices() {
        try {
            XMLConfiguration catag = new XMLConfiguration(MSG_XML);
            this.CODE = catag.getStringArray("mensaje.codigo");
            this.MSG = catag.getStringArray("mensaje.descripcion");
            this.GEN = catag.getStringArray("mensaje.generacion");
            this.EST = catag.getStringArray("mensaje.estado");
        } catch (Exception ex) {
            log.error("Error al cargar lista de mensajes", ex);
        }
    }

    static MensajeServices getInstance() {
        synchronized (MensajeServices.class) {
            if (INSTANCE == null) {
                INSTANCE = new MensajeServices();
            }

            return INSTANCE;
        }
    }

    public String getMensaje(String estado) {
        if (this.EST == null) {
            return "-";
        }

        int i = ArrayUtils.indexOf(this.EST, estado);
        return (i == -1) ? "-" : this.MSG[i];
    }

    public boolean getGenerar(String estado) {
        if (this.EST == null) {
            return true;
        }

        int i = ArrayUtils.indexOf(this.EST, estado);
        return (i == -1) ? true : Boolean.valueOf(this.GEN[i]);
    }

    public String getCodigo(String estado) {
        if (this.EST == null) {
            return "-";
        }

        int i = ArrayUtils.indexOf(this.EST, estado);
        return (i == -1) ? "-" : this.CODE[i];
    }
}
