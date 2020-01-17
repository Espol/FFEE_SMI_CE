package ec.incloud.ce.bean.liquidacionCompra;

import java.util.List;

import ec.incloud.ce.bean.common.DetAdicional;
import ec.incloud.ce.bean.common.Impuesto;

public class LiquidacionCompraDetalle {
	
	private String codigoPrincipal;
    private String codigoAuxiliar;
    private String descripcion;
    private String unidadMedida;
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
	public String getUnidadMedida() {
		return unidadMedida;
	}
	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
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

}
