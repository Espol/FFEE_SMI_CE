/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.incloud.ce.validacion.util;

/**
 *
 * @author INCLOUD SOLUTIONS S.A.C.
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ResourceUtil {
    /**
     * Returns a resource as a stream by checking:
     * 
     * 1. The classpath for a resource with the specified name
     * 2. For a file with the specified path
     * 
     * @param resourceName
     * @param clazz
     * @return
     * @throws FileNotFoundException
     */
    public static InputStream getResourceStream(Class<?> clazz, String resourceName) throws FileNotFoundException {
        String cpResourceName = null;
        
        if (!resourceName.startsWith("/")) {
            cpResourceName = "/" + resourceName;
        } else {
            cpResourceName = resourceName;
        }
        
        InputStream is = clazz.getResourceAsStream(cpResourceName);

        if (is == null) {
            is = new FileInputStream(resourceName);
        }

        return is;
    }
}
