/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.pdf.services;

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
 * @author Incloud Services S.A.C.
 */
public class PdfFactory {

	@SuppressWarnings("unchecked")
    public static PdfServices<Factura> createPdfFacturaServices() {
        return FacturaPdfServices.create();
    }

<<<<<<< HEAD
    @SuppressWarnings("unchecked")
	public static PdfServices<FacturaExportacion> createPdfFacturaExportacionServices() {
		return FacturaExportacionPdfServices.create();
	}

	@SuppressWarnings("unchecked")
	public static PdfServices<FacturaReembolso> createPdfFacturaReembolsoServices() {
		return FacturaReembolsoPdfServices.create();
	}

	@SuppressWarnings("unchecked")
    public static PdfServices<GuiaRemision> createPdfGuiaRemisionServices() {
=======
	@SuppressWarnings("rawtypes")
	public static PdfServices createPdfFacturaExportacionServices() {
		return FacturaExportacionPdfServices.create();
	}

	@SuppressWarnings("rawtypes")
	public static PdfServices createPdfFacturaReembolsoServices() {
		return FacturaReembolsoPdfServices.create();
	}

    @SuppressWarnings("unchecked")
	public static PdfServices<GuiaRemision> createPdfGuiaRemisionServices() {
>>>>>>> 93603c36689dfc83e5b70120ae5b211845382986
        return GuiaRemisionPdfServices.create();
    }

    @SuppressWarnings("unchecked")
<<<<<<< HEAD
    public static PdfServices<NotaCredito> createPdfNotaCreditoServices() {
=======
	public static PdfServices<NotaCredito> createPdfNotaCreditoServices() {
>>>>>>> 93603c36689dfc83e5b70120ae5b211845382986
        return NotaCreditoPdfServices.create();
    }

    @SuppressWarnings("unchecked")
<<<<<<< HEAD
    public static PdfServices<NotaDebito> createPdfNotaDebitoServices() {
=======
	public static PdfServices<NotaDebito> createPdfNotaDebitoServices() {
>>>>>>> 93603c36689dfc83e5b70120ae5b211845382986
        return NotaDebitoPdfServices.create();
    }

    @SuppressWarnings("unchecked")
	public static PdfServices<ComprobanteRetencion> createPdfComprobanteRetencionServices() {
        return RetencionPdfServices.create();
    }
    
    @SuppressWarnings("unchecked")
	public static PdfServices<LiquidacionCompra> createPdfLiquidacionCompra() {
    	return LiquidacionCompraPdfServices.create();
    }
}
