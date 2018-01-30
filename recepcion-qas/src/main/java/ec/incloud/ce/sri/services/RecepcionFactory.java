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
public abstract class RecepcionFactory {

    private static RecepcionFactory factory = null;

    public static RecepcionFactory getFactory() {
        synchronized (RecepcionFactory.class) {
            if (factory == null) {
                factory = new DefaultRecepcionFactory();
            }
            return factory;
        }
    }

    public abstract RecepcionServices getSriServices();
}
