/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.incloud.ce.integrador.test;

import java.util.List;

import ec.incloud.ce.integrador.bean.Documento;
import ec.incloud.ce.integrador.bean.MailSetting;
import ec.incloud.ce.integrador.bean.Usuario;
import ec.incloud.ce.integrador.exception.IntegradorException;
import ec.incloud.ce.integrador.services.DocumentoServices;
import ec.incloud.ce.integrador.services.ServicesFactory;
import ec.incloud.ce.integrador.util.MailSettingUtil;

/**
 *
 * @author Usuario
 */
public class DocumentosPorAutorizarTest {
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
                
                System.out.println("" + mailSetting.getHost());
                System.out.println("" + mailSetting.getPassword());
                System.out.println("" + mailSetting.getPort());
                System.out.println("" + mailSetting.getUser());
                System.out.println("Mail Destino : " + doc.getMailDestino());
            }
            
		} catch (IntegradorException e) {
			e.printStackTrace();
		}
        
        
    }
}
