/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.integrador.on;

import ec.incloud.ce.bean.credito.NotaCredito;
import ec.incloud.ce.bean.debito.NotaDebito;
import ec.incloud.ce.bean.factura.Factura;
import ec.incloud.ce.bean.facturaExportacion.FacturaExportacion;
import ec.incloud.ce.bean.facturaReembolso.FacturaReembolso;
import ec.incloud.ce.bean.guia.GuiaRemision;
import ec.incloud.ce.bean.retencion.ComprobanteRetencion;

/**
 *
 * @author Joel Povis Ocaña
 */
public class EmisionFactoryOnLine {

    /**
     * Retorna emisor de Factura Reembolso.
     *
     * @return
     */
    public static Emision<FacturaReembolso> createEmisionFacturaReembolso() {
        return FacturaReembolsoEmision.create();
    }
    
    
    /**
     * Retorna emisor de Factura Exportacion.
     *
     * @return
     */
    public static Emision<FacturaExportacion> createEmisionFacturaExportacion() {
        return FacturaExportacionEmision.create();
    }
    
    /**
     * Retorna la emisor de Factura.
     *
     * @return
     */
    public static Emision<Factura> createEmisionFactura() {
        return FacturaEmision.create();
    }

    /**
     * Retorna el emisor de Nota de Credito.
     *
     * @return
     */
    public static Emision<NotaCredito> createEmisionNotaCredito() {
        return NotaCreditoEmision.create();
    }

    /**
     * Retorna el emisor de Nota de Debito.
     *
     * @return
     */
    public static Emision<NotaDebito> createEmisionNotaDebito() {
        return NotaDebitoEmision.create();
    }

    /**
     * Retorna el emisor de Guias de Remision.
     *
     * @return
     */
    public static Emision<GuiaRemision> createEmisionGuiaRemision() {
        return GuiaRemisionEmision.create();
    }

    /**
     * Retorna el emisor de Comprobantes de Retencion.
     *
     * @return
     */
    public static Emision<ComprobanteRetencion> createEmisionRetencion() {
        return RetencionEmision.create();
    }
}
