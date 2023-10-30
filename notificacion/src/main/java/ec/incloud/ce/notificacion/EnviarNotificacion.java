/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.incloud.ce.notificacion;

import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.List;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

/**
 *
 * @author Usuario
 */
public enum EnviarNotificacion implements Notificacion{
	
    NO_AUTORIZADO {
        @Override
        public boolean enviarCorreo(
        						String sociedad[], 
        						MailProperties mailProp, 
                                 String tipoDocumento, 
                                 String numeroSri, 
                                 String mensajeAdicional, 
                                 String from, 
                                 String cc, 
                                 String cco, 
                                 List<String> archivoAdjunto) {
        	
        	String rucEmisor = sociedad[0];
//        	String nomComercial = sociedad[1];
        	
        	String pathHtmlNotificacion = TemplateNotificacion.NO_AUTORIZADO + rucEmisor + ".html";
            EnviarMail mail = new EnviarMail();
            VelocityContext context = new VelocityContext();
            context.put("tipo", tipoDocumento);
            context.put("nroSri", numeroSri);
        
            Template t = this.velocity.getTemplate(pathHtmlNotificacion);
            
            StringWriter w = new StringWriter();
            t.merge(context, w);
//            String titulo = "Usted ha recibido un Comprobante Electrónico de " + nomComercial;
            String titulo = MessageFormat.format("NOTIFICACIÓN: {0} - {1} - {2}", 
                    tipoDocumento,
                    "NO AUTORIZADO",
                    numeroSri);
            return mail.enviarCorreo(mailProp, titulo, w.toString(), from, cc, cco, archivoAdjunto);
        }
        
    },
    AUTORIZADO_CONFIRMACION {
        @Override
        public boolean enviarCorreo(
				String sociedad[], 
								MailProperties mailProp, 
                                 String tipoDocumento, 
                                 String numeroSri, 
                                 String mensajeAdicional, 
                                 String from, 
                                 String cc, 
                                 String cco, 
                                 List<String> archivoAdjunto) {
        	
        	String rucEmisor = sociedad[0];
//        	String nomComercial = sociedad[1];
//        	String userNamePortal = sociedad[2];
//        	String passwordPortal = sociedad[3];
//        	String flag = sociedad[4];
        	
        	String pathHtmlNotificacion = TemplateNotificacion.AUTORIZADO_CONFIRMACION + rucEmisor + ".html";
            EnviarMail mail = new EnviarMail();
            VelocityContext context = new VelocityContext();
            context.put("tipo", tipoDocumento);
            context.put("nroSri", numeroSri);
//            context.put("user", userNamePortal);
//            context.put("pass", passwordPortal);
//            context.put("visualizarPassword", flag);
        
            Template t = this.velocity.getTemplate(pathHtmlNotificacion);
            
            StringWriter w = new StringWriter();
            t.merge(context, w);
            
            String titulo = MessageFormat.format("NOTIFICACIÓN: {0} - {1} - {2}", 
                    tipoDocumento,
                    "AUTORIZADO",
                    numeroSri);
            
            return mail.enviarCorreo(mailProp, titulo, w.toString(), from, cc, cco, archivoAdjunto);
        }
        
    },
    AUTORIZADO_ONLINE {
        @Override
        public boolean enviarCorreo(
				String sociedad[], 
								MailProperties mailProp,
                                 String tipoDocumento, 
                                 String numeroSri, 
                                 String mensajeAdicional, 
                                 String from, 
                                 String cc, 
                                 String cco, 
                                 List<String> archivoAdjunto) {
        	
        	String rucEmisor = sociedad[0];
//        	String nomComercial = sociedad[1];
//        	String userNamePortal = sociedad[2];
//        	String passwordPortal = sociedad[3];
//        	String flag = sociedad[4];
        	
        	String pathHtmlNotificacion = TemplateNotificacion.AUTORIZADO + rucEmisor + ".html";
            EnviarMail mail = new EnviarMail();
            VelocityContext context = new VelocityContext();
            context.put("tipo", tipoDocumento);
            context.put("nroSri", numeroSri);
//            context.put("user", userNamePortal);
//            context.put("pass", passwordPortal);
//            context.put("visualizarPassword", flag);
        
            Template t = this.velocity.getTemplate(pathHtmlNotificacion);
            
            StringWriter w = new StringWriter();
            t.merge(context, w);
            
            String titulo = MessageFormat.format("NOTIFICACIÓN: {0} - {1} - {2}", 
                    tipoDocumento,
                    "AUTORIZADO",
                    numeroSri);
            
            return mail.enviarCorreo(mailProp, titulo, w.toString(), from, cc, cco, archivoAdjunto);
        }
        
    },
    AUTORIZADO {
        @Override
        public boolean enviarCorreo(
				String sociedad[], 
				        		MailProperties mailProp, 
                                 String tipoDocumento, 
                                 String numeroSri, 
                                 String mensajeAdicional, 
                                 String from, 
                                 String cc, 
                                 String cco, 
                                 List<String> archivoAdjunto) {
        	
        	String rucEmisor = sociedad[0];
        	String nomComercial = sociedad[1];
//        	String userNamePortal = sociedad[2];
//        	String passwordPortal = sociedad[3];
//        	String flag = sociedad[4];
        	
        	String pathHtmlNotificacion = TemplateNotificacion.AUTORIZADO + rucEmisor + ".html";;
            EnviarMail mail = new EnviarMail();
            VelocityContext context = new VelocityContext();
            context.put("tipo", tipoDocumento);
            context.put("nroSri", numeroSri);
//            context.put("user", userNamePortal);
//            context.put("pass", passwordPortal);
//            context.put("visualizarPassword", flag);
        
            Template t = this.velocity.getTemplate(pathHtmlNotificacion);
            
            StringWriter w = new StringWriter();
            t.merge(context, w);
            String titulo = "Usted ha recibido un Comprobante Electrónico de " + nomComercial + " : " + numeroSri;
            return mail.enviarCorreo(mailProp, titulo, w.toString(), from, cc, cco, archivoAdjunto);
        }
        
    },
    NOTIFICACION_RECHAZADO {
        @Override
        public boolean enviarCorreo(
				String sociedad[], 
								MailProperties mailProp, 
                                 String tipoDocumento, 
                                 String numeroSri, 
                                 String mensajeAdicional, 
                                 String from, 
                                 String cc, 
                                 String cco,
                                 List<String> archivoAdjunto) {
        	
        	String rucEmisor = sociedad[0];
//        	String nomComercial = sociedad[1];
        	String timeAvailableCorrecion = sociedad[2];
        	String pathHtmlNotificacion = TemplateNotificacion.NOTIFICACION_RECHAZADO + rucEmisor + ".html";
            EnviarMail mail = new EnviarMail();
            VelocityContext context = new VelocityContext();
            context.put("tipo", tipoDocumento);
            context.put("nroSri", numeroSri);
            context.put("mensajeAdicional", mensajeAdicional);
            context.put( "timeAvailableCorrecion", timeAvailableCorrecion );//tiempo disponible para corregir el comprobante
            
            Template t = this.velocity.getTemplate(pathHtmlNotificacion);
            
            StringWriter w = new StringWriter();
            t.merge(context, w);
            String titulo = MessageFormat.format("NOTIFICACIÓN: {0} - {1} - {2}", 
                                                 tipoDocumento,
                                                 "NO AUTORIZADO",//rechazado
                                                 numeroSri);
            return mail.enviarCorreo(mailProp, titulo, w.toString(), from, cc, cco, archivoAdjunto);
        }
        
    },
    RECHAZADO {
        @Override
        public boolean enviarCorreo(
				String sociedad[], 
								MailProperties mailProp, 
                                 String tipoDocumento, 
                                 String numeroSri, 
                                 String mensajeAdicional, 
                                 String from, 
                                 String cc, 
                                 String cco, 
                                 List<String> archivoAdjunto) {
        	
        	String rucEmisor = sociedad[0];
//        	String nomComercial = sociedad[1];
        	String pathHtmlNotificacion = TemplateNotificacion.RECHAZADO + rucEmisor + ".html";
            EnviarMail mail = new EnviarMail();
            VelocityContext context = new VelocityContext();
            context.put("tipo", tipoDocumento);
            context.put("nroSri", numeroSri);
            context.put("mensajeAdicional", mensajeAdicional);
            Template t = this.velocity.getTemplate(pathHtmlNotificacion);
            
            StringWriter w = new StringWriter();
            t.merge(context, w);
            String titulo = MessageFormat.format("NOTIFICACIÓN: {0} - {1} - {2}", 
                                                 tipoDocumento,
                                                 "NO AUTORIZADO",
                                                 numeroSri);
            return mail.enviarCorreo(mailProp, titulo, w.toString(), from, cc, cco, archivoAdjunto);
        }
        
    },
    INCONSISTENTE {
        @Override
        public boolean enviarCorreo(
				String sociedad[], 
				        		MailProperties mailProp, 
                                 String tipoDocumento, 
                                 String numeroSri, 
                                 String mensajeAdicional, 
                                 String from, 
                                 String cc, 
                                 String cco, 
                                 List<String> archivoAdjunto) {
        	
        	String rucEmisor = sociedad[0];
//        	String nomComercial = sociedad[1];
        	String pathHtmlNotificacion = TemplateNotificacion.INCONSISTENTE + rucEmisor + ".html";;
            EnviarMail mail = new EnviarMail();
            VelocityContext context = new VelocityContext();
            context.put("tipo", tipoDocumento);
            context.put("nroSri", numeroSri);
            context.put("mensajeAdicional", mensajeAdicional);
            Template t = this.velocity.getTemplate(pathHtmlNotificacion);
            
            StringWriter w = new StringWriter();
            t.merge(context, w);
            String titulo = MessageFormat.format("NOTIFICACIÓN: {0} - {1} - {2}", 
                                                 tipoDocumento,
                                                 "INCONSISTENTE",
                                                 numeroSri);
            return mail.enviarCorreo(mailProp, titulo, w.toString(), from, cc, cco, archivoAdjunto);
        }
        
    },
    NOTIFICACION_FIRMA {
        @Override
        public boolean enviarCorreo(
				String sociedad[],
								MailProperties mailProp,
                                 String tipoDocumento,
                                 String numeroSri,
                                 String mensajeAdicional,
                                 String from,
                                 String cc,
                                 String cco,
                                 List<String> archivoAdjunto) {        	
        	String rucEmisor = sociedad[0];
        	String fechaCaducidadFirma = sociedad[1];
        	String pathHtmlNotificacion = TemplateNotificacion.NOTIFICACION_FIRMA + rucEmisor + ".html";
            EnviarMail mail = new EnviarMail();
            VelocityContext context = new VelocityContext();
            context.put("tipo", tipoDocumento);
            context.put("nroSri", numeroSri);
            context.put("fechaCaducidadFirma", fechaCaducidadFirma);
            Template t = this.velocity.getTemplate(pathHtmlNotificacion);
            
            StringWriter w = new StringWriter();
            t.merge(context, w);
            String titulo = MessageFormat.format("NOTIFICACIÓN: {0} ", "CADUCIDAD DE LA FIRMA ELECTRÓNICA");
            
            return mail.enviarCorreo(mailProp, titulo, w.toString(), from, cc, cco, archivoAdjunto);
        }
    };
    
    VelocityEngine velocity;
    
    private EnviarNotificacion() {
        this.velocity = new VelocityEngine();
        this.velocity.setProperty(Velocity.RESOURCE_LOADER, "classpath");
        this.velocity.setProperty("classpath.resource.loader.class",ClasspathResourceLoader.class.getName());
        this.velocity.setProperty("input.encoding","UTF-8");
        this.velocity.init();
    }
}
