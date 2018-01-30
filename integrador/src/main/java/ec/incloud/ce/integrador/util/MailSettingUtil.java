/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.integrador.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import ec.incloud.ce.integrador.bean.MailSetting;

/**
 *
 * @author Incloud Services S.A.C.
 */
public class MailSettingUtil {

    private static MailSettingUtil INSTANCE;
    private final XStream xstream;

    private MailSettingUtil() {
        xstream = new XStream(new DomDriver("UTF-8"));
        xstream.alias("mailSetting", MailSetting.class);
        xstream.autodetectAnnotations(true);
    }

    public static MailSettingUtil getInstance() {
        synchronized (MailSettingUtil.class) {
            if (INSTANCE == null) {
                INSTANCE = new MailSettingUtil();
            }
            return INSTANCE;
        }
    }

    public String toXml(MailSetting ack) {
        return xstream.toXML(ack);
    }

    public MailSetting toObject(String xml) {
        return (MailSetting) xstream.fromXML(xml);
    }

}
