/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.incloud.ce.bean.debito;

import ec.incloud.ce.bean.common.Impuesto;
import ec.incloud.ce.bean.common.Pago;

import java.util.List;

/**
 *
 * @author Rolando
 */
public class InfoNotaDebito {

    private String fechaEmision;
    private String dirEstablecimiento;
    private String tipoIdentificacionComprador;
    private String razonSocialComprador;
    private String identificacionComprador;
    private String contribuyenteEspecial;
    private String obligadoContabilidad;
    private String rise;
    public List<Pago> getPagos() {
		return pagos;
	}

	public void setPagos(List<Pago> pagos) {
		this.pagos = pagos;
	}

	private String codDocModificado;
    private String numDocModificado;
    private String fechaEmisionDocSustento;
    private String totalSinImpuestos;
    private List<Impuesto> impuestos;
    private String valorTotal;
    private List<Pago> pagos;

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision.trim();
    }

    public String getDirEstablecimiento() {
        return dirEstablecimiento;
    }

    public void setDirEstablecimiento(String dirEstablecimiento) {
        this.dirEstablecimiento = dirEstablecimiento.trim();
    }

    public String getTipoIdentificacionComprador() {
        return tipoIdentificacionComprador;
    }

    public void setTipoIdentificacionComprador(String tipoIdentificacionComprador) {
        this.tipoIdentificacionComprador = tipoIdentificacionComprador.trim();
    }

    public String getRazonSocialComprador() {
        return razonSocialComprador;
    }

    public void setRazonSocialComprador(String razonSocialComprador) {
        this.razonSocialComprador = razonSocialComprador.trim();
    }

    public String getIdentificacionComprador() {
        return identificacionComprador;
    }

    public void setIdentificacionComprador(String identificacionComprador) { 
        if (identificacionComprador != null) {
            identificacionComprador = identificacionComprador.trim();
            if (identificacionComprador.length() > 13) {
                this.identificacionComprador = identificacionComprador.substring(0, 13);
            } else {
                this.identificacionComprador = identificacionComprador.trim();
            }
        }
    }

    public String getContribuyenteEspecial() {
        return contribuyenteEspecial;
    }

    public void setContribuyenteEspecial(String contribuyenteEspecial) {
        this.contribuyenteEspecial = contribuyenteEspecial.trim();
    }

    public String getObligadoContabilidad() {
        return obligadoContabilidad;
    }

    public void setObligadoContabilidad(String obligadoContabilidad) {
        this.obligadoContabilidad = obligadoContabilidad.trim();
    }

    public String getRise() {
        return rise;
    }

    public void setRise(String rise) {
        this.rise = rise.trim();
    }

    public String getCodDocModificado() {
        return codDocModificado;
    }

    public void setCodDocModificado(String codDocModificado) {
        this.codDocModificado = codDocModificado.trim();
    }

    public String getNumDocModificado() {
        return numDocModificado;
    }

    public void setNumDocModificado(String numDocModificado) {
        this.numDocModificado = numDocModificado.trim();
    }

    public String getFechaEmisionDocSustento() {
        return fechaEmisionDocSustento;
    }

    public void setFechaEmisionDocSustento(String fechaEmisionDocSustento) {
        this.fechaEmisionDocSustento = fechaEmisionDocSustento.trim();
    }

    public String getTotalSinImpuestos() {
        return totalSinImpuestos;
    }

    public void setTotalSinImpuestos(String totalSinImpuestos) {
        this.totalSinImpuestos = totalSinImpuestos.trim();
    }

    public List<Impuesto> getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(List<Impuesto> impuestos) {
        this.impuestos = impuestos;
    }

    public String getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(String valorTotal) {
        this.valorTotal = valorTotal.trim();
    }
}