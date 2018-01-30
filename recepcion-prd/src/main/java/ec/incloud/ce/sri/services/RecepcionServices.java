/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.sri.services;

import ec.gob.sri.comprobantes.ws.rec.RespuestaSolicitud;

/**
 *
 * @author Incloud Services S.A.C.
 */
public interface RecepcionServices {

    public void setSecondForTimeOut(int second);

    public RespuestaSolicitud validarComprobante(String pathAbsoluteXML) throws RecepcionException;

}
