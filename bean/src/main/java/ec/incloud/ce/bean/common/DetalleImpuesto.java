/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.incloud.ce.bean.common;

/**
 *
 * @author CSTICORP
 */
public class DetalleImpuesto {
    
    private String codigo;
    private String codigoPorcentaje;
    private String tarifa;
    private String baseImponibleReembolso;
    private String impuestoReembolso;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTarifa() {
        return tarifa;
    }

    public void setTarifa(String tarifa) {
        this.tarifa = tarifa;
    }

    public String getBaseImponibleReembolso() {
        return baseImponibleReembolso;
    }

    public void setBaseImponibleReembolso(String baseImponibleReembolso) {
        this.baseImponibleReembolso = baseImponibleReembolso;
    }

    public String getImpuestoReembolso() {
        return impuestoReembolso;
    }

    public void setImpuestoReembolso(String impuestoReembolso) {
        this.impuestoReembolso = impuestoReembolso;
    }

    public String getCodigoPorcentaje() {
        return codigoPorcentaje;
    }

    public void setCodigoPorcentaje(String codigoPorcentaje) {
        this.codigoPorcentaje = codigoPorcentaje;
    }
    
}
