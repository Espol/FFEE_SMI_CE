/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.sap.services;

/**
 *
 * @author Incloud Services S.A.C.
 */
public class RespuestaSap {

    private String tipo;
    private String descripcion;

    public RespuestaSap() {
    }

    public RespuestaSap(String tipo, String descripcion) {
        this.tipo = tipo;
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return new StringBuilder("RespuestaSap [")
                .append("tipo: ").append(tipo)
                .append(", descripcion:").append(descripcion)
                .append("]").toString();
    }

}
