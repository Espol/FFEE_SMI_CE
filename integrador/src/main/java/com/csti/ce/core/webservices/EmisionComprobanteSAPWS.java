/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package com.csti.ce.core.webservices;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.mirth.connect.connectors.ws.AcceptMessage;
import com.mirth.connect.connectors.ws.WebServiceReceiver;

import ec.incloud.ce.bean.common.Referencia;
import ec.incloud.ce.bean.credito.NotaCredito;
import ec.incloud.ce.bean.debito.NotaDebito;
import ec.incloud.ce.bean.factura.Factura;
import ec.incloud.ce.bean.facturaExportacion.FacturaExportacion;
import ec.incloud.ce.bean.facturaReembolso.FacturaReembolso;
import ec.incloud.ce.bean.guia.GuiaRemision;
import ec.incloud.ce.bean.liquidacionCompra.LiquidacionCompra;
import ec.incloud.ce.bean.retencion.ComprobanteRetencion;
import ec.incloud.ce.integrador.bean.Respuesta;
import ec.incloud.ce.integrador.on.Emision;
import ec.incloud.ce.integrador.on.EmisionFactoryOnLine;

/**
 *
 * @author Incloud Services S.A.C.
 */
@WebService(serviceName = "EmisionComprobanteSAPWS")
public class EmisionComprobanteSAPWS extends AcceptMessage {

    public EmisionComprobanteSAPWS(WebServiceReceiver webServiceReceiver) {
        super(webServiceReceiver);
    }

    @WebMethod(operationName = "EmitirFactura")
    public Respuesta EmitirFactura(
            @WebParam(name = "factura") Factura factura,
            @WebParam(name = "referencia") Referencia referencia,
            @WebParam(name = "usuario") String usuario,
            @WebParam(name = "terminal") String terminal,
            @WebParam(name = "codInterlocutor") String codInterlocutor,
            @WebParam(name = "emailDestino") String mails,
            @WebParam(name = "emailPortal") String emailPortal,
            @WebParam(name = "montoTotal") String montoTotal,
            @WebParam(name = "userPortal") String userPortal,
            @WebParam(name = "idUsuario") String idUsuario,//contraseña
            @WebParam(name = "password") String password,
            @WebParam(name = "observacion") String observacion
    ) {
        Emision<Factura> emision = EmisionFactoryOnLine.createEmisionFactura();
        return emision.emitir(factura, referencia, usuario, terminal, codInterlocutor, mails, emailPortal, montoTotal, userPortal, idUsuario, password, observacion);        
    }

    @WebMethod(operationName = "EmitirFacturaExportacion")
    public Respuesta EmitirFacturaExportacion(
            @WebParam(name = "facturaExportacion") FacturaExportacion facturaExportacion,
            @WebParam(name = "referencia") Referencia referencia,
            @WebParam(name = "usuario") String usuario,
            @WebParam(name = "terminal") String terminal,
            @WebParam(name = "codInterlocutor") String codInterlocutor,
            @WebParam(name = "emailDestino") String mails,
            @WebParam(name = "emailPortal") String emailPortal,
            @WebParam(name = "montoTotal") String montoTotal,
            @WebParam(name = "userPortal") String userPortal,
            @WebParam(name = "idUsuario") String idUsuario,
            @WebParam(name = "password") String password,
            @WebParam(name = "observacion") String observacion
    ) {
        Emision<FacturaExportacion> emision = EmisionFactoryOnLine.createEmisionFacturaExportacion();
        return emision.emitir(facturaExportacion, referencia, usuario, terminal, codInterlocutor, mails, emailPortal, montoTotal, userPortal, idUsuario, password, observacion);      
    }

    @WebMethod(operationName = "EmitirFacturaReembolso")
    public Respuesta EmitirFacturaReembolso(
            @WebParam(name = "facturaReembolso") FacturaReembolso facturaReembolso,
            @WebParam(name = "referencia") Referencia referencia,
            @WebParam(name = "usuario") String usuario,
            @WebParam(name = "terminal") String terminal,
            @WebParam(name = "codInterlocutor") String codInterlocutor,
            @WebParam(name = "emailDestino") String mails,
            @WebParam(name = "emailPortal") String emailPortal,
            @WebParam(name = "montoTotal") String montoTotal,
            @WebParam(name = "userPortal") String userPortal,
            @WebParam(name = "idUsuario") String idUsuario,
            @WebParam(name = "password") String password,
            @WebParam(name = "observacion") String observacion
    ) {
        Emision<FacturaReembolso> emision = EmisionFactoryOnLine.createEmisionFacturaReembolso();
        return emision.emitir(facturaReembolso, referencia, usuario, terminal, codInterlocutor, mails, emailPortal, montoTotal, userPortal, idUsuario, password, observacion);
    }

