/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.pdf.services;

import ec.incloud.ce.bean.credito.NotaCredito;
import ec.incloud.ce.bean.debito.NotaDebito;
import ec.incloud.ce.bean.factura.Factura;
import ec.incloud.ce.bean.guia.GuiaRemision;
import ec.incloud.ce.bean.retencion.ComprobanteRetencion;

/**
 *
 * @author Incloud Services S.A.C.
 */
public class PdfFactory {

    public static PdfServices<Factura> createPdfFacturaServices() {
        return FacturaPdfServices.create();
    }

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
        return GuiaRemisionPdfServices.create();
    }

    @SuppressWarnings("unchecked")
	public static PdfServices<NotaCredito> createPdfNotaCreditoServices() {
        return NotaCreditoPdfServices.create();
    }

    @SuppressWarnings("unchecked")
	public static PdfServices<NotaDebito> createPdfNotaDebitoServices() {
        return NotaDebitoPdfServices.create();
    }

    @SuppressWarnings("unchecked")
	public static PdfServices<ComprobanteRetencion> createPdfComprobanteRetencionServices() {
        return RetencionPdfServices.create();
    }
}
