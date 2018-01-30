/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.incloud.ce.bean.guia;

import java.util.List;

/**
 *
 * @author Usuario
 */
public class Destinatario {

    private String identificacionDestinatario;
    private String razonSocialDestinatario;
    private String dirDestinatario;
    private String motivoTraslado;
    private String docAduaneroUnico;
    private String codEstabDestino;
    private String ruta;
    private String codDocSustento;
    private String numDocSustento;
    private String numAutDocSustento;
    private String fechaEmisionDocSustento;
    private List<GuiaRemisionDetalle> detalles;

    public String getCodDocSustento() {
        return codDocSustento;
    }

    public void setCodDocSustento(String codDocSustento) {
        this.codDocSustento = codDocSustento.trim();
    }

    public String getCodEstabDestino() {
        return codEstabDestino;
    }

    public void setCodEstabDestino(String codEstabDestino) {
        this.codEstabDestino = codEstabDestino.trim();
    }

    public List<GuiaRemisionDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<GuiaRemisionDetalle> detalles) {
        this.detalles = detalles;
    }

    public String getDirDestinatario() {
        return dirDestinatario;
    }

    public void setDirDestinatario(String dirDestinatario) {
        this.dirDestinatario = dirDestinatario.trim();
    }

    public String getDocAduaneroUnico() {
        return docAduaneroUnico;
    }

    public void setDocAduaneroUnico(String docAduaneroUnico) {
        this.docAduaneroUnico = docAduaneroUnico.trim();
    }

    public String getFechaEmisionDocSustento() {
        return fechaEmisionDocSustento;
    }

    public void setFechaEmisionDocSustento(String fechaEmisionDocSustento) {
        this.fechaEmisionDocSustento = fechaEmisionDocSustento.trim();
    }

    public String getIdentificacionDestinatario() {
        return identificacionDestinatario;
    }

    public void setIdentificacionDestinatario(String identificacionDestinatario) {
        if (identificacionDestinatario != null) {
            identificacionDestinatario = identificacionDestinatario.trim();
            if (identificacionDestinatario.length() > 13) {
                this.identificacionDestinatario = identificacionDestinatario.substring(0, 13);
            } else {
                this.identificacionDestinatario = identificacionDestinatario.trim();
            }
        }
    }

    public String getMotivoTraslado() {
        return motivoTraslado;
    }

    public void setMotivoTraslado(String motivoTraslado) {
        this.motivoTraslado = motivoTraslado.trim();
    }

    public String getNumAutDocSustento() {
        return numAutDocSustento;
    }

    public void setNumAutDocSustento(String numAutDocSustento) {
        this.numAutDocSustento = numAutDocSustento.trim();
    }

    public String getNumDocSustento() {
        return numDocSustento;
    }

    public void setNumDocSustento(String numDocSustento) {
        this.numDocSustento = numDocSustento.trim();
    }

    public String getRazonSocialDestinatario() {
        return razonSocialDestinatario;
    }

    public void setRazonSocialDestinatario(String razonSocialDestinatario) {
        this.razonSocialDestinatario = razonSocialDestinatario.trim();
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta.trim();
    }
}
