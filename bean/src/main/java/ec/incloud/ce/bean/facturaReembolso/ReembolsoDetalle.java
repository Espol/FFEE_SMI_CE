/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.incloud.ce.bean.facturaReembolso;

import java.util.List;

import ec.incloud.ce.bean.common.DetalleImpuesto;

/**
 *
 * @author CSTICORP
 */
public class ReembolsoDetalle {
    
    private String tipoIdentificacionProveedorReembolso;
    private String identificacionProveedorReembolso;
    private String codPaisPagoProveedorReembolso;
    private String tipoProveedorReembolso;
    private String codDocReembolso;
    private String estabDocReembolso;
    private String ptoEmiDocReembolso;
    private String secuencialDocReembolso;
    private String fechaEmisionDocReembolso;
    private String numeroautorizacionDocReemb;
    private List<DetalleImpuesto> detalleImpuestos;

    public String getTipoIdentificacionProveedorReembolso() {
        return tipoIdentificacionProveedorReembolso;
    }

    public void setTipoIdentificacionProveedorReembolso(String tipoIdentificacionProveedorReembolso) {
        this.tipoIdentificacionProveedorReembolso = tipoIdentificacionProveedorReembolso;
    }

    public String getIdentificacionProveedorReembolso() {
        return identificacionProveedorReembolso;
    }

    public void setIdentificacionProveedorReembolso(String identificacionProveedorReembolso) {
        this.identificacionProveedorReembolso = identificacionProveedorReembolso;
    }

    public String getCodPaisPagoProveedorReembolso() {
        return codPaisPagoProveedorReembolso;
    }

    public void setCodPaisPagoProveedorReembolso(String codPaisPagoProveedorReembolso) {
        this.codPaisPagoProveedorReembolso = codPaisPagoProveedorReembolso;
    }

    public String getTipoProveedorReembolso() {
        return tipoProveedorReembolso;
    }

    public void setTipoProveedorReembolso(String tipoProveedorReembolso) {
        this.tipoProveedorReembolso = tipoProveedorReembolso;
    }

    public String getCodDocReembolso() {
        return codDocReembolso;
    }

    public void setCodDocReembolso(String codDocReembolso) {
        this.codDocReembolso = codDocReembolso;
    }

    public String getEstabDocReembolso() {
        return estabDocReembolso;
    }

    public void setEstabDocReembolso(String estabDocReembolso) {
        this.estabDocReembolso = estabDocReembolso;
    }

    public String getPtoEmiDocReembolso() {
        return ptoEmiDocReembolso;
    }

    public void setPtoEmiDocReembolso(String ptoEmiDocReembolso) {
        this.ptoEmiDocReembolso = ptoEmiDocReembolso;
    }

    public String getSecuencialDocReembolso() {
        return secuencialDocReembolso;
    }

    public void setSecuencialDocReembolso(String secuencialDocReembolso) {
        this.secuencialDocReembolso = secuencialDocReembolso;
    }

    public String getFechaEmisionDocReembolso() {
        return fechaEmisionDocReembolso;
    }

    public void setFechaEmisionDocReembolso(String fechaEmisionDocReembolso) {
        this.fechaEmisionDocReembolso = fechaEmisionDocReembolso;
    }

    public List<DetalleImpuesto> getDetalleImpuestos() {
        return detalleImpuestos;
    }

    public void setDetalleImpuestos(List<DetalleImpuesto> detalleImpuestos) {
        this.detalleImpuestos = detalleImpuestos;
    }

    public String getNumeroautorizacionDocReemb() {
        return numeroautorizacionDocReemb;
    }

    public void setNumeroautorizacionDocReemb(String numeroautorizacionDocReemb) {
        this.numeroautorizacionDocReemb = numeroautorizacionDocReemb;
    }
    
}
