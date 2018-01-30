/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.incloud.ce.bean.facturaReembolso;

import java.math.BigDecimal;
import java.util.List;

import ec.incloud.ce.bean.common.AplicacionUtil;
import ec.incloud.ce.bean.common.DetAdicional;
import ec.incloud.ce.bean.common.Impuesto;

/**
 *
 * @author CSTICORP
 */
public class FacturaReembolsoDetalle {
    
    private String codigoPrincipal;
    private String codigoAuxiliar;
    private String descripcion;
    private String cantidad;
    private String precioUnitario;
    private String descuento;
    private String precioTotalSinImpuesto;
    private List<DetAdicional> detallesAdicionales;
    private List<Impuesto> impuestos;

    public String getCodigoPrincipal() {
        return codigoPrincipal;
    }

    public void setCodigoPrincipal(String codigoPrincipal) {
        this.codigoPrincipal = codigoPrincipal;
    }

    public String getCodigoAuxiliar() {
        return codigoAuxiliar;
    }

    public void setCodigoAuxiliar(String codigoAuxiliar) {
        this.codigoAuxiliar = codigoAuxiliar;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(String precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public String getDescuento() {
        return descuento;
    }

    public void setDescuento(String descuento) {
        this.descuento = descuento;
    }

    public String getPrecioTotalSinImpuesto() {
        return precioTotalSinImpuesto;
    }

    public void setPrecioTotalSinImpuesto(String precioTotalSinImpuesto) {
        this.precioTotalSinImpuesto = precioTotalSinImpuesto;
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
    
    public String getSUB_TOTAL() {
        BigDecimal bigSubtotal = new BigDecimal("0.00");
        BigDecimal bigPrecio = new BigDecimal(this.getPrecioUnitario());
        BigDecimal bigCantidad = new BigDecimal(this.getCantidad());
        bigSubtotal = bigSubtotal.add(bigPrecio.multiply(bigCantidad));
        return AplicacionUtil.DosDecimales(bigSubtotal).toString();
    }
}
