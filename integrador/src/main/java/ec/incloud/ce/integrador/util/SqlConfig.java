/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.integrador.util;

import java.io.InputStream;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.SqlSessionManager;
import org.apache.log4j.Logger;

import ec.incloud.ce.integrador.bean.DataBaseSettings;
import ec.incloud.ce.integrador.services.ServicesFactory;

/**
 *
 * @author Incloud Services S.A.C.
 */
public class SqlConfig {

    private static SqlSessionFactory sqlSessionfactory;
    private static SqlSessionManager sqlSessionManager = null;
    private static final String MYBATIS_CONFIG = "conf_integrador/mybatis-config.xml";
    private static final Logger log = Logger.getLogger(SqlConfig.class);
    private SqlConfig() {
    }

    public static SqlSessionManager getSqlSessionManager() {
        synchronized (SqlConfig.class) {
            if (sqlSessionManager == null) {
                init();
            }
            return sqlSessionManager;
        }
    }

    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionfactory;
    }

    public static void init() {
        try {
            DataBaseSettings dbSettings = ServicesFactory.getFactory().createPropertiesServices().getDatabaseSettings();
            InputStream is = ResourceUtil.getResourceStream(SqlConfig.class, MYBATIS_CONFIG);//Resources.getResourceAsStream(MYBATIS_CONFIG);
            sqlSessionfactory = new SqlSessionFactoryBuilder().build(is, dbSettings.getProperties());
            sqlSessionManager = SqlSessionManager.newInstance(sqlSessionfactory);
        } catch (Exception e) {
            log.error("Error al cargar configuracion de base de datos", e);
            throw new RuntimeException(e);
        }
    }

}
