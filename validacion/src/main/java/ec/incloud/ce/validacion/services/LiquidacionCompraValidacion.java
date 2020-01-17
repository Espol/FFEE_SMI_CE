package ec.incloud.ce.validacion.services;

import java.io.StringReader;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.log4j.Logger;

import ec.incloud.ce.bean.liquidacionCompra.LiquidacionCompra;
import ec.incloud.ce.validacion.exception.ValidacionException;
import ec.incloud.ce.validacion.util.ResourceUtil;
import ec.incloud.ce.xml.services.XmlFactory;

public class LiquidacionCompraValidacion implements ValidacionServices<LiquidacionCompra> {
	
	private static LiquidacionCompraValidacion instance;
	private final String LIQUIDACIONCOMPRA_XSD = "/xsd/liquidacionCompra_1.1.0.xsd";
    private final Logger log = Logger.getLogger("integrador");
	
	private LiquidacionCompraValidacion () {
		
	}
	
	public static LiquidacionCompraValidacion create () {
		synchronized (LiquidacionCompraValidacion.class) {
			if(instance ==  null) {
				instance = new  LiquidacionCompraValidacion();
			}
			return instance;
		}
	}

	@Override
	public void validar(LiquidacionCompra comprobante) throws ValidacionException {
		log.debug("Validar la comprobante con XSD");
        String xml = null;

        try {
            xml = XmlFactory.getLiquidacionCompraService().generarXml(comprobante);
        } catch (Exception e) {
            log.error(e);
        }

        SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        Schema schema;
        try {

            schema = schemaFactory.newSchema(new StreamSource(ResourceUtil.getResourceStream(this.getClass(), LIQUIDACIONCOMPRA_XSD)));
        } catch (Exception e) {
            log.error("Error de sintaxis de esquema " + LIQUIDACIONCOMPRA_XSD, e);
            throw new ValidacionException("Error de sintaxis de esquema :\n" + LIQUIDACIONCOMPRA_XSD + "\n " + e.getMessage());
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
