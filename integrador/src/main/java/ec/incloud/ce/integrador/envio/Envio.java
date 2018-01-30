/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.integrador.envio;

/**
 *
 * @author Incloud Services S.A.C.
 */
public interface Envio {

    /**
     * Envio de documentos al ws de recepcion
     */
    public void enviarDocumentoRecepcion();

    /**
     * Reintento de documentos con error de comunicacion al ws de recepcion
     */
    public void reenviarDocumentoRecepcion();

    /**
     * Envio de documentos al ws de autorizacion
     */
    public void enviarDocumentoAutorizacion();

    /**
     * Reintento de documentos con error de comunicacion al ws de autorizacion
     */
    public void reenviarDocumentoAutorizacion();

    /**
     * Envio de documentos al sap - rfc
     */
    public void enviarDocumentoSap();

    /**
     * Notificación por vencimiento de la firma electrónica
     */
    public void notificarByFirmaCaducada();
}
