/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.xml.services;

import ec.incloud.ce.xml.exception.XmlException;

/**
 *
 * @author Joel Povis Ocaña
 * @param <T> Comprobante
 */
public interface XmlServices<T> {

    /**
     * Genera el comprobante en XML. La generacion se realiza en el path
     * indicado como parametro.
     *
     * @param comprobante
     * @param pathAbsolute
     * @throws XmlException
     */
    public void generarXml(T comprobante, String pathAbsolute) throws XmlException;

    /**
     * Retorna el XML del comprobante. El XML es retornado en una cadena de
     * texto.
     *
     * @param comprobante
     * @return
     * @throws ec.incloud.ce.xml.exception.XmlException
     */
    public String generarXml(T comprobante) throws XmlException;

    /**
     * Retorna el comprobante a partir de un XML existente.
     *
     * @param pathAbsolute
     * @return Comprobante
     * @throws ec.incloud.ce.xml.exception.XmlException
     */
    public T getComprobanteDePathArchivo(String pathAbsolute) throws XmlException;
}
