/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.incloud.ce.bean.debito;

import ec.incloud.ce.bean.common.CalendarToStringStyle;
import ec.incloud.ce.bean.common.CampoAdicional;
import ec.incloud.ce.bean.common.InfoTributaria;
import java.util.List;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 *
 * @author Usuario
 */
public class NotaDebito {

    private String id;
    private String version;
    private InfoTributaria infoTributaria;
    private InfoNotaDebito infoNotaDebito;
    private List<Motivo> motivos;
    private List<CampoAdicional> infoAdicional;

    public NotaDebito() {
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

    public InfoNotaDebito getInfoNotaDebito() {
        return infoNotaDebito;
    }

    public void setInfoNotaDebito(InfoNotaDebito infoNotaDebito) {
        this.infoNotaDebito = infoNotaDebito;
    }

    public List<Motivo> getMotivos() {
        return motivos;
    }

    public void setMotivos(List<Motivo> motivos) {
        this.motivos = motivos;
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
                .append("fechaEmision", infoNotaDebito.getFechaEmision()).toString();
    }
}
