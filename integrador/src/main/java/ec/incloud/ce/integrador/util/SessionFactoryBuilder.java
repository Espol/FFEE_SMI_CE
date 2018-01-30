/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.integrador.util;

import java.io.InputStream;
import java.util.Properties;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

/**
 *
 * @author Incloud Services S.A.C.
 */
public class SessionFactoryBuilder {

    private static SqlSessionFactory factory;
    private final static Logger log = Logger.getLogger(SessionFactoryBuilder.class);
    private static final String INTEGRADOR = "integrador.properties";
    private static final String MYBATIS_CONFIG = "/conf_integrador/mybatis-config.xml";

    private SessionFactoryBuilder() {
    }

    private SqlSessionFactory getFactory() {
        InputStream is;
        try {
            is = ResourceUtil.getResourceStream(SessionFactoryBuilder.class, INTEGRADOR);
            Properties prop = new Properties();
            prop.load(is);
            is = Resources.getResourceAsStream(MYBATIS_CONFIG);
            return new SqlSessionFactoryBuilder().build(is, prop);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return null;
    }

    public static SqlSessionFactory getSessionFactory() {
        synchronized (SessionFactoryBuilder.class) {
            if (factory == null) {
                SessionFactoryBuilder builder = new SessionFactoryBuilder();
                factory = builder.getFactory();
            }
            return factory;
        }
    }
}
