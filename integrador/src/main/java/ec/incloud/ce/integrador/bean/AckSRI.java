/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.integrador.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 *
 * @author Incloud Services S.A.C.
 */
public class AckSRI {

    private String estado;
    private String claveAccesoConsultada;
    private String numeroAutorizacion;
    private String fechaAutorizacion;
    @XStreamAlias("mensajes")
    private List<Mensaje> mensaje;

    public AckSRI() {
        this.mensaje = new ArrayList<>();
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<Mensaje> getMensaje() {
        return mensaje;
    }

    public void setMensaje(List<Mensaje> mensaje) {
        this.mensaje = mensaje;
    }

    public void addMensaje(int identificador, String tipo, String adicional, String descripcion) {
        this.mensaje.add(new Mensaje(identificador, tipo, adicional, descripcion));
    }

    public String getClaveAccesoConsultada() {
        return claveAccesoConsultada;
    }

    public void setClaveAccesoConsultada(String claveAccesoConsultada) {
        this.claveAccesoConsultada = claveAccesoConsultada;
    }

    public String getNumeroAutorizacion() {
        return numeroAutorizacion;
    }

    public void setNumeroAutorizacion(String numeroAutorizacion) {
        this.numeroAutorizacion = numeroAutorizacion;
    }

    public String getFechaAutorizacion() {
        return fechaAutorizacion;
    }

    public void setFechaAutorizacion(String fechaAutorizacion) {
        this.fechaAutorizacion = fechaAutorizacion;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, CalendarToStringStyle.instance())
                .append("estado", estado)
                .append("numeroAutorizacion", numeroAutorizacion)
                .append("fechaAutorizacion", fechaAutorizacion)
                .append("mensaje", mensaje).toString();
    }
}
