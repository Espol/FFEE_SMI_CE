/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.integrador.envio;

/**
 *
 * @author Incloud Services S.A.C.
 */
public abstract class EnvioFactory {

    private static EnvioFactory factory = null;

    public static EnvioFactory getFactory() {
        synchronized (EnvioFactory.class) {
            if (factory == null) {
                factory = new DefaultEnvioFactory();
            }
            return factory;
        }
    }

    public abstract Envio createEnvioServices();

}
