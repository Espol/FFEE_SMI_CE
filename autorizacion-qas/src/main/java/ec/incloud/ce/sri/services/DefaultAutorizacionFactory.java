/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.sri.services;

/**
 *
 * @author Incloud Services S.A.C.
 */
public class DefaultAutorizacionFactory extends AutorizacionFactory {

    @Override
    public AutorizacionServices getSriServices() {
        return AutorizacionServicesImpl.getInstance();
    }

}
