/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.sri.services;

/**
 *
 * @author Incloud Services S.A.C.
 */
public class RecepcionException extends Exception {

    private String code;

    public RecepcionException(String message) {
        super(message);
    }

    public RecepcionException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    @Override
    public String toString() {
        return new StringBuilder("RecepcionException [")
                .append("code: ").append(code)
                .append(", message: ").append(getMessage()).append("]").toString();
    }

}
