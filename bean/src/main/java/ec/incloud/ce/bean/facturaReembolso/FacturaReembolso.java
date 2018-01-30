/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.incloud.ce.bean.facturaReembolso;

import java.math.BigDecimal;
import java.util.List;

import ec.incloud.ce.bean.common.AplicacionUtil;
import ec.incloud.ce.bean.common.CampoAdicional;
import ec.incloud.ce.bean.common.Impuesto;
import ec.incloud.ce.bean.common.InfoTributaria;

/**
 *
 * @author CSTICORP
 */
public class FacturaReembolso {
    
    private String id;
    private String version;
    private InfoTributaria infoTributaria;
    private InfoFacturaReembolso infoFactura;
    private List<FacturaReembolsoDetalle> detalles;
    private List<ReembolsoDetalle> reembolsos;//Reembolso
    private List<CampoAdicional> infoAdicional;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public InfoTributaria getInfoTributaria() {
        return infoTributaria;
    }

    public void setInfoTributaria(InfoTributaria infoTributaria) {
        this.infoTributaria = infoTributaria;
    }

    public InfoFacturaReembolso getInfoFactura() {
        return infoFactura;
    }

    public void setInfoFactura(InfoFacturaReembolso infoFactura) {
        this.infoFactura = infoFactura;
    }

    public List<CampoAdicional> getInfoAdicional() {
        return infoAdicional;
    }

    public void setInfoAdicional(List<CampoAdicional> infoAdicional) {
        this.infoAdicional = infoAdicional;
    }

    public List<ReembolsoDetalle> getReembolsos() {
        return reembolsos;
    }

    public void setReembolsos(List<ReembolsoDetalle> reembolsos) {
        this.reembolsos = reembolsos;
    }
    
    public String getIVA_12() {
        BigDecimal iva_12 = new BigDecimal("0.00");
        for (FacturaReembolsoDetalle facturaDetalle : this.getDetalles()) {
            for (Impuesto impuesto : facturaDetalle.getImpuestos()) {
                if (impuesto.getCodigo().equalsIgnoreCase("2") && impuesto.getCodigoPorcentaje().equalsIgnoreCase("2")) {
                    iva_12 = iva_12.add(new BigDecimal(facturaDetalle.getPrecioTotalSinImpuesto()));
                }
            }
        }
        return AplicacionUtil.DosDecimales(iva_12).toString();
    }

    public String getIVA_0() {
        BigDecimal iva0 = new BigDecimal("0.00");
        for (FacturaReembolsoDetalle facturaDetalle : this.getDetalles()) {
            for (Impuesto impuesto : facturaDetalle.getImpuestos()) {
                if (impuesto.getCodigo().equalsIgnoreCase("2") && impuesto.getCodigoPorcentaje().equalsIgnoreCase("0")) {
                    iva0 = iva0.add(new BigDecimal(facturaDetalle.getPrecioTotalSinImpuesto()));
                }
            }
        }
        return AplicacionUtil.DosDecimales(iva0).toString();
    }

    public String getNO_OBJETO_IVA() {
        BigDecimal noObjetoIva = new BigDecimal("0.00");
        for (FacturaReembolsoDetalle facturaDetalle : this.getDetalles()) {
            for (Impuesto impuesto : facturaDetalle.getImpuestos()) {
                if (impuesto.getCodigo().equalsIgnoreCase("2") && impuesto.getCodigoPorcentaje().equalsIgnoreCase("6")) {
                    noObjetoIva = noObjetoIva.add(new BigDecimal(facturaDetalle.getPrecioTotalSinImpuesto()));
                }
            }
        }
        return AplicacionUtil.DosDecimales(noObjetoIva).toString();
    }

    public String getSUB_TOTAL() {
        return this.getInfoFactura().getTotalSinImpuestos();
    }

    public String getTOTAL_DESCUENTO() {
        return this.getInfoFactura().getTotalDescuento();
    }

    public String getVALOR_ICE() {
        BigDecimal valorICE = new BigDecimal("0.00");
        for (FacturaReembolsoDetalle facturaDetalle : this.getDetalles()) {
            for (Impuesto impuesto : facturaDetalle.getImpuestos()) {
                if (impuesto.getCodigo().equalsIgnoreCase("3")) {
                    valorICE = valorICE.add(new BigDecimal(facturaDetalle.getPrecioTotalSinImpuesto()).multiply(new BigDecimal(impuesto.getTarifa())));
                }
            }
        }
        return AplicacionUtil.DosDecimales(valorICE).toString();
    }

    public String getVALOR_IVA() {
        BigDecimal valorIva = new BigDecimal("0.00");
        valorIva = new BigDecimal(this.getIVA_12()).add(new BigDecimal(this.getVALOR_ICE())).multiply(new BigDecimal("0.12"));
        return AplicacionUtil.DosDecimales(valorIva).toString();
    }

    public List<FacturaReembolsoDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<FacturaReembolsoDetalle> detalles) {
        this.detalles = detalles;
    }
}

