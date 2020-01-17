/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.integrador.bean;

import java.util.Date;

/**
 *
 * @author Joel Povis Ocaña
 */
public class Sociedad {

    private int idSociedad;
    private String ruc;
    private String razonSocial;
    private String pathRoot;
    private String pathCertificado;
    private String claveCertificado;
    private String sapSettings;
    private String mailSettings;
    private String mailNotificacion;
    private int intervalNotifDocRechazado;
    
    private Date vencFirma;
    
    private String mailFactura;
    private String mailRetencion;
    private String mailCredito;
    private String mailDebito;
    private String mailGuia;
    private String mailLiquidacionCompra;
    
    private int intervalNotifFirma;
    private int iniNotifFirma;
    private int procVencFirma;
    
    private int iniTimeAvailableCorrecion;
    
    private String url;
    
    private int esquema;
    
    private int diasFechaExtemporanea;
    
    private int portalImpl;
    
    private String textoRide;

	public Sociedad() {
    }

    public int getIdSociedad() {
        return idSociedad;
    }

    public void setIdSociedad(int idSociedad) {
        this.idSociedad = idSociedad;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getPathRoot() {
        return pathRoot;
    }

    public void setPathRoot(String pathRoot) {
        this.pathRoot = pathRoot;
    }

    public String getPathCertificado() {
        return pathCertificado;
    }

    public void setPathCertificado(String pathCertificado) {
        this.pathCertificado = pathCertificado;
    }

    public String getClaveCertificado() {
        return claveCertificado;
    }

    public void setClaveCertificado(String claveCertificado) {
        this.claveCertificado = claveCertificado;
    }

    public String getSapSettings() {
        return sapSettings;
    }

    public void setSapSettings(String sapSettings) {
        this.sapSettings = sapSettings;
    }

    public String getMailSettings() {
        return mailSettings;
    }

    public void setMailSettings(String mailSettings) {
        this.mailSettings = mailSettings;
    }

    public String getMailNotificacion() {
        return mailNotificacion;
    }

    public int getIntervalNotifDocRechazado() {
		return intervalNotifDocRechazado;
	}

	public void setIntervalNotifDocRechazado(int intervalNotifDocRechazado) {
		this.intervalNotifDocRechazado = intervalNotifDocRechazado;
	}

	public void setMailNotificacion(String mailNotificacion) {
        this.mailNotificacion = mailNotificacion;
    }

	public int getIntervalNotifFirma() {
		return intervalNotifFirma;
	}

	public void setIntervalNotifFirma(int intervalNotifFirma) {
		this.intervalNotifFirma = intervalNotifFirma;
	}

	public Date getVencFirma() {
		return vencFirma;
	}

	public void setVencFirma(Date vencFirma) {
		this.vencFirma = vencFirma;
	}

	public String getMailFactura() {
		return mailFactura;
	}

	public void setMailFactura(String mailFactura) {
		this.mailFactura = mailFactura;
	}

	public String getMailRetencion() {
		return mailRetencion;
	}

	public void setMailRetencion(String mailRetencion) {
		this.mailRetencion = mailRetencion;
	}

	public String getMailCredito() {
		return mailCredito;
	}

	public void setMailCredito(String mailCredito) {
		this.mailCredito = mailCredito;
	}

	public String getMailDebito() {
		return mailDebito;
	}

	public void setMailDebito(String mailDebito) {
		this.mailDebito = mailDebito;
	}

	public String getMailGuia() {
		return mailGuia;
	}

	public void setMailGuia(String mailGuia) {
		this.mailGuia = mailGuia;
	}

	public int getIniNotifFirma() {
		return iniNotifFirma;
	}

	public void setIniNotifFirma(int iniNotifFirma) {
		this.iniNotifFirma = iniNotifFirma;
	}

	public int getProcVencFirma() {
		return procVencFirma;
	}

	public void setProcVencFirma(int procVencFirma) {
		this.procVencFirma = procVencFirma;
	}

	public int getIniTimeAvailableCorrecion() {
		return iniTimeAvailableCorrecion;
	}

	public void setIniTimeAvailableCorrecion(int iniTimeAvailableCorrecion) {
		this.iniTimeAvailableCorrecion = iniTimeAvailableCorrecion;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getEsquema() {
		return esquema;
	}

	public void setEsquema(int esquema) {
		this.esquema = esquema;
	}
	
	public int getDiasFechaExtemporanea() {
		return diasFechaExtemporanea;
	}

	public void setDiasFechaExtemporanea(int diasFechaExtemporanea) {
		this.diasFechaExtemporanea = diasFechaExtemporanea;
	}

	public int getPortalImpl() {
		return portalImpl;
	}

	public void setPortalImpl(int portalImpl) {
		this.portalImpl = portalImpl;
	}
	
	public String getTextoRide() {
		return textoRide;
	}

	public void setTextoRide(String textoRide) {
		this.textoRide = textoRide;
	}

	public String getMailLiquidacionCompra() {
		return mailLiquidacionCompra;
	}

	public void setMailLiquidacionCompra(String mailLiquidacionCompra) {
		this.mailLiquidacionCompra = mailLiquidacionCompra;
	}
}
