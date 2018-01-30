/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.incloud.ce.validacion.services;

import java.io.StringReader;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.log4j.Logger;

import ec.incloud.ce.bean.facturaExportacion.FacturaExportacion;
import ec.incloud.ce.validacion.exception.ValidacionException;
import ec.incloud.ce.validacion.util.ResourceUtil;
import ec.incloud.ce.xml.services.XmlFactory;

/**
 *
 * @author Joel Povis Ocaña
 */
final class FacturaExportacionValidacion implements ValidacionServices<FacturaExportacion> {

    private static ValidacionServices<FacturaExportacion> instance;
    private final String FACTURA_XSD = "/xsd/factura_1.1.0.xsd";
    private final Logger log = Logger.getLogger("integrador");

    private FacturaExportacionValidacion() {
    }

    static ValidacionServices<FacturaExportacion> create() {
        synchronized (FacturaExportacionValidacion.class) {
            if (instance == null) {
                instance = new FacturaExportacionValidacion();
            }
            return instance;
        }
    }

    @Override
    public void validar(FacturaExportacion comprobante) throws ValidacionException {
        log.debug("Validar la comprobante con XSD");
        String xml = null;

        try {
            xml = XmlFactory.getFacturaExportacionXmlServices().generarXml(comprobante);
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
