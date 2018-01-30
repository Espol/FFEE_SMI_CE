/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.incloud.ce.validacion.services;

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
public class ValidacionFactory {

    private ValidacionFactory() {
    }

    /**
     * Retorna validacion de Factura Exportacion.
     *
     * @return
     */
    public static ValidacionServices<FacturaReembolso> createValidacionFacturaReembolsoServices() {
        return FacturaReembolsoValidacion.create();
    }


    /**
     * Retorna validacion de Factura Exportacion.
     *
     * @return
     */
    public static ValidacionServices<FacturaExportacion> createValidacionFacturaExportacionServices() {
        return FacturaExportacionValidacion.create();
    }

    /**
     * Retorna validacion de Factura.
     *
     * @return
     */
    public static ValidacionServices<Factura> createValidacionFacturaServices() {
        return FacturaValidacion.create();
    }

    /**
     * Retorna validacion de Guia de Remision.
     *
     * @return
     */
    public static ValidacionServices<GuiaRemision> createValidacionGuiaServices() {
        return GuiaRemisionValidacion.create();
    }

    /**
     * Retorna validacion de Nota de Credito.
     *
     * @return
     */
    public static ValidacionServices<NotaCredito> createValidacionNotaCreditoServices() {
        return NotaCreditoValidacion.create();
    }

    /**
     * Retorna validacion de Nota de Debito.
     *
     * @return
     */
    public static ValidacionServices<NotaDebito> createValidacionNotaDebitoServices() {
        return NotaDebitoValidacion.create();
    }

    /**
     * Retorna validacion de Comprobantes de Retencion.
     *
     * @return
     */
    public static ValidacionServices<ComprobanteRetencion> createValidacionRetencionServices() {
        return RetencionValidacion.create();
    }
}
