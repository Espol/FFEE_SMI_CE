/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.integrador.services;

import ec.incloud.ce.integrador.bean.DataBaseSettings;

/**
 *
 * @author Incloud Services S.A.C.
 */
public interface PropertiesServices {

    public DataBaseSettings getDatabaseSettings();

    public int getPoolEnvioSri();

    public int getPoolMaxEnvioSri();

    public int getPoolEnvioSap();

    public int getPoolMaxEnvioSap();
    
    public int getPoolMaxRechazadoAutorizacion();

}
