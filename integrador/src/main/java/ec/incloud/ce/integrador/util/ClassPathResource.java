package ec.incloud.ce.integrador.util;

import java.net.URI;
import java.net.URL;

import org.apache.log4j.Logger;

public class ClassPathResource {

    private static Logger logger = Logger.getLogger(ClassPathResource.class);

    public static URI getResourceURI(String resource) {
        URI uri = null;
        try {
            logger.debug("Loading: " + resource);
            URL url = ClassPathResource.class.getResource(resource);

            if (url == null) {
                if (resource.charAt(0) == '/') {
                    resource = resource.substring(1);
                } else {
                    resource = "/" + resource;
                }
                url = ClassPathResource.class.getResource(resource);
            }
            if (url != null) {
                uri = url.toURI();
            }
        } catch (Exception e) {
            logger.error("Could not load resource.", e);
        }
        return uri;
    }
}
