/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.incloud.ce.bean.common;

/**
 *
 * @author Usuario
 */
public class Impuesto {
    private String codigo;
    private String codigoPorcentaje;
    private String tarifa;
    private String baseImponible;
    private String valor;
//    private String codigoRetenido;

    public Impuesto() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigoPorcentaje() {
        return codigoPorcentaje;
    }

    public void setCodigoPorcentaje(String codigoPorcentaje) {
        this.codigoPorcentaje = codigoPorcentaje;
    }

    public String getTarifa() {
        return tarifa;
    }

    public void setTarifa(String tarifa) {
        this.tarifa = tarifa;
    }

    public String getBaseImponible() {
        return baseImponible;
    }

    public void setBaseImponible(String baseImponible) {
        this.baseImponible = baseImponible;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

//    public String getCodigoRetenido() {
//        return codigoRetenido;
//    }
//
//    public void setCodigoRetenido(String codigoRetenido) {
//        this.codigoRetenido = codigoRetenido;
//    }
    
    
}
