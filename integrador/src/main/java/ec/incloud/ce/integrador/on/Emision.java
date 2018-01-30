/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.integrador.on;

import ec.incloud.ce.bean.common.Referencia;
import ec.incloud.ce.integrador.bean.Respuesta;

/**
 *
 * @author Joel Povis Ocaña
 * @param <T>
 */
public interface Emision<T> {

    public static final String PUNTO_XML = ".xml";
    public static final String PUNTO_PDF = ".pdf";
    public static final String OK = "OK";
    public static final String ERROR = "ERROR";

    /**
     * Emite un comprobante. El comprobante sera validado en estructura, firmado
     * y enviado a la entidad recaudadora de impuestos.
     *
     * @param comprobante
     * @param fechaRegistro
     * @param usuario
     * @param terminal
     * @param interlocutor
     * @param mail
     * @param docSap
     * @param clase
     * @param montoTotal
     * @return
     */
//    public Respuesta emitir(T comprobante,
//            String fechaRegistro,
//            String usuario,
//            String terminal,
//            String interlocutor,
//            String mail,
//            String docSap,
//            String clase,
//            String montoTotal);
    public Respuesta emitir(
    		T comprobante,
    		Referencia referencia,
            String usuario,
            String terminal,
            String codInterlocutor,
            String mails,
            String emailPortal,
            String montoTotal,
            String userPortal,
            String idUsuario,
            String password,
            String observacion);
}
