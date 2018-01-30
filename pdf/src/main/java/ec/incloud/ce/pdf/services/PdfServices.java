/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.pdf.services;

/**
 *
 * @author Incloud Services S.A.C.
 * @param <T>
 */
public interface PdfServices<T> {

    public void generarPdf(
    		T comprobante,
            String pathAbsolute,
            String numeroAutorizacion,
            String fechaAutorizacion,
    		String [] customPdfBySociedad,
    		String [] sociedad,
    		String porcentajeIvaDinamico
    		);
}
