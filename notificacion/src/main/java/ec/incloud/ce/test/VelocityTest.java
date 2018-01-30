/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.incloud.ce.test;

import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

/**
 *
 * @author Usuario
 */
public class VelocityTest {
    public static void main(String... arg){
        
//        Properties p = new Properties();
//        p.setProperty(Velocity.RESOURCE_LOADER, "class");
        
        //Velocity.
        VelocityEngine ve = new VelocityEngine();
        
        ve.setProperty(Velocity.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class",ClasspathResourceLoader.class.getName());
        ve.setProperty("input.encoding","UTF-8");
//        ve.setProperty("classpath.resource.loader.path", "notificacion");
//        ve.setProperty(Velocity., ve);
        ve.init();
        
        VelocityContext context = new VelocityContext();
        context.put("tipo", "FACTURA");
        context.put("nroSri", "001-001-00001");
        
        Template t = ve.getTemplate("notificacion/rechazado-0990129185001.html");
        
        StringWriter w = new StringWriter();
        t.merge(context, w);
        System.out.println("" + w.toString());
    }
    
    private void crearClase(){
        class miClase{
            private int a = 0;
            private int b = 0;
         
            public int getA(){
                return a;
            }
        }
        
        miClase abc = new miClase();
        int a = abc.getA();
       
    }
    
    
}
