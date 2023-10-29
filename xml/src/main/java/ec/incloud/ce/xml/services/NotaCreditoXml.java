/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.xml.services;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import ec.incloud.ce.bean.common.DetAdicional;
import ec.incloud.ce.bean.common.Impuesto;
import ec.incloud.ce.bean.common.InfoTributaria;
import ec.incloud.ce.bean.common.TotalImpuesto;
import ec.incloud.ce.bean.credito.InfoNotaCredito;
import ec.incloud.ce.bean.credito.NotaCredito;
import ec.incloud.ce.bean.credito.NotaCreditoDetalle;
import ec.incloud.ce.xml.convert.CampoAdicionalConvert;
import ec.incloud.ce.xml.convert.DetAdicionalConvert;
import ec.incloud.ce.xml.exception.XmlException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import org.apache.log4j.Logger;

/**
 *
 * @author Joel Povis Ocaña
 */
final class NotaCreditoXml implements XmlServices<NotaCredito> {

    @SuppressWarnings("rawtypes")
	private static XmlServices instance;
    private final XStream xStream;
    private final Logger log = Logger.getLogger("integrador");

    private NotaCreditoXml() {
        this.xStream = new XStream(new DomDriver("UTF-8"));
        this.xStream.setClassLoader(NotaCredito.class.getClassLoader());
        this.xStream.alias("notaCredito", NotaCredito.class);
        this.xStream.alias("infoTributaria", InfoTributaria.class);
        this.xStream.alias("infoNotaCredito", InfoNotaCredito.class);
        this.xStream.alias("detalle", NotaCreditoDetalle.class);
        this.xStream.alias("detAdicional", DetAdicional.class);
        this.xStream.alias("impuesto", Impuesto.class);
        this.xStream.alias("totalImpuesto", TotalImpuesto.class);
        this.xStream.registerLocalConverter(NotaCredito.class, "infoAdicional", new CampoAdicionalConvert());
        this.xStream.registerLocalConverter(NotaCreditoDetalle.class, "detallesAdicionales", new DetAdicionalConvert());
        this.xStream.useAttributeFor("id", String.class);
        this.xStream.useAttributeFor("version", String.class);
        this.xStream.useAttributeFor("nombre", String.class);

        xStream.omitField(NotaCredito.class, "ds:Signature");
    }

    @SuppressWarnings("unchecked")
	public static XmlServices<NotaCredito> create() {
        synchronized (NotaCreditoXml.class) {
            if (instance == null) {
                instance = new NotaCreditoXml();
            }
            return instance;
        }
    }

    @Override
    public void generarXml(NotaCredito comprobante, String pathAbsolute) throws XmlException {
        try {
            log.debug("Escribiendo el XML de la Nota de Credito en " + pathAbsolute);
            FileOutputStream fis = new FileOutputStream(new File(pathAbsolute));
            fis.write("<?xml version='1.0' encoding='UTF-8' ?>".getBytes());
            OutputStreamWriter osw = new OutputStreamWriter(fis, "UTF-8");
            this.xStream.toXML(comprobante, osw);
        } catch (Exception ex) {
            log.error("Al escribir XML de la Nota de Credito ", ex);
            throw new XmlException(ex.getMessage());
        }
    }

    @Override
    public String generarXml(NotaCredito comprobante) throws XmlException {
        if (comprobante == null) {
            throw new XmlException("Informacion del comprobante es nula");
        }
        return xStream.toXML(comprobante);
    }

    @Override
    public NotaCredito getComprobanteDePathArchivo(String pathAbsolute) throws XmlException {
        if (pathAbsolute == null || pathAbsolute.isEmpty()) {
            throw new XmlException("Direccion path XML nulo o vacio");
        }
        File xmlFile = new File(pathAbsolute);
        if (!xmlFile.isFile()) {
            throw new XmlException("Direccion path XML no es valido");
        }
        return (NotaCredito) xStream.fromXML(xmlFile);
    }

}
