/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.integrador.util;

/**
 *
 * @author Incloud Services S.A.C.
 */
public enum EstadoDocumentoEnum {

    PENDIENTE("PE"),
    RECEPCIONADO("RC"),
    NO_RECEPCIONADO("NR"),
    AUTORIZADO("AU"),
    RECHAZADO("RE");

    private final String codigo;

    private EstadoDocumentoEnum(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

}
