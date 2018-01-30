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
public class SapSettings {

    private String mandante;
    private String usuario;
    private String contrasena;
    private String idioma;
    private String ipServidor;
    private String numeroInstancia;
    private String idSistema;
    private String sapRouter;

    public String getMandante() {
        return mandante;
    }

    public void setMandante(String mandante) {
        this.mandante = mandante;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getIpServidor() {
        return ipServidor;
    }

    public void setIpServidor(String ipServidor) {
        this.ipServidor = ipServidor;
    }

    public String getNumeroInstancia() {
        return numeroInstancia;
    }

    public void setNumeroInstancia(String numeroInstancia) {
        this.numeroInstancia = numeroInstancia;
    }

    public String getIdSistema() {
        return idSistema;
    }

    public void setIdSistema(String idSistema) {
        this.idSistema = idSistema;
    }

    public String getSapRouter() {
        return sapRouter;
    }

    public void setSapRouter(String sapRouter) {
        this.sapRouter = sapRouter;
    }

}
