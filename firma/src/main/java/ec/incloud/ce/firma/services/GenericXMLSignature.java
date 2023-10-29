/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.incloud.ce.firma.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import adsi.org.apache.xml.security.utils.XMLUtils;
import ec.incloud.ce.firma.exception.FirmaException;
import es.mityc.firmaJava.libreria.xades.DataToSign;
import es.mityc.firmaJava.libreria.xades.FirmaXML;
import es.mityc.javasign.pkstore.CertStoreException;
import es.mityc.javasign.pkstore.IPKStoreManager;
import es.mityc.javasign.pkstore.IPassStoreKS;
import es.mityc.javasign.pkstore.keystore.KSStore;

/**
 * <p>
 * Clase base que deberían extender los diferentes ejemplos para realizar firmas
 * XML.
 * </p>
 *
 */
public abstract class GenericXMLSignature {

    protected abstract String getPathCertificado();

    protected abstract String getClaveCertificado();

    /**
     * <p>
     * Crea el objeto DataToSign que contiene toda la información de la firma
     * que se desea realizar. Todas las implementaciones deberán proporcionar
     * una implementación de este método
     * </p>
     *
     * @return El objeto DataToSign que contiene toda la información de la firma
     * a realizar
     */
    protected abstract DataToSign createDataToSign() throws FirmaException;

    /**
     * <p>
     * Directorio donde se almacenará el resultado de la firma
     * </p>
     */
//    public final static String OUTPUT_DIRECTORY = ".";
    /**
     * <p>
     * Ejecución del ejemplo. La ejecución consistirá en la firma de los datos
     * creados por el método abstracto <code>createDataToSign</code> mediante el
     * certificado declarado en la constante <code>PKCS12_FILE</code>. El
     * resultado del proceso de firma será almacenado en un fichero XML en el
     * directorio correspondiente a la constante <code>OUTPUT_DIRECTORY</code>
     * del usuario bajo el nombre devuelto por el método abstracto
     * <code>getSignFileName</code>
     * </p>
     * @throws ec.incloud.ce.firma.exception.FirmaException
     */
    protected void execute() throws FirmaException {

        // Obtencion del gestor de claves
        IPKStoreManager storeManager = getPKStoreManager();
        if (storeManager == null) {
            throw new FirmaException("El gestor de claves no se ha obtenido correctamente.");
        }

        // Obtencion del certificado para firmar. Utilizaremos el primer
        // certificado del almacen.
        X509Certificate certificate = getFirstCertificate(storeManager);
        if (certificate == null) {
            throw new FirmaException("No existe ningún certificado para firmar.");
        }
        // Obtención de la clave privada asociada al certificado
        PrivateKey privateKey;
        try {
            privateKey = storeManager.getPrivateKey(certificate);
        } catch (CertStoreException e) {
            throw new FirmaException("Error al acceder al almacén.");
        }
        // Obtención del provider encargado de las labores criptográficas
        Provider provider = storeManager.getProvider(certificate);

        /*
         * Creación del objeto que contiene tanto los datos a firmar como la
         * configuración del tipo de firma
         */
        DataToSign dataToSign = createDataToSign();

        /*
         * Creación del objeto encargado de realizar la firma
         */
        FirmaXML firma = new FirmaXML();

        // Firmamos el documento
        Document docSigned = null;
        try {
            Object[] res = firma.signFile(certificate, dataToSign, privateKey, provider);
            docSigned = (Document) res[0];
        } catch (Exception ex) {
            throw new FirmaException("Error realizando la firma");
        }
        
        

        // Guardamos la firma a un fichero en el home del usuario
//        String filePath = OUTPUT_DIRECTORY + File.separatorChar + getSignatureFileName();
//        System.out.println("Firma salvada en en: " + filePath);
        saveDocumentToFile(docSigned, getSignatureFileName());
//        try {
//            FileOutputStream fos = new FileOutputStream(getSignatureFileName());
////            fos.write("<?xml version='1.0' encoding='UTF-8' standalone='yes'?>".getBytes());
//            byte[] bytes = this.getDocumentAsString(getSignatureFileName()).getBytes("UTF-8");
//            fos.write(bytes);
//        } catch (Exception e) {
//            throw new FirmaException("Error al Guardar Documento");
//        }
    }
    
