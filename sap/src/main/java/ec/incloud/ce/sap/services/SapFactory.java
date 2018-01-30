/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.sap.services;

/**
 *
 * @author Incloud Services S.A.C.
 */
public class SapFactory {

    public static SapServices createSapServices() {
        return DefaultSapServices.create();
    }
}
