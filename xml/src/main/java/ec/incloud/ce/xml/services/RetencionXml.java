/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.xml.services;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import ec.incloud.ce.bean.common.InfoTributaria;
import ec.incloud.ce.bean.retencion.ComprobanteRetencion;
import ec.incloud.ce.bean.retencion.ImpuestoRetenido;
import ec.incloud.ce.bean.retencion.InfoCompRetencion;
import ec.incloud.ce.xml.convert.CampoAdicionalConvert;
import ec.incloud.ce.xml.exception.XmlException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import org.apache.log4j.Logger;

/**
 *
 * @author Joel Povis Ocaña
 */
final class RetencionXml implements XmlServices<ComprobanteRetencion> {

    @SuppressWarnings("rawtypes")
	private static XmlServices instance;
    private final XStream xStream;
    private final Logger log = Logger.getLogger("integrador");

    private RetencionXml() {
        this.xStream = new XStream(new DomDriver("UTF-8"));
        this.xStream.setClassLoader(ComprobanteRetencion.class.getClassLoader());
        this.xStream.alias("comprobanteRetencion", ComprobanteRetencion.class);
        this.xStream.alias("infoTributaria", InfoTributaria.class);
        this.xStream.alias("infoCompRetencion>", InfoCompRetencion.class);
//        xStream.alias("infoCompRetencion>", InfoCompRetencion.class);
//        xStream.alias("impuesto", Impuesto.class);
        this.xStream.alias("impuesto", ImpuestoRetenido.class);
        this.xStream.registerLocalConverter(ComprobanteRetencion.class, "infoAdicional", new CampoAdicionalConvert());
        this.xStream.useAttributeFor("id", String.class);
        this.xStream.useAttributeFor("version", String.class);
        this.xStream.useAttributeFor("nombre", String.class);

        xStream.omitField(ComprobanteRetencion.class, "ds:Signature");
    }

    @SuppressWarnings("unchecked")
	public static XmlServices<ComprobanteRetencion> create() {
        synchronized (RetencionXml.class) {
            if (instance == null) {
                instance = new RetencionXml();
            }
            return instance;
        }
    }

    @Override
    public void generarXml(ComprobanteRetencion comprobante, String pathAbsolute) throws XmlException {
        try {
            log.debug("Escribiendo el XML del Comprobante de Retencion en " + pathAbsolute);
            FileOutputStream fis = new FileOutputStream(new File(pathAbsolute));
            fis.write("<?xml version='1.0' encoding='UTF-8' ?>".getBytes());
            OutputStreamWriter osw = new OutputStreamWriter(fis, "UTF-8");
            this.xStream.toXML(comprobante, osw);
        } catch (Exception ex) {
            log.error("Al escribir XML del Comprobante de Retencion ", ex);
            throw new XmlException(ex.getMessage());
        }
    }

    @Override
    public String generarXml(ComprobanteRetencion comprobante) throws XmlException {
        if (comprobante == null) {
            throw new XmlException("Informacion del comprobante es nula");
        }
        return xStream.toXML(comprobante);
    }

    @Override
    public ComprobanteRetencion getComprobanteDePathArchivo(String pathAbsolute) throws XmlException {
        if (pathAbsolute == null || pathAbsolute.isEmpty()) {
            throw new XmlException("Direccion path XML nulo o vacio");
        }
        File xmlFile = new File(pathAbsolute);
        if (!xmlFile.isFile()) {
            throw new XmlException("Direccion path XML no es valido");
        }
        return (ComprobanteRetencion) xStream.fromXML(xmlFile);
    }

}
