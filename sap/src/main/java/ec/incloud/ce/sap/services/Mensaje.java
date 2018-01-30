/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.incloud.ce.sap.services;

/**
 *
 * @author Joel Povis Ocaña
 */
class Mensaje {
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

    @Override
    public String toString() {
        return new StringBuilder("identificador: " + identificador)
                .append("tipo: ").append(tipo)
                .append("descripcion: ").append(descripcion)
                .append("adicional: ").append(adicional).toString();
    }

}
