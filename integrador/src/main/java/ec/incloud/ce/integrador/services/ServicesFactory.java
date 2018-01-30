/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.integrador.services;

/**
 *
 * @author Incloud Services S.A.C.
 */
public abstract class ServicesFactory {

    private static ServicesFactory factory = null;

    public static ServicesFactory getFactory() {
        synchronized (ServicesFactory.class) {
            if (factory == null) {
                factory = new DefaultServicesFactory();
            }
            return factory;
        }
    }

    public abstract DocumentoServices createDocumentoServices();

    public abstract SociedadServices createSociedadServices();

    public abstract PropertiesServices createPropertiesServices();

}
