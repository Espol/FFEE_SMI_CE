/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.xml.services;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import ec.incloud.ce.bean.common.DetAdicional;
import ec.incloud.ce.bean.common.InfoTributaria;
import ec.incloud.ce.bean.guia.Destinatario;
import ec.incloud.ce.bean.guia.GuiaRemision;
import ec.incloud.ce.bean.guia.GuiaRemisionDetalle;
import ec.incloud.ce.bean.guia.InfoGuiaRemision;
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
final class GuiaRemisionXml implements XmlServices<GuiaRemision> {

    private static XmlServices instance;
    private final XStream xStream;
    private final Logger log = Logger.getLogger("integrador");

    private GuiaRemisionXml() {
        this.xStream = new XStream(new DomDriver("UTF-8"));
        this.xStream.setClassLoader(GuiaRemision.class.getClassLoader());
        this.xStream.alias("guiaRemision", GuiaRemision.class);
        this.xStream.alias("infoTributaria", InfoTributaria.class);
        this.xStream.alias("infoGuiaRemision", InfoGuiaRemision.class);
        this.xStream.alias("destinatario", Destinatario.class);
        this.xStream.alias("detalle", GuiaRemisionDetalle.class);
        this.xStream.alias("detAdicional", DetAdicional.class);
        this.xStream.registerLocalConverter(GuiaRemision.class, "infoAdicional", new CampoAdicionalConvert());
        this.xStream.registerLocalConverter(GuiaRemisionDetalle.class, "detallesAdicionales", new DetAdicionalConvert());
        this.xStream.useAttributeFor("id", String.class);
        this.xStream.useAttributeFor("version", String.class);
        this.xStream.useAttributeFor("nombre", String.class);
        
        xStream.omitField(GuiaRemision.class, "ds:Signature");
    }

    public static XmlServices<GuiaRemision> create() {
        synchronized (GuiaRemisionXml.class) {
            if (instance == null) {
                instance = new GuiaRemisionXml();
            }
            return instance;
        }
    }

    @Override
    public void generarXml(GuiaRemision comprobante, String pathAbsolute) throws XmlException {
        try {
            log.debug("Escribiendo el XML de la Guia de Remision en " + pathAbsolute);
            FileOutputStream fis = new FileOutputStream(new File(pathAbsolute));
            fis.write("<?xml version='1.0' encoding='UTF-8' ?>".getBytes());
            OutputStreamWriter osw = new OutputStreamWriter(fis, "UTF-8");
            this.xStream.toXML(comprobante, osw);
        } catch (Exception ex) {
            log.error("Al escribir XML de la Guia de Remision ", ex);
            throw new XmlException(ex.getMessage());
        }
    }

    @Override
    public String generarXml(GuiaRemision comprobante) throws XmlException {
        if (comprobante == null) {
            throw new XmlException("Informacion del comprobante es nula");
        }
        return this.xStream.toXML(comprobante);
    }

    @Override
    public GuiaRemision getComprobanteDePathArchivo(String pathAbsolute) throws XmlException {
        if (pathAbsolute == null || pathAbsolute.isEmpty()) {
            throw new XmlException("Direccion path XML nulo o vacio");
        }
        File xmlFile = new File(pathAbsolute);
        if (!xmlFile.isFile()) {
            throw new XmlException("Direccion path XML no es valido");
        }
        return (GuiaRemision) xStream.fromXML(xmlFile);
    }

}
