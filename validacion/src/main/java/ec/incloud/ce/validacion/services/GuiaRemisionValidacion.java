/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.incloud.ce.validacion.services;

import ec.incloud.ce.bean.guia.GuiaRemision;
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
final class GuiaRemisionValidacion implements ValidacionServices<GuiaRemision> {

    private static ValidacionServices<GuiaRemision> instance;
    private final String GUIA_XSD = "/xsd/guiaRemision_1.1.0.xsd";
    private final Logger log = Logger.getLogger("integrador");

    private GuiaRemisionValidacion() {
    }

    static ValidacionServices<GuiaRemision> create() {
        synchronized (GuiaRemisionValidacion.class) {
            if (instance == null) {
                instance = new GuiaRemisionValidacion();
            }
            return instance;
        }
    }

    @Override
    public void validar(GuiaRemision comprobante) throws ValidacionException {
        log.debug("Validar la comprobante con XSD");
        String xml = null;
        try {
            xml = XmlFactory.getGuiaRemisionXmlServices().generarXml(comprobante);
        } catch (Exception e) {
            log.error(e);
        }

        SchemaFactory schemaFactory = SchemaFactory
                .newInstance("http://www.w3.org/2001/XMLSchema");
        Schema schema;
        try {

            schema = schemaFactory.newSchema(new StreamSource(ResourceUtil.getResourceStream(this.getClass(), GUIA_XSD)));
        } catch (Exception e) {
            log.error("Error de sintaxis de esquema " + GUIA_XSD, e);
            throw new ValidacionException("Error de sintaxis de esquema :\n" + GUIA_XSD + "\n " + e.getMessage());
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
