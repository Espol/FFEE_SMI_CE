/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.sap.services;

/**
 *
 * @author Incloud Services S.A.C.
 */
public interface SapServices {

    /**
     * 12.	T_RPTA (Tabla) - Tabla de mensajes de respuesta del SRI
     *
     * @param updConfig
     * @param ruc IV_STCD1 - RUC de la sociedad del emisor
     * @param tipoDocumento IV_CODDOC - Cód. de documento SRI
     * @param serieCorrelativo IV_XBLNR - Nro. de comprobante SRI
     * @param numeroSap IV_VBELN - Documento SAP
     * @param estado IV_ESTADO - Estado electrónico (AU ó RE)
     * @param numeroAutorizacion IV_AUTO_SRI - Nro. de Autorización SRI
     * @param claveAcceso IV_CLAVE_SRI - Clave de Acceso
     * @param fechaAutorizacion IV_FEC_AUT - Fecha de autorización SRI
     * @param usuarioSap IV_USER - Usuario SAP que emitió el documento
     * @param generoPdf IV_PDF - ¿Se generó PDF? ( 0(No) ó 1(Sí) )
     * @param envioMail IV_EMAIL - ¿Se envió email al cliente? ( 0(No) ó 1(Sí) )
     * @param mensajeSap
     * @return
     */
    public RespuestaSap actualizarDocumento(boolean updConfig,
            String ruc,
            String tipoDocumento,
            String serieCorrelativo,
            String numeroSap,
            String estado,
            String numeroAutorizacion,
            String claveAcceso,
            String fechaAutorizacion,
            String usuarioSap,
            boolean generoPdf,
            boolean envioMail,
            MensajeSap mensajeSap);

    public void setMandante(String mandante);

    public void setUsuario(String usuario);

    public void setContrasena(String contrasena);

    public void setIdioma(String idioma);

    public void setIpServidor(String servidor);

    public void setNumeroInstancia(String numeroInstancia);

    public void setIdSistema(String idSistema);

    public void setSapRouter(String sapRouter);
}
