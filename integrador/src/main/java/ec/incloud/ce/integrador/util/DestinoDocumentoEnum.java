/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.integrador.util;

/**
 *
 * @author Inclod Services S.A.C.
 */
public enum DestinoDocumentoEnum {

    SRI_RECEPCION("SR"),
    SRI_AUTORIZACION("ST"),
    SAP("SA"),
    PORTAL("PO");

    private final String codigo;

    private DestinoDocumentoEnum(String codigo) {
        this.codigo = codigo;
    }

    public String getDestino() {
        return this.codigo;
    }
}
