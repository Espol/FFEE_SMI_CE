/*
 * Copyright (c) Inclod Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.integrador.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import ec.incloud.ce.integrador.bean.AckSRI;
import ec.incloud.ce.integrador.bean.Mensaje;

/**
 *
 * @author Inclod Services S.A.C.
 */
public class AckSRIUtil {

    private static AckSRIUtil INSTANCE;
    private final XStream xstream;

    private AckSRIUtil() {
        xstream = new XStream(new DomDriver("UTF-8"));
        xstream.alias("mensaje", Mensaje.class);
        xstream.alias("ackSRI", AckSRI.class);
        xstream.autodetectAnnotations(true);
    }

    public static AckSRIUtil getInstance() {
        synchronized (AckSRIUtil.class) {
            if (INSTANCE == null) {
                INSTANCE = new AckSRIUtil();
            }
            return INSTANCE;
        }
    }

    public String toXml(AckSRI ack) {
        return xstream.toXML(ack);
    }

    public AckSRI toObject(String xml) {
        return (AckSRI) xstream.fromXML(xml);
    }

}
