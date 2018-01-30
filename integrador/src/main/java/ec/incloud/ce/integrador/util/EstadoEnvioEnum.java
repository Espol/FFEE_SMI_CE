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
public enum EstadoEnvioEnum {

    PENDIENTE("PE"),
    EN_PROCESO("EP"),
    ERROR("EC"),
    TERMINADO("TE");

    private final String codigo;

    private EstadoEnvioEnum(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

}
