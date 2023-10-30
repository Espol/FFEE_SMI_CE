/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.integrador.exception;

/**
 *
 * @author Incloud Services S.A.C.
 */
@SuppressWarnings("serial")
public class IntegradorException extends Exception {

    private String code;

    public IntegradorException(String message) {
        super(message);
    }

    public IntegradorException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    @Override
    public String toString() {
        return new StringBuilder("IntegradorException [")
                .append("code: ").append(code)
                .append(", message: ").append(getMessage()).append("]").toString();
    }

}
