/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.incloud.ce.bean.retencion;

import ec.incloud.ce.bean.common.CalendarToStringStyle;
import ec.incloud.ce.bean.common.CampoAdicional;
import ec.incloud.ce.bean.common.InfoTributaria;
import java.util.List;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 *
 * @author Usuario
 */
public class ComprobanteRetencion {

    private InfoTributaria infoTributaria;
    private InfoCompRetencion infoCompRetencion;
    private List<ImpuestoRetenido> impuestos;
    private List<CampoAdicional> infoAdicional;
    private String id;
    private String version;

    public ComprobanteRetencion() {
    }

    public InfoTributaria getInfoTributaria() {
        return infoTributaria;
    }

    public void setInfoTributaria(InfoTributaria infoTributaria) {
        this.infoTributaria = infoTributaria;
    }

    public InfoCompRetencion getInfoCompRetencion() {
        return infoCompRetencion;
    }

    public void setInfoCompRetencion(InfoCompRetencion infoCompRetencion) {
        this.infoCompRetencion = infoCompRetencion;
    }

    public List<ImpuestoRetenido> getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(List<ImpuestoRetenido> impuestos) {
        this.impuestos = impuestos;
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
                .append("fechaIniTransporte", infoCompRetencion.getFechaEmision()).toString();
    }
}