    public static URL getLocation(Class<?> clazz) {
        return clazz.getResource('/' + clazz.getName().replace('.', '/') + ".class");
    }

    /**
     * <p>
     * Nombre del fichero donde se desea guardar la firma generada. Todas las
     * implementaciones deberán proporcionar este nombre.
     * </p>
     *
     * @return El nombre donde se desea guardar la firma generada
     */
    protected abstract String getSignatureFileName();

    /**
     * <p>
     * Escribe el documento a un fichero.
     * </p>
     *
     * @param document El documento a imprmir
     * @param pathfile El path del fichero donde se quiere escribir.
     */
    private void saveDocumentToFile(Document document, String pathfile) throws FirmaException {
        try {
            FileOutputStream fos = new FileOutputStream(pathfile);
//            UtilidadTratarNodo.saveDocumentToOutputStream(document, fos, false);
            document.setXmlVersion("1.0");            
            XMLUtils.outputDOM(document, fos, true);
////            String XML = XMLUtils.getFullTextChildrenFromElement(document.getDocumentElement());
////            System.out.println("***********"+XML);
//             OutputStreamWriter osw = new OutputStreamWriter(fos);
//             com.sun.org.apache.xerces.internal.dom.DOMOutputImpl domoutputimpl = new com.sun.org.apache.xerces.internal.dom.DOMOutputImpl();
//            domoutputimpl.setEncoding("UTF-8");
//            domoutputimpl.setCharacterStream(osw);
//
//            org.w3c.dom.ls.LSSerializer serializer;
//            org.w3c.dom.ls.DOMImplementationLS dils;
//            dils = (org.w3c.dom.ls.DOMImplementationLS) document.getImplementation();
//            serializer = dils.createLSSerializer();
//            serializer.getDomConfig().setParameter("namespaces", false);
//            serializer.getDomConfig().getParameterNames();
//            ((org.w3c.dom.ls.LSSerializer) (serializer)).write(document, domoutputimpl);
        } catch (FileNotFoundException e) {
            throw new FirmaException("Error al salvar el documento");
        }
    }

