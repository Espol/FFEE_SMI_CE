package ec.incloud.ce.integrador.test;

import ec.incloud.ce.integrador.envio.Envio;
import ec.incloud.ce.integrador.envio.EnvioFactory;

public class EnvioAutorizacionTest {
	
	public static void main(String[] args) {

		System.out.println("inicio");
		Envio envio = EnvioFactory.getFactory().createEnvioServices();
		envio.enviarDocumentoAutorizacion();
		System.out.println("fin");

		
	}

}
