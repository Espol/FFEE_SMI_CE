/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.integrador.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import ec.incloud.ce.integrador.bean.SapSettings;

/**
 *
 * @author Incloud Services S.A.C.
 */
public class SapSettingUtil {

    private static SapSettingUtil INSTANCE;
    private final XStream xstream;

    private SapSettingUtil() {
        xstream = new XStream(new DomDriver("UTF-8"));
        xstream.alias("sapSetting", SapSettings.class);
        xstream.autodetectAnnotations(true);
    }

    public static SapSettingUtil getInstance() {
        synchronized (SapSettingUtil.class) {
            if (INSTANCE == null) {
                INSTANCE = new SapSettingUtil();
            }
            return INSTANCE;
        }
    }

    public String toXml(SapSettings ack) {
        return xstream.toXML(ack);
    }

    public SapSettings toObject(String xml) {
        return (SapSettings) xstream.fromXML(xml);
    }

}
