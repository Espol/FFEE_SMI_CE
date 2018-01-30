/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.incloud.ce.bean.facturaExportacion;

import java.util.List;

import ec.incloud.ce.bean.common.Pago;
import ec.incloud.ce.bean.common.TotalImpuesto;

/**
 *
 * @author CSTICORP
 */
public class InfoFacturaExportacion {
    
    private String fechaEmision;
    private String dirEstablecimiento;
    private String contribuyenteEspecial;
    private String obligadoContabilidad;
    private String comercioExterior;//Exportacion
    private String incoTermFactura;//CIF - Exportacion
    private String lugarIncoTerm;//Exportacion
    private String paisOrigen;//Codigo del Pais Origen - Exportacion
    private String puertoEmbarque;//Exportacion
    private String puertoDestino;//Exportacion
    private String paisDestino;//Codigo del Pais Destino - Exportacion
    private String paisAdquisicion;//Codigo del Pais Adquisicion - Exportacion
    private String tipoIdentificacionComprador;
    private String guiaRemision;
    private String razonSocialComprador;
    private String identificacionComprador;
    private String direccionComprador;
    private String totalSinImpuestos;
    private String incoTermTotalSinImpuestos;//Exportacion
    private String totalDescuento;
//    protected String codDocReemb;//Reembolso
//    protected String totalComprobantesReembolso;//Reembolso
//    protected String totalBaseImponibleReembolso;//Reembolso
//    protected String totalImpuestoReembolso;//Reembolso
    private List<TotalImpuesto> totalConImpuestos;
    private String propina;
    private String fleteInternacional;//Exportacion
    private String seguroInternacional;//Exportacion
    private String gastosAduaneros;//Exportacion
    private String gastosTransporteOtros;//Exportacion
    private String importeTotal;
    private String moneda;
    private List<Pago> pagos;//Exportacion

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

    public String getComercioExterior() {
        return comercioExterior;
    }

    public void setComercioExterior(String comercioExterior) {
        this.comercioExterior = comercioExterior;
    }

    public String getIncoTermFactura() {
        return incoTermFactura;
    }

    public void setIncoTermFactura(String incoTermFactura) {
        this.incoTermFactura = incoTermFactura;
    }

    public String getLugarIncoTerm() {
        return lugarIncoTerm;
    }

    public void setLugarIncoTerm(String lugarIncoTerm) {
        this.lugarIncoTerm = lugarIncoTerm;
    }

    public String getPaisOrigen() {
        return paisOrigen;
    }

    public void setPaisOrigen(String paisOrigen) {
        this.paisOrigen = paisOrigen;
    }

    public String getPuertoEmbarque() {
        return puertoEmbarque;
    }

    public void setPuertoEmbarque(String puertoEmbarque) {
        this.puertoEmbarque = puertoEmbarque;
    }

    public String getPuertoDestino() {
        return puertoDestino;
    }

    public void setPuertoDestino(String puertoDestino) {
        this.puertoDestino = puertoDestino;
    }

    public String getPaisDestino() {
        return paisDestino;
    }

    public void setPaisDestino(String paisDestino) {
        this.paisDestino = paisDestino;
    }

    public String getPaisAdquisicion() {
        return paisAdquisicion;
    }

    public void setPaisAdquisicion(String paisAdquisicion) {
        this.paisAdquisicion = paisAdquisicion;
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
            if (identificacionComprador.length() > 16) {
                this.identificacionComprador = identificacionComprador.substring(0, 16);
            } else {
                this.identificacionComprador = identificacionComprador.trim();
            }
        }
    }

    public String getTotalSinImpuestos() {
        return totalSinImpuestos;
    }

    public void setTotalSinImpuestos(String totalSinImpuestos) {
        this.totalSinImpuestos = totalSinImpuestos;
    }

    public String getIncoTermTotalSinImpuestos() {
        return incoTermTotalSinImpuestos;
    }

    public void setIncoTermTotalSinImpuestos(String incoTermTotalSinImpuestos) {
        this.incoTermTotalSinImpuestos = incoTermTotalSinImpuestos;
    }

    public String getTotalDescuento() {
        return totalDescuento;
    }

    public void setTotalDescuento(String totalDescuento) {
        this.totalDescuento = totalDescuento;
    }

    public List<TotalImpuesto> getTotalConImpuestos() {
        return totalConImpuestos;
    }

    public void setTotalConImpuestos(List<TotalImpuesto> totalConImpuestos) {
        this.totalConImpuestos = totalConImpuestos;
    }

    public String getPropina() {
        return propina;
    }

    public void setPropina(String propina) {
        this.propina = propina;
    }

    public String getFleteInternacional() {
        return fleteInternacional;
    }

    public void setFleteInternacional(String fleteInternacional) {
        this.fleteInternacional = fleteInternacional;
    }

    public String getSeguroInternacional() {
        return seguroInternacional;
    }

    public void setSeguroInternacional(String seguroInternacional) {
        this.seguroInternacional = seguroInternacional;
    }

    public String getGastosAduaneros() {
        return gastosAduaneros;
    }

    public void setGastosAduaneros(String gastosAduaneros) {
        this.gastosAduaneros = gastosAduaneros;
    }

    public String getGastosTransporteOtros() {
        return gastosTransporteOtros;
    }

    public void setGastosTransporteOtros(String gastosTransporteOtros) {
        this.gastosTransporteOtros = gastosTransporteOtros;
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

    public String getDireccionComprador() {
        return direccionComprador;
    }

    public void setDireccionComprador(String direccionComprador) {
        this.direccionComprador = direccionComprador;
    }
    
}
