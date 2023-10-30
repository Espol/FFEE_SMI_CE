/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.incloud.ce.integrador.test;

import ec.incloud.ce.integrador.bean.MailSetting;
import ec.incloud.ce.integrador.util.MailSettingUtil;

/**
 *
 * @author Usuario
 */
public class MailSettingTest {
    public static void main(String... arg){
        MailSetting mail = new MailSetting();
        mail.setHost("mail.hivimar.com");
        mail.setPassword("");
        mail.setUser("hfe@hivimar.com");
        mail.setPassword("hivms33$");
        mail.setPort("587");
        
        MailSettingUtil util = MailSettingUtil.getInstance();
        System.out.println(util.toXml(mail));
        
        String xml = "<mailSetting><host>mail.hivimar.com</host><port>587</port><user>hfe@hivimar.com</user><password>hivms33$</password></mailSetting>";
        
        mail = util.toObject(xml);
    }
}
