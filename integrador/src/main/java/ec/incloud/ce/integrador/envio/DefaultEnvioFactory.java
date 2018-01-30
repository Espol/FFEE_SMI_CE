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
public class DefaultEnvioFactory extends EnvioFactory {

    @Override
    public Envio createEnvioServices() {
        return EnvioImpl.create();
    }

}
