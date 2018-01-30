
package ec.incloud.ce.integrador.test;

import ec.incloud.ce.integrador.bean.Documento;
import ec.incloud.ce.integrador.exception.IntegradorException;
import ec.incloud.ce.integrador.services.DocumentoServices;
import ec.incloud.ce.integrador.services.ServicesFactory;

/**
 *
 * @author Joel Povis Ocaña
 */
public class MensajeServicesTest {
    public static void main(String... arg) throws IntegradorException {
        DocumentoServices services = ServicesFactory.getFactory().createDocumentoServices();
        java.util.List<Documento> listDocumento = services.getlstDocumentoPorEnviarSriAutorizacion();
        
        System.out.println("" + listDocumento.size());
    }
}
