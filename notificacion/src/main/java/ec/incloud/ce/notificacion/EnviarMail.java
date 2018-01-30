/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.incloud.ce.notificacion;

import java.io.File;
import java.text.MessageFormat;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.log4j.Logger;

/**
 *
 * @author Usuario
 */
class EnviarMail  {

    private final Logger log = Logger.getLogger(EnviarMail.class);
   
    public boolean enviarCorreo(MailProperties mailProp, 
                             String asunto,
                             String mensaje,
                             String from,
                             String cc,
                             String cco,
                             List<String> archivoAdjunto) {
        
        cco = (cco == null) ? MessageFormat.format("{0}", mailProp.getUsuario()) :
                              MessageFormat.format("{0},{1}", cco, mailProp.getUsuario());
        
        try {

            Properties propiedades = new Properties();

            propiedades.put("mail.smtp.starttls.enable", true);
            propiedades.put("mail.smtp.host", mailProp.getHost());
            propiedades.put("mail.smtp.user", mailProp.getUsuario());
            propiedades.put("mail.smtp.password", mailProp.getPassword());
            propiedades.put("mail.smtp.port", mailProp.getPort());
            propiedades.put("mail.smtp.auth", true);
            propiedades.put("mail.smtp.debug", true);
            propiedades.put("mail.smtp.ssl.trust", mailProp.getHost());
            
            MimeMultipart multiParte = new MimeMultipart();
            Session session = Session.getDefaultInstance(propiedades);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(propiedades.getProperty("mail.smtp.user").trim()));

            if (from != null && !from.isEmpty()) {
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(from));
            }
            
            if (cc != null && !cc.isEmpty()) {
                message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));
            }
            
            if (cco != null && !cco.isEmpty()) {
                message.setRecipients(Message.RecipientType.BCC,
                        InternetAddress.parse(cco));
            }
            
//            message.setSubject(asunto, "UTF-8");
            message.setSubject(asunto, "ISO-8859-1");
            
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setContent(mensaje, "text/html");
            multiParte.addBodyPart(textPart);

            if (archivoAdjunto != null && !archivoAdjunto.isEmpty()) {
                log.debug("Adjuntando documentos");
                for (int i = 0; i < archivoAdjunto.size(); i++) {
                    log.debug(MessageFormat.format("Adjuntando archivo {0}", (i + 1)));

                    if (archivoAdjunto.get(i) != null || !archivoAdjunto.get(i).trim().isEmpty()) {
                        BodyPart adjunto = new MimeBodyPart();
                        adjunto.setDataHandler(new DataHandler(new FileDataSource(archivoAdjunto.get(i))));
                        
                        File archivo = new File(archivoAdjunto.get(i));
                        
                        if (!archivo.exists()) {
                            log.warn("El archivo no existe " + archivoAdjunto.get(i));
                            throw new Exception("Archivo no existe " + archivoAdjunto.get(i));
                        }
                        
                        String fileName = archivo.getName();
                        adjunto.setFileName(fileName);
                        multiParte.addBodyPart(adjunto);
                    }
                }
            }
            
            message.setContent(multiParte);
            log.debug("Preparando Envio de Mensaje");

            Transport t = session.getTransport("smtp");
            
            t.connect(propiedades.getProperty("mail.smtp.user").trim(),
                      propiedades.getProperty("mail.smtp.password").trim());
            log.debug("Enviando....");
            t.sendMessage(message, message.getAllRecipients());
            t.close();
            log.info("Mensaje enviado ");
            return true;
        } catch (Exception e) {
            log.error("Error al enviar mensaje : ",e);
            return false;
        }

    }
}
