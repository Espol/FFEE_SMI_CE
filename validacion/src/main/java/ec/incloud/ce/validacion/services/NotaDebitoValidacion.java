/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.incloud.ce.validacion.services;

import ec.incloud.ce.bean.debito.NotaDebito;
import ec.incloud.ce.validacion.exception.ValidacionException;
import ec.incloud.ce.validacion.util.ResourceUtil;
import ec.incloud.ce.xml.services.XmlFactory;
import java.io.StringReader;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.apache.log4j.Logger;

/**
 *
 * @author Joel Povis Ocaña
 */
final class NotaDebitoValidacion implements ValidacionServices<NotaDebito> {

    private static ValidacionServices<NotaDebito> instance;
    private final String NOTA_DEBITO_XSD = "/xsd/notaDebito_1.0.0.xsd";
    private final Logger log = Logger.getLogger("integrador");

    private NotaDebitoValidacion() {
    }

    static ValidacionServices<NotaDebito> create() {
        synchronized (NotaDebitoValidacion.class) {
            if (instance == null) {
                instance = new NotaDebitoValidacion();
            }
            return instance;
        }
    }

    @Override
    public void validar(NotaDebito comprobante) throws ValidacionException {
        String xml = null;

        try {
            xml = XmlFactory.getNotaDebitoXmlServices().generarXml(comprobante);
        } catch (Exception e) {
            log.error(e);
        }

        log.debug("Validar la comprobante con XSD");
        SchemaFactory schemaFactory = SchemaFactory
                .newInstance("http://www.w3.org/2001/XMLSchema");
        Schema schema;
        try {

            schema = schemaFactory.newSchema(new StreamSource(ResourceUtil.getResourceStream(this.getClass(), NOTA_DEBITO_XSD)));
        } catch (Exception ex) {
            log.error("Error de sintaxis de esquema " + NOTA_DEBITO_XSD, ex);
            throw new ValidacionException("Error de sintaxis de esquema :\n" + NOTA_DEBITO_XSD + "\n " + ex.getMessage());
        }
        Validator validator = schema.newValidator();
        try {
            validator.validate(new StreamSource(new StringReader(xml)));
        } catch (Exception ex) {
            log.error("Error al validar XML ", ex);
            throw new ValidacionException("Error al validar XML: \n" + ex.getMessage());
        }
    }

}
