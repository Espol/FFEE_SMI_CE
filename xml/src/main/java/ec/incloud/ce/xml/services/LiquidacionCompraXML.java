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
import ec.incloud.ce.bean.factura.Factura;
import ec.incloud.ce.bean.factura.FacturaDetalle;
import ec.incloud.ce.bean.facturaReembolso.ReembolsoDetalle;
import ec.incloud.ce.bean.liquidacionCompra.InfoLiquidacionCompra;
import ec.incloud.ce.bean.liquidacionCompra.LiquidacionCompra;
import ec.incloud.ce.bean.liquidacionCompra.LiquidacionCompraDetalle;
import ec.incloud.ce.bean.liquidacionCompra.MaquinaFiscal;
import ec.incloud.ce.xml.convert.CampoAdicionalConvert;
import ec.incloud.ce.xml.convert.DetAdicionalConvert;
import ec.incloud.ce.xml.exception.XmlException;

public class LiquidacionCompraXML implements XmlServices<LiquidacionCompra> {
	
	 private static XmlServices instance;
	 private final XStream xStream;
	 private final Logger log = Logger.getLogger("integrador");
	    
	 private  LiquidacionCompraXML() {
		 xStream = new XStream(new DomDriver("UTF-8"));
	        xStream.alias("liquidacionCompra", LiquidacionCompra.class);
	        xStream.alias("infoTributaria", InfoTributaria.class);
	        xStream.alias("infoLiquidacionCompra", InfoLiquidacionCompra.class);
	        xStream.alias("totalImpuesto", TotalImpuesto.class);
	        xStream.alias("pago", Pago.class);
	        xStream.alias("detalle", LiquidacionCompraDetalle.class);
	        xStream.alias("detAdicional", DetAdicional.class);
	        xStream.alias("impuesto", Impuesto.class);
	        xStream.alias("reembolsoDetalle", ReembolsoDetalle.class);
	        xStream.alias("detalleImpuestos", DetalleImpuesto.class);
	        xStream.alias("maquinaFiscal", MaquinaFiscal.class);
	        xStream.registerLocalConverter(LiquidacionCompra.class, "infoAdicional", new CampoAdicionalConvert());
	        xStream.registerLocalConverter(LiquidacionCompraDetalle.class, "detallesAdicionales", new DetAdicionalConvert());
	        xStream.useAttributeFor("id", String.class);
	        xStream.useAttributeFor("version", String.class);
	        xStream.useAttributeFor("nombre", String.class);
	        
	        xStream.omitField(Factura.class, "ds:Signature");
	 }
	 
	 public static XmlServices<LiquidacionCompra> create() {
		synchronized (LiquidacionCompraXML.class) {
			if(instance == null) {
				instance = new LiquidacionCompraXML(); 
			}
			return instance;
		}
	 }
	 

	@Override
	public void generarXml(LiquidacionCompra comprobante, String pathAbsolute) throws XmlException {
		try {
            log.debug("Escribiendo el XML de Liquidación de Compra en " + pathAbsolute);
            FileOutputStream fis = new FileOutputStream(new File(pathAbsolute));
            fis.write("<?xml version='1.0' encoding='UTF-8' ?>".getBytes());
            OutputStreamWriter osw = new OutputStreamWriter(fis, "UTF-8");
            xStream.toXML(comprobante, osw);
        } catch (Exception ex) {
            log.error("Al escribir XML de Liquidación de Compra ", ex);
            throw new XmlException(ex.getMessage());
        }
	}

	@Override
	public String generarXml(LiquidacionCompra comprobante) throws XmlException {
		if (comprobante == null) {
            throw new XmlException("Informacion del comprobante es nula");
        }
        return xStream.toXML(comprobante);
	}

	@Override
	public LiquidacionCompra getComprobanteDePathArchivo(String pathAbsolute) throws XmlException {
		if (pathAbsolute == null || pathAbsolute.isEmpty()) {
            throw new XmlException("Direccion path XML nulo o vacio");
        }
        File xmlFile = new File(pathAbsolute);
        if (!xmlFile.isFile()) {
            throw new XmlException("Direccion path XML no es valido");
        }
        return (LiquidacionCompra) xStream.fromXML(xmlFile);
	}

}