    @WebMethod(operationName = "EmitirNotaCredito")
    public Respuesta EmitirNotaCredito(
    		@WebParam(name = "notaCredito") NotaCredito notaCredito,
            @WebParam(name = "referencia") Referencia referencia,
            @WebParam(name = "usuario") String usuario,
            @WebParam(name = "terminal") String terminal,
            @WebParam(name = "codInterlocutor") String codInterlocutor,
            @WebParam(name = "emailDestino") String mails,            
            @WebParam(name = "emailPortal") String emailPortal,
            @WebParam(name = "montoTotal") String montoTotal,
            @WebParam(name = "userPortal") String userPortal,
            @WebParam(name = "idUsuario") String idUsuario,
            @WebParam(name = "password") String password,
            @WebParam(name = "observacion") String observacion
            
    		) {
        Emision<NotaCredito> emision = EmisionFactoryOnLine.createEmisionNotaCredito(); 
        return emision.emitir(notaCredito, referencia, usuario, terminal, codInterlocutor, mails, emailPortal, montoTotal, userPortal, idUsuario, password, observacion);
    }

    @WebMethod(operationName = "EmitirNotaDebito")
    public Respuesta EmitirNotaDebito(            
    		@WebParam(name = "notaDebito") NotaDebito notaDebito,
            @WebParam(name = "referencia") Referencia referencia,
            @WebParam(name = "usuario") String usuario,
            @WebParam(name = "terminal") String terminal,
            @WebParam(name = "codInterlocutor") String codInterlocutor,
            @WebParam(name = "emailDestino") String mails,            
            @WebParam(name = "emailPortal") String emailPortal,
            @WebParam(name = "montoTotal") String montoTotal,
            @WebParam(name = "userPortal") String userPortal,
            @WebParam(name = "idUsuario") String idUsuario,
            @WebParam(name = "password") String password,
            @WebParam(name = "observacion") String observacion
    		) {
        Emision<NotaDebito> emision = EmisionFactoryOnLine.createEmisionNotaDebito(); 
        return emision.emitir(notaDebito, referencia, usuario, terminal, codInterlocutor, mails, emailPortal, montoTotal, userPortal, idUsuario, password, observacion);
    }

    @WebMethod(operationName = "EmitirGuiaRemision")
    public Respuesta EmitirGuiaRemision(
    		@WebParam(name = "guiaRemision") GuiaRemision guiaRemision,
            @WebParam(name = "referencia") Referencia referencia,
            @WebParam(name = "usuario") String usuario,
            @WebParam(name = "terminal") String terminal,
            @WebParam(name = "codInterlocutor") String codInterlocutor,
            @WebParam(name = "emailDestino") String mails,            
            @WebParam(name = "emailPortal") String emailPortal,
            @WebParam(name = "montoTotal") String montoTotal,
            @WebParam(name = "userPortal") String userPortal,
            @WebParam(name = "idUsuario") String idUsuario,
            @WebParam(name = "password") String password,
            @WebParam(name = "observacion") String observacion
        ) {
        Emision<GuiaRemision> emision = EmisionFactoryOnLine.createEmisionGuiaRemision();
        return emision.emitir(guiaRemision, referencia, usuario, terminal, codInterlocutor, mails, emailPortal, montoTotal, userPortal, idUsuario, password, observacion);
    }

    @WebMethod(operationName = "EmitirRetencion")
    public Respuesta EmitirRetencion(   
    		@WebParam(name = "comprobanteRetencion") ComprobanteRetencion comprobanteRetencion,
            @WebParam(name = "referencia") Referencia referencia,
            @WebParam(name = "usuario") String usuario,
            @WebParam(name = "terminal") String terminal,
            @WebParam(name = "codInterlocutor") String codInterlocutor,
            @WebParam(name = "emailDestino") String mails,             
            @WebParam(name = "emailPortal") String emailPortal,
            @WebParam(name = "montoTotal") String montoTotal,
            @WebParam(name = "userPortal") String userPortal,
            @WebParam(name = "idUsuario") String idUsuario,
            @WebParam(name = "password") String password,
            @WebParam(name = "observacion") String observacion
    		
    		    		
    		) {
        Emision<ComprobanteRetencion> emision = EmisionFactoryOnLine.createEmisionRetencion();
        return emision.emitir(comprobanteRetencion, referencia, usuario, terminal, codInterlocutor, mails, emailPortal, montoTotal, userPortal, idUsuario, password, observacion);
    }
    
    @WebMethod(operationName = "EmitirLiquidacionCompra")
    public Respuesta EmitirLiquidacionCompra(
    		 @WebParam(name = "liquidacionCompra") LiquidacionCompra liquidacionCompra,
             @WebParam(name = "referencia") Referencia referencia,
             @WebParam(name = "usuario") String usuario,
             @WebParam(name = "terminal") String terminal,
             @WebParam(name = "codInterlocutor") String codInterlocutor,
             @WebParam(name = "emailDestino") String mails,
             @WebParam(name = "montoTotal") String montoTotal,
             @WebParam(name = "observacion") String observacion
    		) {
    	Emision<LiquidacionCompra> emision = EmisionFactoryOnLine.createEmisionLiquidacionCompra();
    	return emision.emitir(liquidacionCompra, referencia, usuario, terminal, codInterlocutor, mails, "", montoTotal, "", "", "", observacion);
    }
}
