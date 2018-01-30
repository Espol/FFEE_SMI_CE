/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.bean.factura;

import ec.incloud.ce.bean.common.CalendarToStringStyle;
import ec.incloud.ce.bean.common.CampoAdicional;
import ec.incloud.ce.bean.common.InfoTributaria;
import java.util.List;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 *
 * @author Incloud Services S.A.C.
 */
public class Factura {

    private String id;
    private String version;
    private InfoTributaria infoTributaria;
    private InfoFactura infoFactura;
    private List<FacturaDetalle> detalles;
    private List<CampoAdicional> infoAdicional;

    public Factura() {
    }

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

    public InfoFactura getInfoFactura() {
        return infoFactura;
    }

    public void setInfoFactura(InfoFactura infoFactura) {
        this.infoFactura = infoFactura;
    }

    public List<FacturaDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<FacturaDetalle> detalles) {
        this.detalles = detalles;
    }

    public List<CampoAdicional> getInfoAdicional() {
        return infoAdicional;
    }

    public void setInfoAdicional(List<CampoAdicional> infoAdicional) {
        this.infoAdicional = infoAdicional;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, CalendarToStringStyle.instance())
                .append("establecimiento", infoTributaria.getEstab())
                .append("ptoEmision", infoTributaria.getPtoEmi())
                .append("secuencial", infoTributaria.getSecuencial())
                .append("fechaEmision", infoFactura.getFechaEmision()).toString();
    }
}
