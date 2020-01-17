/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.incloud.ce.xml.services;

import ec.incloud.ce.bean.credito.NotaCredito;
import ec.incloud.ce.bean.debito.NotaDebito;
import ec.incloud.ce.bean.factura.Factura;
import ec.incloud.ce.bean.facturaExportacion.FacturaExportacion;
import ec.incloud.ce.bean.facturaReembolso.FacturaReembolso;
import ec.incloud.ce.bean.guia.GuiaRemision;
import ec.incloud.ce.bean.liquidacionCompra.LiquidacionCompra;
import ec.incloud.ce.bean.retencion.ComprobanteRetencion;

/**
 *
 * @author Joel Povis Ocaña
 */
public class XmlFactory {

    private XmlFactory() {
    }
    
    public static XmlServices<Factura> getFacturaXmlServices() {
        return FacturaXml.create();
    }
    
    public static XmlServices<FacturaReembolso> getFacturaReembolsoXmlServices() {
        return FacturaReembolsoXml.create();
    }
    
    public static XmlServices<FacturaExportacion> getFacturaExportacionXmlServices() {
        return FacturaExportacionXml.create();
    }

    public static XmlServices<GuiaRemision> getGuiaRemisionXmlServices() {
        return GuiaRemisionXml.create();
    }

    public static XmlServices<NotaCredito> getNotaCreditoXmlServices() {
        return NotaCreditoXml.create();
    }

    public static XmlServices<NotaDebito> getNotaDebitoXmlServices() {
        return NotaDebitoXml.create();
    }

    public static XmlServices<ComprobanteRetencion> getRetencionServices() {
        return RetencionXml.create();
    }
    
    public static XmlServices<LiquidacionCompra> getLiquidacionCompraService() {
    	return LiquidacionCompraXML.create();
    }
}
