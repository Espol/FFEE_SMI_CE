/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.integrador.bean;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Incloud Services S.A.C.
 */
public class Respuesta {

    private String estado;
    private List<Mensaje> lstMensaje;

    public Respuesta() {
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<Mensaje> getLstMensaje() {
        return lstMensaje;
    }

    public void setLstMensaje(List<Mensaje> lstMensaje) {
        this.lstMensaje = lstMensaje;
    }

    public void addMensaje(Mensaje mensaje) {
        if (mensaje != null) {
            if (getLstMensaje() == null) {
                this.lstMensaje = new ArrayList<>();
            }
            getLstMensaje().add(mensaje);
        }
    }

}
