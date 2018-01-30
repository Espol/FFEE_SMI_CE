/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.incloud.ce.integrador.test;

import ec.incloud.ce.integrador.util.Util;
import java.io.File;
import java.util.Date;

/**
 *
 * @author Usuario
 */
public class PathTest {
    public static void main(String... arg){
        String name = "D:\\PruebasMirth\\ComprobantesHivimar\\factura\\factura-FSX-12345-001-002-000000006-27-10-2015-11-57-21.xml";
         Util util = Util.INSTANCE;
        
         StringBuilder p = new StringBuilder();
        p.append( (new File(name)).getParent());
        p.append(File.separator);
        p.append("factura");
        p.append("-");
        p.append("FSX");
        p.append("-");
        p.append("12345");
        p.append("-");
        p.append("001-002-000000006");
        p.append("-");
        p.append(util.getStringFromDateXmlName(new Date()));
        p.append(".xml");
        System.out.println("" + p.toString());
    }
}
