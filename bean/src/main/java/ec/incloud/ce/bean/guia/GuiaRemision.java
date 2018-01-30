/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.incloud.ce.bean.guia;

import ec.incloud.ce.bean.common.CalendarToStringStyle;
import ec.incloud.ce.bean.common.CampoAdicional;
import ec.incloud.ce.bean.common.InfoTributaria;
import java.util.List;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 *
 * @author Usuario
 */
public class GuiaRemision {

    private InfoTributaria infoTributaria;
    private InfoGuiaRemision infoGuiaRemision;
    private List<Destinatario> destinatarios;
    private String id;
    private String version;
    private List<CampoAdicional> infoAdicional;

    public GuiaRemision() {
    }

    public InfoTributaria getInfoTributaria() {
        return infoTributaria;
    }

    public void setInfoTributaria(InfoTributaria infoTributaria) {
        this.infoTributaria = infoTributaria;
    }

    public InfoGuiaRemision getInfoGuiaRemision() {
        return infoGuiaRemision;
    }

    public void setInfoGuiaRemision(InfoGuiaRemision infoGuiaRemision) {
        this.infoGuiaRemision = infoGuiaRemision;
    }

    public List<Destinatario> getDestinatarios() {
        return destinatarios;
    }

    public void setDestinatarios(List<Destinatario> destinatarios) {
        this.destinatarios = destinatarios;
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
                .append("fechaIniTransporte", infoGuiaRemision.getFechaIniTransporte()).toString();
    }
}
