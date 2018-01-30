/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.incloud.ce.bean.facturaReembolso;

import java.util.List;

import ec.incloud.ce.bean.common.Pago;
import ec.incloud.ce.bean.common.TotalImpuesto;

/**
 *
 * @author CSTICORP
 */
public class InfoFacturaReembolso {
    
    protected String fechaEmision;
    protected String dirEstablecimiento;
    protected String contribuyenteEspecial;
    protected String obligadoContabilidad;
    protected String tipoIdentificacionComprador;
    protected String guiaRemision;
    protected String razonSocialComprador;
    protected String identificacionComprador;
    protected String direccionComprador; // campo nuevo
    protected String totalSinImpuestos;
    protected String totalDescuento;
    protected String codDocReembolso;//Reembolso
    protected String totalComprobantesReembolso;//Reembolso
    protected String totalBaseImponibleReembolso;//Reembolso
    protected String totalImpuestoReembolso;//Reembolso
    protected List<TotalImpuesto> totalConImpuestos;
    protected String propina;
    protected String importeTotal;
    protected String moneda;
    protected List<Pago> pagos;

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

    public String getTipoIdentificacionComprador() {
        return tipoIdentificacionComprador;
    }

    public void setTipoIdentificacionComprador(String tipoIdentificacionComprador) {
        this.tipoIdentificacionComprador = tipoIdentificacionComprador;
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

    public String getIdentificacionComprador() {
        return identificacionComprador;
    }

    public void setIdentificacionComprador(String identificacionComprador) {
        if (identificacionComprador != null) {
            if (identificacionComprador.length() > 13) {
                this.identificacionComprador = identificacionComprador.substring(0, 13);
            } else {
                this.identificacionComprador = identificacionComprador.trim();
            }
        }
    }
    
    public String getDireccionComprador() {
		return direccionComprador;
	}

	public void setDireccionComprador(String direccionComprador) {
		this.direccionComprador = direccionComprador;
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

    public List<Pago> getPagos() {
		return pagos;
	}

	public void setPagos(List<Pago> pagos) {
		this.pagos = pagos;
	}

	public String getPropina() {
        return propina;
    }

    public void setPropina(String propina) {
        this.propina = propina;
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
    
}

