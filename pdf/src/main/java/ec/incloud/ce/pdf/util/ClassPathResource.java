package ec.incloud.ce.pdf.util;

import java.net.URI;
import java.net.URL;

/**
 *
 * @author INCLOUD S.A.C.
 */
public class ClassPathResource {

    public static URI getResourceURI(String resource) {
        // If nothing found, null is returned
        URI uri = null;
        try {

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
            e.printStackTrace();
        }
        return uri;
    }
}
