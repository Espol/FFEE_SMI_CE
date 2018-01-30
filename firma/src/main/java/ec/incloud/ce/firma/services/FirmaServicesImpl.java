/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.incloud.ce.firma.services;

import org.w3c.dom.Document;

import ec.incloud.ce.firma.exception.FirmaException;
import es.mityc.firmaJava.libreria.xades.DataToSign;
import es.mityc.firmaJava.libreria.xades.XAdESSchemas;
import es.mityc.javasign.EnumFormatoFirma;
import es.mityc.javasign.xml.refs.InternObjectToSign;
import es.mityc.javasign.xml.refs.ObjectToSign;

/**
 *
 * @author Usuario
 */
final class FirmaServicesImpl extends GenericXMLSignature implements FirmaServices{
    
    private static FirmaServices instance;
    private String pathXml;
    private String pathCertificado;
    private String claveCertificado;
    
    private  FirmaServicesImpl() {
    }
    
    public static FirmaServices create(){
        synchronized (FirmaServicesImpl.class){
            if(instance == null){
                instance = new FirmaServicesImpl();
            }
            return instance;
        }
    }
    
    @Override
    public synchronized void firma(String pathAbsoluteXml, String certificado, String clave) throws FirmaException {
        this.pathXml = pathAbsoluteXml;
        this.pathCertificado = certificado;
        this.claveCertificado = clave;
        execute();
    }

    @Override
    protected String getPathCertificado() {
        return this.pathCertificado;
    }

    @Override
    protected String getClaveCertificado() {
        return this.claveCertificado;
    }

    @Override
    protected synchronized DataToSign createDataToSign() throws FirmaException {
                DataToSign dataToSign = new DataToSign();
        try {
            dataToSign.setXadesFormat(EnumFormatoFirma.XAdES_BES);
            dataToSign.setEsquema(XAdESSchemas.XAdES_132);
            dataToSign.setXMLEncoding("UTF-8");
            dataToSign.setEnveloped(true);
            Document docToSign = getDocument(getSignatureFileName());
            dataToSign.setDocument(docToSign);
            dataToSign.addObject(new ObjectToSign(new InternObjectToSign("comprobante"), "comprobante", null, "text/xml", null));
            
        }catch (Exception ex){
                dataToSign = null;
                ex.printStackTrace();
                throw new FirmaException(ex.getMessage());
        }

        return dataToSign;
    }

    @Override
    protected synchronized String getSignatureFileName() {
        return this.pathXml;
    }
    
}
