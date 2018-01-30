/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.sri.services.on.aut;

/**
 *
 * @author Incloud Services S.A.C.
 */
public class AutorizacionException extends Exception {

    private String code;

    public AutorizacionException(String message) {
        super(message);
    }

    public AutorizacionException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    @Override
    public String toString() {
        return new StringBuilder("AutorizacionException [")
                .append("code: ").append(code)
                .append(", message: ").append(getMessage()).append("]").toString();
    }

}
