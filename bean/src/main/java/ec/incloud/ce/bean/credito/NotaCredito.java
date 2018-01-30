/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.incloud.ce.bean.credito;

import ec.incloud.ce.bean.common.CalendarToStringStyle;
import ec.incloud.ce.bean.common.CampoAdicional;
import ec.incloud.ce.bean.common.InfoTributaria;
import java.util.List;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 *
 * @author Usuario
 */
public class NotaCredito {

    private InfoTributaria infoTributaria;
    private InfoNotaCredito infoNotaCredito;
    private List<NotaCreditoDetalle> detalles;
    private List<CampoAdicional> infoAdicional;
    private String id;
    private String version;

    public NotaCredito() {
    }

    public InfoTributaria getInfoTributaria() {
        return infoTributaria;
    }

    public void setInfoTributaria(InfoTributaria infoTributaria) {
        this.infoTributaria = infoTributaria;
    }

    public InfoNotaCredito getInfoNotaCredito() {
        return infoNotaCredito;
    }

    public void setInfoNotaCredito(InfoNotaCredito infoNotaCredito) {
        this.infoNotaCredito = infoNotaCredito;
    }

    public List<NotaCreditoDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<NotaCreditoDetalle> detalles) {
        this.detalles = detalles;
    }

    public List<CampoAdicional> getInfoAdicional() {
        return infoAdicional;
    }

    public void setInfoAdicional(List<CampoAdicional> infoAdicional) {
        this.infoAdicional = infoAdicional;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this, CalendarToStringStyle.instance())
                .append("establecimiento", infoTributaria.getEstab())
                .append("ptoEmision", infoTributaria.getPtoEmi())
                .append("secuencial", infoTributaria.getSecuencial())
                .append("fechaEmision", infoNotaCredito.getFechaEmision()).toString();
    }
}
