/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.xml.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import org.apache.log4j.Logger;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import ec.incloud.ce.bean.common.DetAdicional;
import ec.incloud.ce.bean.common.Impuesto;
import ec.incloud.ce.bean.common.InfoTributaria;
import ec.incloud.ce.bean.common.Pago;
import ec.incloud.ce.bean.common.TotalImpuesto;
import ec.incloud.ce.bean.facturaExportacion.FacturaExportacion;
import ec.incloud.ce.bean.facturaExportacion.FacturaExportacionDetalle;
import ec.incloud.ce.bean.facturaExportacion.InfoFacturaExportacion;
import ec.incloud.ce.xml.convert.CampoAdicionalConvert;
import ec.incloud.ce.xml.convert.DetAdicionalConvert;
import ec.incloud.ce.xml.exception.XmlException;

/**
 *
 * @author Joel Povis Ocaña
 */
public final class FacturaExportacionXml implements XmlServices<FacturaExportacion> {

    private static XmlServices instance;
    private final XStream xStream;
    private final Logger log = Logger.getLogger("integrador");

    private FacturaExportacionXml() {
        xStream = new XStream(new DomDriver("UTF-8"));
        xStream.alias("factura", FacturaExportacion.class);
        xStream.alias("infoTributaria", InfoTributaria.class);
        xStream.alias("infoFactura", InfoFacturaExportacion.class);
        xStream.alias("detalle", FacturaExportacionDetalle.class);
        xStream.alias("detAdicional", DetAdicional.class);
        xStream.alias("impuesto", Impuesto.class);
        xStream.alias("totalImpuesto", TotalImpuesto.class);
        xStream.alias("pago", Pago.class);
        xStream.registerLocalConverter(FacturaExportacion.class, "infoAdicional", new CampoAdicionalConvert());
        xStream.registerLocalConverter(FacturaExportacionDetalle.class, "detallesAdicionales", new DetAdicionalConvert());
        xStream.useAttributeFor("id", String.class);
        xStream.useAttributeFor("version", String.class);
        xStream.useAttributeFor("nombre", String.class);

        xStream.omitField(FacturaExportacion.class, "ds:Signature");
    }

    public static XmlServices<FacturaExportacion> create() {
        synchronized (FacturaExportacionXml.class) {
            if (instance == null) {
                instance = new FacturaExportacionXml();
            }
            return instance;
        }
    }

    @Override
    public synchronized void generarXml(FacturaExportacion comprobante, String pathAbsolute) throws XmlException {
        try {
            log.debug("Escribiendo el XML de Factura en " + pathAbsolute);
            FileOutputStream fis = new FileOutputStream(new File(pathAbsolute));
            fis.write("<?xml version='1.0' encoding='UTF-8' ?>".getBytes());
            OutputStreamWriter osw = new OutputStreamWriter(fis, "UTF-8");
            xStream.toXML(comprobante, osw);
        } catch (Exception ex) {
            log.error("Al escribir XML de factura ", ex);
            throw new XmlException(ex.getMessage());
        }
    }

    @Override
    public String generarXml(FacturaExportacion comprobante) throws XmlException {
        if (comprobante == null) {
            throw new XmlException("Informacion del comprobante es nula");
        }
        return xStream.toXML(comprobante);
    }

    @Override
    public FacturaExportacion getComprobanteDePathArchivo(String pathAbsolute) throws XmlException {
        if (pathAbsolute == null || pathAbsolute.isEmpty()) {
            throw new XmlException("Direccion path XML nulo o vacio");
        }
        File xmlFile = new File(pathAbsolute);
        if (!xmlFile.isFile()) {
            throw new XmlException("Direccion path XML no es valido");
        }
        return (FacturaExportacion) xStream.fromXML(xmlFile);
    }

}
