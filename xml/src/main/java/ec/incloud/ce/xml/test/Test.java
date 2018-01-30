package ec.incloud.ce.xml.test;

import ec.incloud.ce.bean.guia.GuiaRemision;
import ec.incloud.ce.xml.services.XmlFactory;

/**
 *
 * @author Usuario
 */
public class Test {

    public static void main(String[] args) {
        try {
            GuiaRemision f = XmlFactory.getGuiaRemisionXmlServices().getComprobanteDePathArchivo("E:\\Comprobantes\\quimpac\\guia.xml");
            System.out.println("Coorecto: " + f);
        } catch (Exception e) {
            System.out.println("" + e);
        }
    }
}
