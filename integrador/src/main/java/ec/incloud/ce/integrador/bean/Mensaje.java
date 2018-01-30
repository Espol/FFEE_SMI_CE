/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.integrador.bean;

/**
 *
 * @author Incloud Services S.A.C.
 */
public class Mensaje {
    /* 
     Codigo error de SRI
     */

    private int identificador;
    /*
     ERROR       => E
     INFORMATIVO => I
     */
    private String tipo;
    /*
     Titulo del mensaje
     */
    private String descripcion;

    /*
     Descripcion detallada del mensaje
     */
    private String adicional;

    public Mensaje() {
    }

    public Mensaje(String tipo, String descripcion) {
        this.tipo = tipo;
        this.descripcion = descripcion;
    }

    public Mensaje(int identificador, String tipo, String adicional, String descripcion) {
        this.identificador = identificador;
        this.tipo = tipo;
        this.adicional = adicional;
        this.descripcion = descripcion;
    }

    public Mensaje(int identificador, String tipo, String descripcion) {
        this.identificador = identificador;
        this.tipo = tipo;
        this.descripcion = descripcion;
    }

    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
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

    public String getAdicional() {
        return adicional;
    }

    public void setAdicional(String adicional) {
        this.adicional = adicional;
    }

}
