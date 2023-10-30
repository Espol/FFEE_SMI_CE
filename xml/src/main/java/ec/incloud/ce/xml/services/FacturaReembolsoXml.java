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
import ec.incloud.ce.bean.common.DetalleImpuesto;
import ec.incloud.ce.bean.common.Impuesto;
import ec.incloud.ce.bean.common.InfoTributaria;
import ec.incloud.ce.bean.common.Pago;
import ec.incloud.ce.bean.common.TotalImpuesto;
import ec.incloud.ce.bean.facturaReembolso.FacturaReembolso;
import ec.incloud.ce.bean.facturaReembolso.FacturaReembolsoDetalle;
import ec.incloud.ce.bean.facturaReembolso.InfoFacturaReembolso;
import ec.incloud.ce.bean.facturaReembolso.ReembolsoDetalle;
import ec.incloud.ce.xml.convert.CampoAdicionalConvert;
import ec.incloud.ce.xml.convert.DetAdicionalConvert;
import ec.incloud.ce.xml.exception.XmlException;

/**
 *
 * @author Joel Povis Ocaña
 */
public final class FacturaReembolsoXml implements XmlServices<FacturaReembolso> {

    @SuppressWarnings("rawtypes")
	private static XmlServices instance;
    private final XStream xStream;
    private final Logger log = Logger.getLogger("integrador");

    private FacturaReembolsoXml() {
        xStream = new XStream(new DomDriver("UTF-8"));
        xStream.alias("factura", FacturaReembolso.class);
        xStream.alias("infoTributaria", InfoTributaria.class);
        xStream.alias("infoFactura", InfoFacturaReembolso.class);
        xStream.alias("detalle", FacturaReembolsoDetalle.class);
        xStream.alias("reembolsoDetalle", ReembolsoDetalle.class);
        xStream.alias("detalleImpuesto", DetalleImpuesto.class);
        xStream.alias("detAdicional", DetAdicional.class);//se amarra con FacturaReembolsoDetalle
        xStream.alias("impuesto", Impuesto.class);//se amarra con FacturaReembolsoDetalle
        xStream.alias("totalImpuesto", TotalImpuesto.class);//se amarra con InfoFacturaReembolso
        xStream.alias("pago", Pago.class);//se amarra con InfoFacturaReembolso
        xStream.registerLocalConverter(FacturaReembolso.class, "infoAdicional", new CampoAdicionalConvert());
        xStream.registerLocalConverter(FacturaReembolsoDetalle.class, "detallesAdicionales", new DetAdicionalConvert());
        xStream.useAttributeFor("id", String.class);
        xStream.useAttributeFor("version", String.class);
        xStream.useAttributeFor("nombre", String.class);

        xStream.omitField(FacturaReembolso.class, "ds:Signature");
    }

    @SuppressWarnings("unchecked")
	public static XmlServices<FacturaReembolso> create() {
        synchronized (FacturaReembolsoXml.class) {
            if (instance == null) {
                instance = new FacturaReembolsoXml();
            }
            return instance;
        }
    }

    @Override
    public synchronized void generarXml(FacturaReembolso comprobante, String pathAbsolute) throws XmlException {
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
    public String generarXml(FacturaReembolso comprobante) throws XmlException {
        if (comprobante == null) {
            throw new XmlException("Informacion del comprobante es nula");
        }
        return xStream.toXML(comprobante);
    }

    @Override
    public FacturaReembolso getComprobanteDePathArchivo(String pathAbsolute) throws XmlException {
        if (pathAbsolute == null || pathAbsolute.isEmpty()) {
            throw new XmlException("Direccion path XML nulo o vacio");
        }
        File xmlFile = new File(pathAbsolute);
        if (!xmlFile.isFile()) {
            throw new XmlException("Direccion path XML no es valido");
        }
        return (FacturaReembolso) xStream.fromXML(xmlFile);
    }

}
