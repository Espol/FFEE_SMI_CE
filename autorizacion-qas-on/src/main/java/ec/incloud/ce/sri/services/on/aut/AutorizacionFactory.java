/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.sri.services.on.aut;

/**
 *
 * @author Incloud Services S.A.C.
 */
public abstract class AutorizacionFactory {

    private static AutorizacionFactory factory = null;

    public static AutorizacionFactory getFactory() {
        synchronized (AutorizacionFactory.class) {
            if (factory == null) {
                factory = new DefaultAutorizacionFactory();
            }
            return factory;
        }
    }

    public abstract AutorizacionServices getSriServices();
}
