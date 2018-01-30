package ec.incloud.ce.integrador.test;

import java.util.List;

import ec.incloud.ce.integrador.bean.Documento;
import ec.incloud.ce.integrador.bean.MailSetting;
import ec.incloud.ce.integrador.bean.Usuario;
import ec.incloud.ce.integrador.envio.Envio;
import ec.incloud.ce.integrador.envio.EnvioFactory;
import ec.incloud.ce.integrador.exception.IntegradorException;
import ec.incloud.ce.integrador.services.DocumentoServices;
import ec.incloud.ce.integrador.services.ServicesFactory;
import ec.incloud.ce.integrador.util.MailSettingUtil;

public class TestEnvioSRI {
	
	public static void main(String... arg){
        DocumentoServices services = ServicesFactory.getFactory().createDocumentoServices();
        List<Documento> lst = services.getlstDocumentoPorEnviarSriAutorizacion();
        try {
        	for(Documento doc : lst){
                MailSetting mailSetting =
                MailSettingUtil.getInstance().toObject(doc.getSociedad().getMailSettings());
                Usuario usuario;
    			usuario = services.getClavePortal(doc.getRucCliente());
//                services.notificaAutorizadoCliente(doc);
                services.notificaAutorizadoCliente(doc,usuario);
            }
            
            int a = 0;
		} catch (IntegradorException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    }
	
	public void enviarRecepcionSRI(){
		Envio envio = EnvioFactory.getFactory().createEnvioServices();
		envio.enviarDocumentoRecepcion();
	}
	
	public void enviarAutorizarSRI(){
//		Envio envio = EnvioFactory.getFactory().createEnvioServices();
//		envio.enviarDocumentoAutorizacion();
		
		DocumentoServices services = ServicesFactory.getFactory().createDocumentoServices();
        List<Documento> lst = services.getlstDocumentoPorEnviarSriAutorizacion();
        
        try {
        	for(Documento doc : lst){
                Usuario usuario = services.getClavePortal(doc.getRucCliente());
                
            }
		} catch (IntegradorException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
