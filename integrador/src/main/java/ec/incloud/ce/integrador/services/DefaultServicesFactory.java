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
public class DefaultServicesFactory extends ServicesFactory {

    @Override
    public DocumentoServices createDocumentoServices() {
        return DocumentoServicesImpl.create();
    }

    @Override
    public SociedadServices createSociedadServices() {
        return SociedadServicesImpl.create();
    }

    @Override
    public PropertiesServices createPropertiesServices() {
        return PropertiesServicesImpl.create();
    }
}
