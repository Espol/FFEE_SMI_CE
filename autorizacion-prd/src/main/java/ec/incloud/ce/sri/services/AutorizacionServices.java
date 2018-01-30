/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.sri.services;

import ec.gob.sri.comprobantes.ws.aut.RespuestaComprobante;

/**
 *
 * @author Incloud Services S.A.C.
 */
public interface AutorizacionServices {

    public void setSecondForTimeOut(int second);

    public RespuestaComprobante autorizarComprobante(String claveAcceso) throws AutorizacionException;

}
