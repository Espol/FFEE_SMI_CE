/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.integrador.services;

import ec.incloud.ce.integrador.bean.DataBaseSettings;
import org.apache.log4j.Logger;
import ec.incloud.ce.integrador.util.ResourceUtil;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Incloud Services S.A.C.
 */
public class PropertiesServicesImpl implements PropertiesServices {

    private final Logger log = Logger.getLogger(PropertiesServices.class);
    private static PropertiesServices instance = null;
    private Properties integradorConfig = new Properties();
    private DataBaseSettings databaseSettings;

    private final String INTEGRADOR_PROPERTIES = "integrador.properties";
    private final int DEFAULT_POOL_ENVIO_SRI = 20;
    private final int DEFAULT_ENVIO_MAX_SRI = 10;
    private final int DEFAULT_POOL_ENVIO_SAP = 10;
    private final int DEFAULT_ENVIO_MAX_SAP = 10;
    private final int DEFAULT_POOL_MAX_RECHAZO_AUTORIZADO = 10;

    public int getPoolMaxRechazadoAutorizacion() {
		return (integradorConfig.getProperty("pool.max-rechazo_autorizado") == null) ? 
				DEFAULT_POOL_MAX_RECHAZO_AUTORIZADO : 
                Integer.parseInt(integradorConfig.getProperty("pool.max-rechazo_autorizado"));
	}

	private void initialize() {
        try {
            //integradorConfig.setDelimiterParsingDisabled(true);
            //integradorConfig.setFile(new File());
            InputStream ie = ResourceUtil.getResourceStream(PropertiesServicesImpl.class, INTEGRADOR_PROPERTIES);
            integradorConfig.load(ie);
            
            databaseSettings = new DataBaseSettings(integradorConfig);
        } catch (Exception e) {
            log.error("Error al cargar parametros del Integrador " + e);
            throw new RuntimeException(e);
        }
    }

    private PropertiesServicesImpl() {
    }

    static PropertiesServices create() {
        synchronized (PropertiesServicesImpl.class) {
            if (instance == null) {
                instance = new PropertiesServicesImpl();
                ((PropertiesServicesImpl) instance).initialize();
            }
            return instance;
        }
    }
    
    @Override
    public DataBaseSettings getDatabaseSettings() {
        return databaseSettings;
    }

    @Override
    public int getPoolEnvioSri() {
        return (integradorConfig.getProperty("pool.max-sri") == null) ? 
                DEFAULT_POOL_ENVIO_SRI :
                Integer.parseInt(integradorConfig.getProperty("pool.max-sri"));
    }

    @Override
    public int getPoolEnvioSap() {
        return (integradorConfig.getProperty("pool.max-sap") == null) ? 
                DEFAULT_POOL_ENVIO_SAP : 
                Integer.parseInt(integradorConfig.getProperty("pool.max-sap"));
    }

    @Override
    public int getPoolMaxEnvioSri() {
        return (integradorConfig.getProperty("envio.max-sri") == null) ? 
                DEFAULT_ENVIO_MAX_SRI : 
                Integer.parseInt(integradorConfig.getProperty("envio.max-sri"));
    }

    @Override
    public int getPoolMaxEnvioSap() {
        return (integradorConfig.getProperty("envio.max-sap") == null) ? 
                DEFAULT_ENVIO_MAX_SAP : 
                Integer.parseInt(integradorConfig.getProperty("envio.max-sap"));
    }
}