    /**
     * <p>
     * Escribe el documento a un fichero. Esta implementacion es insegura ya que
     * dependiendo del gestor de transformadas el contenido podría ser alterado,
     * con lo que el XML escrito no sería correcto desde el punto de vista de
     * validez de la firma.
     * </p>
     *
     * @param document El documento a imprmir
     * @param pathfile El path del fichero donde se quiere escribir.
     */
    @SuppressWarnings("unused")
    private void saveDocumentToFileUnsafeMode(Document document, String pathfile) {
        TransformerFactory tfactory = TransformerFactory.newInstance();
        Transformer serializer;
        try {
            serializer = tfactory.newTransformer();

            serializer.transform(new DOMSource(document), new StreamResult(new File(pathfile)));
        } catch (TransformerException e) {
            System.err.println("Error al salvar el documento");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * <p>
     * Devuelve el <code>Document</code> correspondiente al
     * <code>resource</code> pasado como parámetro
     * </p>
     *
     * @param resource El recurso que se desea obtener
     * @return El <code>Document</code> asociado al <code>resource</code>
     */
    protected Document getDocument(String resource) throws FirmaException {
        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        try {
            System.out.println(resource);
            doc = dbf.newDocumentBuilder().parse(new FileInputStream(new File(resource)));
//            Element el = doc.getDocumentElement();
//            System.out.println("----------------" + el.toString());
        } catch (ParserConfigurationException ex) {
            throw new FirmaException("Error al parsear el documento");
        } catch (SAXException ex) {
            throw new FirmaException("Error al parsear el documento");
        } catch (IOException ex) {
            throw new FirmaException("Error al parsear el documento");
        } catch (IllegalArgumentException ex) {
            throw new FirmaException("Error al parsear el documento");
        }
        return doc;
    }

    /**
     * <p>
     * Devuelve el contenido del documento XML correspondiente al
     * <code>resource</code> pasado como parámetro
     * </p> como un <code>String</code>
     *
     * @param resource El recurso que se desea obtener
     * @return El contenido del documento XML como un <code>String</code>
     */
    protected String getDocumentAsString(String resource) throws FirmaException {
        Document doc = getDocument(resource);
        TransformerFactory tfactory = TransformerFactory.newInstance();
        Transformer serializer;
        StringWriter stringWriter = new StringWriter();
        try {
            serializer = tfactory.newTransformer();
            serializer.transform(new DOMSource(doc), new StreamResult(stringWriter));
        } catch (TransformerException e) {
            throw new FirmaException("Error al imprimir el documento");
        }

        return stringWriter.toString();
    }

    /**
     * <p>
     * Devuelve el gestor de claves que se va a utilizar
     * </p>
     *
     * @return El gestor de claves que se va a utilizar</p>
     */
//    private IPKStoreManager getPKStoreManager() throws FirmaException {
//        IPKStoreManager storeManager = null;
//        String firma = this.getPathCertificado();
//        String clave = this.getClaveCertificado();
//
//        try {
//
//            if (clave == null || clave.trim().isEmpty()) {
//                throw new FirmaException("Error al leer clave de firma electronica");
//            }
//
//            if (!new File(firma).exists()) {
//                throw new FirmaException("No existe el recurso firma electronica");
//            }
//
//            KeyStore ks = KeyStore.getInstance("PKCS12");
//            try {
//                ks.load(new java.io.FileInputStream(firma), clave.toCharArray());
//            } catch (Exception e) {
//                throw new FirmaException("[ERROR] Las credenciales de las Firmas son Invalidas.");
//            }
//            storeManager = new KSStore(ks, new PassStoreKS(clave));
//            for (Enumeration<String> e = ks.aliases(); e.hasMoreElements();) {
//                String alias = e.nextElement();
//            }
//        } catch (Exception ex) {
//            throw new FirmaException("" + ex.getMessage());
//        }
//        return storeManager;
//    }
    private  IPKStoreManager getPKStoreManager()
    	    throws FirmaException
    	  {
    	    IPKStoreManager storeManager = null;
    	    String firma = getPathCertificado();
    	    String clave = getClaveCertificado();
    	    try
    	    {
    	      if ((clave == null) || (clave.trim().isEmpty())) {
    	        throw new FirmaException("Error al leer clave de firma electronica");
    	      }

    	      if (!(new File(firma).exists())) {
    	        throw new FirmaException("No existe el recurso firma electronica");
    	      }
    	      final char[] PKCS12_PASSWORD = clave.toCharArray();
    	      KeyStore ks = KeyStore.getInstance("PKCS12");
    	      try {
    	        ks.load(new FileInputStream(firma), PKCS12_PASSWORD);
    	      } catch (Exception e) {
    	        throw new FirmaException("[ERROR] Las credenciales de las Firmas son Invalidas.");
    	      }
    				
    	         storeManager = new KSStore(ks, new IPassStoreKS() {
    				public char[] getPassword(X509Certificate certificate, String alias) {
    					return PKCS12_PASSWORD;
    				}
    			});
    	      
    	    }
    	    catch (Exception ex) {
    	      throw new FirmaException(ex.getMessage());
    	    }
    	    return storeManager;
    	  }

    /**
     * <p>
     * Recupera el primero de los certificados del almacén.
     * </p>
     *
     * @param storeManager Interfaz de acceso al almacén
     * @return Primer certificado disponible en el almacén
     */
    private X509Certificate getFirstCertificate(
            final IPKStoreManager storeManager) throws FirmaException {
//        List<X509Certificate> certs = null;
    	X509Certificate certificado = null;
        try {
        	for (X509Certificate cert : storeManager.getSignCertificates()) {
        		if(cert.getKeyUsage()[0]) {
        			certificado = cert;
        		}
        	}
        } catch (CertStoreException ex) {
            throw new FirmaException("Fallo obteniendo listado de certificados");
        }
//        if ((certs == null) || (certs.size() == 0)) {
//            throw new FirmaException("Lista de certificados vacía");
//        }
        if(certificado == null) {
        	throw new FirmaException("Lista de certificados vacía");
        }
        return certificado;
    }

}
