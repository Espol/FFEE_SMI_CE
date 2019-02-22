/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.integrador.bean;

import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 *
 * @author Incloud Services S.A.C.
 */
public class Documento {

    private Periodo periodo;
    private Sociedad sociedad;
    private int secuencia;
    private String tipoDocumento;
    private String establecimiento;
    private String puntoEmision;
    private String numero;
    private String serieCorrelativo;
    private Date fechaEmision;
    private Date fechaRegistro;
    private String fechaReferencia;
    private String codigoCliente;
    private String claveAcceso;
    private String xml;//Ruta de XML el cual cambia con la Respuesta del SRI
    private String pdf;
    private boolean ultimo;
    private String estadoSri;
    private String ackSri;
    private String numeroSap;
    private String usuarioSap;
    private String terminal;
    private String mailDestino;
    private BigDecimal importeTotal;

    private String claseDocumento;
    private String nroAutorizacion;
    private String fechaAutorizacion;
    private int escenario;
    private int envioMail;
    private String mensaje;
    private String nombreCliente;
    
    private String estadoSap;
    private String observacionSap;
    
    private Date fechaNoAutorizado;
    
    private int subtipoDoc;
    
    private int anulado;

    private String obsComprobante;

    private int esquemaProc;
    
    private int estadoNotifRechazado;
    
    private String emailPortal;
    
    private String rucCliente;//portal
    
    private String pathXml;//ruta del XML Generado en JAVA

	public Documento() {
        this.periodo = new Periodo();
        this.sociedad = new Sociedad();
        this.importeTotal = new BigDecimal(0);
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public Sociedad getSociedad() {
        return sociedad;
    }

    public void setSociedad(Sociedad sociedad) {
        this.sociedad = sociedad;
    }

    public int getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(int secuencia) {
        this.secuencia = secuencia;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(String establecimiento) {
        this.establecimiento = establecimiento;
    }

    public String getPuntoEmision() {
        return puntoEmision;
    }

    public void setPuntoEmision(String puntoEmision) {
        this.puntoEmision = puntoEmision;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getFechaReferencia() {
        return fechaReferencia;
    }

    public void setFechaReferencia(String fechaReferencia) {
        this.fechaReferencia = fechaReferencia;
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public String getClaveAcceso() {
        return claveAcceso;
    }

    public void setClaveAcceso(String claveAcceso) {
        this.claveAcceso = claveAcceso;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public boolean isUltimo() {
        return ultimo;
    }

    public void setUltimo(boolean ultimo) {
        this.ultimo = ultimo;
    }

    public String getEstadoSri() {
        return estadoSri;
    }

    public void setEstadoSri(String estadoSri) {
        this.estadoSri = estadoSri;
    }

    public String getNumeroSap() {
        return numeroSap;
    }

    public void setNumeroSap(String numeroSap) {
        this.numeroSap = numeroSap;
    }

    public String getUsuarioSap() {
        return usuarioSap;
    }

    public void setUsuarioSap(String usuarioSap) {
        this.usuarioSap = usuarioSap;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getMailDestino() {
        return mailDestino;
    }

    public void setMailDestino(String mailDestino) {
        this.mailDestino = mailDestino;
    }

    public BigDecimal getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(BigDecimal importeTotal) {
        this.importeTotal = importeTotal;
    }

    public String getAckSri() {
        return ackSri;
    }

    public void setAckSri(String ackSri) {
        this.ackSri = ackSri;
    }

    public String getSerieCorrelativo() {
        return serieCorrelativo;
    }

    public void setSerieCorrelativo(String serieCorrelativo) {
        this.serieCorrelativo = serieCorrelativo;
    }

    public int getEnvioMail() {
        return envioMail;
    }

    public void setEnvioMail(int envioMail) {
        this.envioMail = envioMail;
    }

    public String getNroAutorizacion() {
        return nroAutorizacion;
    }

    public void setNroAutorizacion(String nroAutorizacion) {
        this.nroAutorizacion = nroAutorizacion;
    }

    public String getFechaAutorizacion() {
        return fechaAutorizacion;
    }

    public void setFechaAutorizacion(String fechaAutorizacion) {
        this.fechaAutorizacion = fechaAutorizacion;
    }

    public int getEscenario() {
        return escenario;
    }

    public void setEscenario(int escenario) {
        this.escenario = escenario;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getClaseDocumento() {
        return claseDocumento;
    }

    public void setClaseDocumento(String claseDocumento) {
        this.claseDocumento = claseDocumento;
    }

    public String getEstadoSap() {
        return estadoSap;
    }

    public void setEstadoSap(String estadoSap) {
        this.estadoSap = estadoSap;
    }

    public String getObservacionSap() {
        return observacionSap;
    }

    public void setObservacionSap(String observacionSap) {
        this.observacionSap = observacionSap;
    }

    public Date getFechaNoAutorizado() {
		return fechaNoAutorizado;
	}

	public void setFechaNoAutorizado(Date fechaNoAutorizado) {
		this.fechaNoAutorizado = fechaNoAutorizado;
	}

	public int getSubtipoDoc() {
		return subtipoDoc;
	}

	public void setSubtipoDoc(int subtipoDoc) {
		this.subtipoDoc = subtipoDoc;
	}

    public int getAnulado() {
		return anulado;
	}

	public void setAnulado(int anulado) {
		this.anulado = anulado;
	}

	public String getObsComprobante() {
		return obsComprobante;
	}

	public void setObsComprobante(String obsComprobante) {
		this.obsComprobante = obsComprobante;
	}

	public int getEsquemaProc() {
		return esquemaProc;
	}

	public void setEsquemaProc(int esquemaProc) {
		this.esquemaProc = esquemaProc;
	}

	public int getEstadoNotifRechazado() {
		return estadoNotifRechazado;
	}

	public void setEstadoNotifRechazado(int estadoNotifRechazado) {
		this.estadoNotifRechazado = estadoNotifRechazado;
	}
	
	@Override
    public String toString() {
        return new ToStringBuilder(this, CalendarToStringStyle.instance())
                .append("serieCorrelativo", serieCorrelativo)
                .append("numeroSap", numeroSap)
                .append("tipoDocumento", tipoDocumento)
                .append("establecimiento", establecimiento)
                .append("puntoEmision", puntoEmision)
                .append("numero", numero).toString();
    }

	public String getEmailPortal() {
		return emailPortal;
	}

	public void setEmailPortal(String emailPortal) {
		this.emailPortal = emailPortal;
	}

	public String getRucCliente() {
		return rucCliente;
	}

	public void setRucCliente(String rucCliente) {
		this.rucCliente = rucCliente;
	}

	public String getPathXml() {
		return pathXml;
	}

	public void setPathXml(String pathXml) {
		this.pathXml = pathXml;
	}
}
