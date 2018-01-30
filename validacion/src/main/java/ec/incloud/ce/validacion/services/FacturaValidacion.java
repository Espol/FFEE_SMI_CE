/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.incloud.ce.validacion.services;

import ec.incloud.ce.bean.factura.Factura;
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
final class FacturaValidacion implements ValidacionServices<Factura> {

    private static ValidacionServices<Factura> instance;
    private final String FACTURA_XSD = "/xsd/factura_1.1.0.xsd";
    private final Logger log = Logger.getLogger("integrador");

    private FacturaValidacion() {
    }

    static ValidacionServices<Factura> create() {
        synchronized (FacturaValidacion.class) {
            if (instance == null) {
                instance = new FacturaValidacion();
            }
            return instance;
        }
    }

    @Override
    public void validar(Factura comprobante) throws ValidacionException {
        log.debug("Validar la comprobante con XSD");
        String xml = null;

        try {
            xml = XmlFactory.getFacturaXmlServices().generarXml(comprobante);
        } catch (Exception e) {
            log.error(e);
        }

        SchemaFactory schemaFactory = SchemaFactory
                .newInstance("http://www.w3.org/2001/XMLSchema");
        Schema schema;
        try {

            schema = schemaFactory.newSchema(new StreamSource(ResourceUtil.getResourceStream(this.getClass(), FACTURA_XSD)));
        } catch (Exception e) {
            log.error("Error de sintaxis de esquema " + FACTURA_XSD, e);
            throw new ValidacionException("Error de sintaxis de esquema :\n" + FACTURA_XSD + "\n " + e.getMessage());
        }
        Validator validator = schema.newValidator();
        try {
            validator.validate(new StreamSource(new StringReader(xml)));
        } catch (Exception ex) {
            log.error("Error al validar XML ", ex);
            throw new ValidacionException(ex.getMessage());
        }
    }

}
