/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.xml.services;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import ec.incloud.ce.bean.common.Impuesto;
import ec.incloud.ce.bean.common.InfoTributaria;
import ec.incloud.ce.bean.common.Pago;
import ec.incloud.ce.bean.debito.InfoNotaDebito;
import ec.incloud.ce.bean.debito.Motivo;
import ec.incloud.ce.bean.debito.NotaDebito;
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
final class NotaDebitoXml implements XmlServices<NotaDebito> {

    private static XmlServices instance;
    private final XStream xStream;
    private final Logger log = Logger.getLogger("integrador");

    private NotaDebitoXml() {
        this.xStream = new XStream(new DomDriver("UTF-8"));
        this.xStream.alias("notaDebito", NotaDebito.class);
        this.xStream.alias("infoTributaria", InfoTributaria.class);
        this.xStream.alias("infoNotaDebito", InfoNotaDebito.class);
        this.xStream.alias("impuesto", Impuesto.class);
        this.xStream.alias("motivo", Motivo.class);
        this.xStream.alias("pago", Pago.class);
        this.xStream.registerLocalConverter(NotaDebito.class, "infoAdicional", new CampoAdicionalConvert());
        this.xStream.useAttributeFor("id", String.class);
        this.xStream.useAttributeFor("version", String.class);
        this.xStream.useAttributeFor("nombre", String.class);

        xStream.omitField(NotaDebito.class, "ds:Signature");
    }

    public static XmlServices<NotaDebito> create() {
        synchronized (NotaDebitoXml.class) {
            if (instance == null) {
                instance = new NotaDebitoXml();
            }
            return instance;
        }
    }

    @Override
    public void generarXml(NotaDebito comprobante, String pathAbsolute) throws XmlException {
        try {
            log.debug("Escribiendo el XML de la Nota de Debito en " + pathAbsolute);
            FileOutputStream fis = new FileOutputStream(new File(pathAbsolute));
            fis.write("<?xml version='1.0' encoding='UTF-8' ?>".getBytes());
            OutputStreamWriter osw = new OutputStreamWriter(fis, "UTF-8");
            this.xStream.toXML(comprobante, osw);
        } catch (Exception ex) {
            log.error("Al escribir XML de la Nota de Debito ", ex);
            throw new XmlException(ex.getMessage());
        }
    }

    @Override
    public String generarXml(NotaDebito comprobante) throws XmlException {
        if (comprobante == null) {
            throw new XmlException("Informacion del comprobante es nula");
        }
        return xStream.toXML(comprobante);
    }

    @Override
    public NotaDebito getComprobanteDePathArchivo(String pathAbsolute) throws XmlException {
        if (pathAbsolute == null || pathAbsolute.isEmpty()) {
            throw new XmlException("Direccion path XML nulo o vacio");
        }
        File xmlFile = new File(pathAbsolute);
        if (!xmlFile.isFile()) {
            throw new XmlException("Direccion path XML no es valido");
        }
        return (NotaDebito) xStream.fromXML(xmlFile);
    }

}
