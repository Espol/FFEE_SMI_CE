/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.xml.exception;

/**
 *
 * @author Joel Povis Ocaña
 */
public class XmlException extends Exception {

    public XmlException(String message) {
        super(message);
    }

    public String getMensaje() {
        return super.getMessage();
    }

    @Override
    public String toString() {
        return new StringBuilder("XmlException [")
                .append(", message: ").append(getMessage()).append("]").toString();
    }
}
