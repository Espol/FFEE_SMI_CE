/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.incloud.ce.bean.credito;

import ec.incloud.ce.bean.common.TotalImpuesto;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Rolando
 */
public class InfoNotaCredito {

    protected String fechaEmision;
    protected String dirEstablecimiento;
    protected String tipoIdentificacionComprador;
    protected String razonSocialComprador;
    protected String identificacionComprador;
    protected String contribuyenteEspecial;
    protected String obligadoContabilidad;
    protected String rise;
    protected String codDocModificado;
    protected String numDocModificado;
    protected String fechaEmisionDocSustento;
    protected String totalSinImpuestos;
    protected String valorModificacion;
    protected String moneda;
    protected List<TotalImpuesto> totalConImpuestos;
    protected String motivo;

    public String getCodDocModificado() {
        return codDocModificado;
    }

    public void setCodDocModificado(String codDocModificado) {
        this.codDocModificado = codDocModificado.trim();
    }

    public String getContribuyenteEspecial() {
        return contribuyenteEspecial;
    }

    public void setContribuyenteEspecial(String contribuyenteEspecial) {
        this.contribuyenteEspecial = contribuyenteEspecial.trim();
    }

    public String getDirEstablecimiento() {
        return dirEstablecimiento;
    }

    public void setDirEstablecimiento(String dirEstablecimiento) {
        this.dirEstablecimiento = dirEstablecimiento.trim();
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision.trim();
    }

    public String getFechaEmisionDocSustento() {
        return fechaEmisionDocSustento;
    }

    public void setFechaEmisionDocSustento(String fechaEmisionDocSustento) {
        this.fechaEmisionDocSustento = fechaEmisionDocSustento.trim();
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

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda.trim();
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo.trim();
    }

    public String getNumDocModificado() {
        return numDocModificado;
    }

    public void setNumDocModificado(String numDocModificado) {
        this.numDocModificado = numDocModificado.trim();
    }

    public String getObligadoContabilidad() {
        return obligadoContabilidad;
    }

    public void setObligadoContabilidad(String obligadoContabilidad) {
        this.obligadoContabilidad = obligadoContabilidad.trim();
    }

    public String getRazonSocialComprador() {
        return razonSocialComprador;
    }

    public void setRazonSocialComprador(String razonSocialComprador) {
        this.razonSocialComprador = razonSocialComprador.trim();
    }

    public String getRise() {
        return rise;
    }

    public void setRise(String rise) {
        this.rise = rise.trim();
    }

    public String getTipoIdentificacionComprador() {
        return tipoIdentificacionComprador;
    }

    public void setTipoIdentificacionComprador(String tipoIdentificacionComprador) {
        this.tipoIdentificacionComprador = tipoIdentificacionComprador.trim();
    }

    public List<TotalImpuesto> getTotalConImpuestos() {
        return totalConImpuestos;
    }

    public void setTotalConImpuestos(List<TotalImpuesto> totalConImpuestos) {
        this.totalConImpuestos = totalConImpuestos;
    }

    public String getTotalSinImpuestos() {
        return totalSinImpuestos;
    }

    public void setTotalSinImpuestos(String totalSinImpuestos) {
        this.totalSinImpuestos = totalSinImpuestos.trim();
    }

    public String getValorModificacion() {
        return valorModificacion;
    }

    public void setValorModificacion(String valorModificacion) {
        this.valorModificacion = valorModificacion.trim();
    }

    public String getTotalIva() {
        BigDecimal totalIva = new BigDecimal("0.00");
        for (TotalImpuesto totalImpuesto : totalConImpuestos) {
            totalIva = totalIva.add(new BigDecimal(totalImpuesto.getValor()));
        }
        return totalIva.toString();
    }
}
