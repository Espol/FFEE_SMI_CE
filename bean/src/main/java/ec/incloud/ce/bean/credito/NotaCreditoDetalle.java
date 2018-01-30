/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.incloud.ce.bean.credito;

import ec.incloud.ce.bean.common.DetAdicional;
import ec.incloud.ce.bean.common.Impuesto;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class NotaCreditoDetalle {

    private String codigoInterno;
    private String codigoAdicional;
    private String descripcion;
    private String cantidad;
    private String precioUnitario;
    private String descuento;
    private String precioTotalSinImpuesto;
    private List<DetAdicional> detallesAdicionales;
    private List<Impuesto> impuestos;

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getCodigoAdicional() {
        return codigoAdicional;
    }

    public void setCodigoAdicional(String codigoAdicional) {
        this.codigoAdicional = codigoAdicional.trim();
    }

    public String getCodigoInterno() {
        return codigoInterno;
    }

    public void setCodigoInterno(String codigoInterno) {
        this.codigoInterno = codigoInterno.trim();
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion.trim();
    }

    public String getDescuento() {
        return descuento;
    }

    public void setDescuento(String descuento) {
        this.descuento = descuento;
    }

    public List<DetAdicional> getDetallesAdicionales() {
        return detallesAdicionales;
    }

    public void setDetallesAdicionales(List<DetAdicional> detallesAdicionales) {
        this.detallesAdicionales = detallesAdicionales;
    }

    public List<Impuesto> getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(List<Impuesto> impuestos) {
        this.impuestos = impuestos;
    }

    public String getPrecioTotalSinImpuesto() {
        return precioTotalSinImpuesto;
    }

    public void setPrecioTotalSinImpuesto(String precioTotalSinImpuesto) {
        this.precioTotalSinImpuesto = precioTotalSinImpuesto;
    }

    public String getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(String precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
}
