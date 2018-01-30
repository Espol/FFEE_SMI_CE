/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.incloud.ce.bean.guia;

import ec.incloud.ce.bean.common.DetAdicional;
import java.util.List;

/**
 *
 * @author Rolando
 */

public class GuiaRemisionDetalle {
    private String codigoInterno;
    private String codigoAdicional;
    private String descripcion;
    private String cantidad;
    private List<DetAdicional> detallesAdicionales;

    public String getCodigoInterno() {
        return codigoInterno;
    }

    public void setCodigoInterno(String codigoInterno) {
        this.codigoInterno = codigoInterno.trim();
    }

    public String getCodigoAdicional() {
        return codigoAdicional;
    }

    public void setCodigoAdicional(String codigoAdicional) {
        this.codigoAdicional = codigoAdicional.trim();
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion.trim();
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad.trim();
    }

    public List<DetAdicional> getDetallesAdicionales() {
        return detallesAdicionales;
    }

    public void setDetallesAdicionales(List<DetAdicional> detallesAdicionales) {
        this.detallesAdicionales = detallesAdicionales;
    }

}
