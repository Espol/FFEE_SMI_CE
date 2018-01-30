/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.incloud.ce.bean.retencion;

/**
 *
 * @author Joel
 */
public class ImpuestoRetenido{
    private String codigo;
    private String codigoRetencion;
    private String baseImponible;
    private String porcentajeRetener;
    private String valorRetenido;
    private String codDocSustento;
    private String numDocSustento;
    private String fechaEmisionDocSustento;

    public ImpuestoRetenido() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
    	this.codigo = codigo==null?null:codigo.trim();
    }

    public String getCodigoRetencion() {
        return codigoRetencion;
    }

    public void setCodigoRetencion(String codigoRetencion) {
    	this.codigoRetencion = codigoRetencion==null?null:codigoRetencion.trim();
    }

    public String getBaseImponible() {
        return baseImponible;
    }

    public void setBaseImponible(String baseImponible) {
    	this.baseImponible = baseImponible==null?null:baseImponible.trim();
    }

    public String getPorcentajeRetener() {
        return porcentajeRetener;
    }

    public void setPorcentajeRetener(String porcentajeRetener) {
    	this.porcentajeRetener = porcentajeRetener==null?null:porcentajeRetener.trim();
    }

    public String getValorRetenido() {
        return valorRetenido;
    }

    public void setValorRetenido(String valorRetenido) {
    	this.valorRetenido = valorRetenido==null?valorRetenido:valorRetenido.trim();
    }

    public String getCodDocSustento() {
        return codDocSustento;
    }

    public void setCodDocSustento(String codDocSustento) {
    	this.codDocSustento = codDocSustento==null?null:codDocSustento.trim();
    }

    public String getNumDocSustento() {
        return numDocSustento;
    }
    
    public void setNumDocSustento(String numDocSustento) {
    	this.numDocSustento = numDocSustento==null?null:numDocSustento.trim();
    }

    public String getFechaEmisionDocSustento() {
        return fechaEmisionDocSustento;
    }

    public void setFechaEmisionDocSustento(String fechaEmisionDocSustento) {
    	this.fechaEmisionDocSustento = fechaEmisionDocSustento==null?null:fechaEmisionDocSustento.trim();
    }
    
}
