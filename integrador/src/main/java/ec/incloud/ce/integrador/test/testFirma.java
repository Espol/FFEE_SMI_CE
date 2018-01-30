package ec.incloud.ce.integrador.test;

import ec.incloud.ce.firma.exception.FirmaException;
import ec.incloud.ce.firma.services.FirmaFactory;
import ec.incloud.ce.firma.services.FirmaServices;

public class testFirma {
	
	private static final String xml = "C:\\test\\fa.xml";
	private static final String firma = "C:\\Program Files\\Incloud Middleware Service\\custom-lib\\certificate\\smi_facturacion_electronica.p12";
	private static final String clave = "Csti2016";
	
    
	public static void main(String... arg) throws FirmaException{
    	
//		Envio envio = EnvioFactory.getFactory().createEnvioServices();
//		envio.notificarByFirmaCaducada();
    	
    	firmarXML();
    }
    
    public static void firmarXML() throws FirmaException{

        FirmaServices services = FirmaFactory.createFirmaServices();
        services.firma( xml, firma, clave );
    }

}
