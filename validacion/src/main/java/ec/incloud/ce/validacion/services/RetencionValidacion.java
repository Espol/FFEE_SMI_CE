/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.incloud.ce.validacion.services;

import ec.incloud.ce.bean.retencion.ComprobanteRetencion;
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
final class RetencionValidacion implements ValidacionServices<ComprobanteRetencion> {

    private static ValidacionServices<ComprobanteRetencion> instance;
    private final String RETENCION_XSD = "/xsd/retencion_1.0.0.xsd";
    private final Logger log = Logger.getLogger("integrador");

    private RetencionValidacion() {
    }

    static ValidacionServices<ComprobanteRetencion> create() {
        synchronized (RetencionValidacion.class) {
            if (instance == null) {
                instance = new RetencionValidacion();
            }
            return instance;
        }
    }

    @Override
    public void validar(ComprobanteRetencion comprobante) throws ValidacionException {
        String xml = null;

        try {
            xml = XmlFactory.getRetencionServices().generarXml(comprobante);
        } catch (Exception e) {
            log.error(e);
        }

        SchemaFactory schemaFactory = SchemaFactory
                .newInstance("http://www.w3.org/2001/XMLSchema");
        Schema schema;
        try {

            schema = schemaFactory.newSchema(new StreamSource(ResourceUtil.getResourceStream(this.getClass(), RETENCION_XSD)));
        } catch (Exception ex) {
            log.error("Error de sintaxis de esquema " + RETENCION_XSD, ex);
            throw new ValidacionException("Error de sintaxis de esquema :\n" + RETENCION_XSD + "\n " + ex.getMessage());
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
