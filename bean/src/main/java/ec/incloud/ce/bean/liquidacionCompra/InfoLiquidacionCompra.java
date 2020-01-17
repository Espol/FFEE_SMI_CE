package ec.incloud.ce.bean.liquidacionCompra;

import java.util.List;

import ec.incloud.ce.bean.common.Pago;
import ec.incloud.ce.bean.common.TotalImpuesto;

public class InfoLiquidacionCompra {
	
	private String fechaEmision;
    private String dirEstablecimiento;
    private String contribuyenteEspecial;
    private String obligadoContabilidad;
    private String tipoIdentificacionProveedor;
    private String guiaRemision;
    private String razonSocialComprador;
    private String identificacionProveedor;
    private String direccionProveedor;
    private String totalSinImpuestos;
    private String totalDescuento;
    private String codDocReembolso;
    private String totalComprobantesReembolso;
    private String totalBaseImponibleReembolso;
    private String totalImpuestoReembolso;
    private List<TotalImpuesto> totalConImpuestos;
    private String importeTotal;
    private String moneda;    
    private List<Pago> pagos;
    
	public String getFechaEmision() {
		return fechaEmision;
	}
	public void setFechaEmision(String fechaEmision) {
		this.fechaEmision = fechaEmision;
	}
	public String getDirEstablecimiento() {
		return dirEstablecimiento;
	}
	public void setDirEstablecimiento(String dirEstablecimiento) {
		this.dirEstablecimiento = dirEstablecimiento;
	}
	public String getContribuyenteEspecial() {
		return contribuyenteEspecial;
	}
	public void setContribuyenteEspecial(String contribuyenteEspecial) {
		this.contribuyenteEspecial = contribuyenteEspecial;
	}
	public String getObligadoContabilidad() {
		return obligadoContabilidad;
	}
	public void setObligadoContabilidad(String obligadoContabilidad) {
		this.obligadoContabilidad = obligadoContabilidad;
	}
	public String getTipoIdentificacionProveedor() {
		return tipoIdentificacionProveedor;
	}
	public void setTipoIdentificacionProveedor(String tipoIdentificacionProveedor) {
		this.tipoIdentificacionProveedor = tipoIdentificacionProveedor;
	}
	public String getGuiaRemision() {
		return guiaRemision;
	}
	public void setGuiaRemision(String guiaRemision) {
		this.guiaRemision = guiaRemision;
	}
	public String getRazonSocialComprador() {
		return razonSocialComprador;
	}
	public void setRazonSocialComprador(String razonSocialComprador) {
		this.razonSocialComprador = razonSocialComprador;
	}
	public String getIdentificacionProveedor() {
		return identificacionProveedor;
	}
	public void setIdentificacionProveedor(String identificacionProveedor) {
		this.identificacionProveedor = identificacionProveedor;
	}
	public String getDireccionProveedor() {
		return direccionProveedor;
	}
	public void setDireccionProveedor(String direccionProveedor) {
		this.direccionProveedor = direccionProveedor;
	}
	public String getTotalSinImpuestos() {
		return totalSinImpuestos;
	}
	public void setTotalSinImpuestos(String totalSinImpuestos) {
		this.totalSinImpuestos = totalSinImpuestos;
	}
	public String getTotalDescuento() {
		return totalDescuento;
	}
	public void setTotalDescuento(String totalDescuento) {
		this.totalDescuento = totalDescuento;
	}
	public String getCodDocReembolso() {
		return codDocReembolso;
	}
	public void setCodDocReembolso(String codDocReembolso) {
		this.codDocReembolso = codDocReembolso;
	}
	public String getTotalComprobantesReembolso() {
		return totalComprobantesReembolso;
	}
	public void setTotalComprobantesReembolso(String totalComprobantesReembolso) {
		this.totalComprobantesReembolso = totalComprobantesReembolso;
	}
	public String getTotalBaseImponibleReembolso() {
		return totalBaseImponibleReembolso;
	}
	public void setTotalBaseImponibleReembolso(String totalBaseImponibleReembolso) {
		this.totalBaseImponibleReembolso = totalBaseImponibleReembolso;
	}
	public String getTotalImpuestoReembolso() {
		return totalImpuestoReembolso;
	}
	public void setTotalImpuestoReembolso(String totalImpuestoReembolso) {
		this.totalImpuestoReembolso = totalImpuestoReembolso;
	}
	public List<TotalImpuesto> getTotalConImpuestos() {
		return totalConImpuestos;
	}
	public void setTotalConImpuestos(List<TotalImpuesto> totalConImpuestos) {
		this.totalConImpuestos = totalConImpuestos;
	}
	public String getImporteTotal() {
		return importeTotal;
	}
	public void setImporteTotal(String importeTotal) {
		this.importeTotal = importeTotal;
	}
	public String getMoneda() {
		return moneda;
	}
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}
	public List<Pago> getPagos() {
		return pagos;
	}
	public void setPagos(List<Pago> pagos) {
		this.pagos = pagos;
	}
}
