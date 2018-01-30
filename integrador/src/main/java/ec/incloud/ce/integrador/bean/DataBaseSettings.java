/*
 * Copyright (c) Incloud Services S.A.C. All rights reserved.
 * http://www.csticorp.biz
 * 
 */
package ec.incloud.ce.integrador.bean;

import java.util.Properties;
import org.apache.commons.configuration.ConfigurationConverter;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Incloud Services S.A.C.
 */
public class DataBaseSettings {

    private static final String DATABASE_URL = "database.url";
    private static final String DATABASE_DRIVER = "database.driver";
    private static final String DATABASE_USERNAME = "database.username";
    private static final String DATABASE_PASSWORD = "database.password";
    private static final String DATABASE_MAX_CONNECTIONS = "database.max-connections";
    private static final String DATABASE_TEST_QUERY = "database.test-query";
    private static final String DATABASE_TEST_IDLE_TIME = "database.test-idle-time";

    private String databaseUrl;
    private String databaseDriver;
    private String databaseUsername;
    private String databasePassword;
    private Integer databaseMaxConnections;
    private String databasePool;
    private Integer databaseTestIdleTime;

    public DataBaseSettings() {
    }

    public DataBaseSettings(Properties properties) {
        setProperties(properties);
    }

    public void setProperties(Properties properties) {
        setDatabaseUrl(properties.getProperty(DATABASE_URL));
        setDatabaseDriver(properties.getProperty(DATABASE_DRIVER));
        setDatabaseUsername(properties.getProperty(DATABASE_USERNAME));
        setDatabasePassword(properties.getProperty(DATABASE_PASSWORD));
        setDatabaseMaxConnections(Integer.parseInt(properties.getProperty(DATABASE_MAX_CONNECTIONS)));
        setDatabaseTestIdleTime(Integer.parseInt(properties.getProperty(DATABASE_TEST_IDLE_TIME, "10000")));
    }

    public Properties getProperties() {
        PropertiesConfiguration configuration = new PropertiesConfiguration();

        if (getDatabaseUrl() != null) {
            configuration.setProperty(DATABASE_URL, getDatabaseUrl());
        }

        if (getDatabaseDriver() != null) {
            configuration.setProperty(DATABASE_DRIVER, getDatabaseDriver());
        }

        if (getMappedTestQuery() != null) {
            configuration.setProperty(DATABASE_TEST_QUERY, getMappedTestQuery());
        }

        if (getDatabaseUsername() != null) {
            configuration.setProperty(DATABASE_USERNAME, getDatabaseUsername());
        } else {
            configuration.setProperty(DATABASE_USERNAME, StringUtils.EMPTY);
        }

        if (getDatabasePassword() != null) {
            configuration.setProperty(DATABASE_PASSWORD, getDatabasePassword());
        } else {
            configuration.setProperty(DATABASE_PASSWORD, StringUtils.EMPTY);
        }

        if (getDatabaseMaxConnections() != null) {
            configuration.setProperty(DATABASE_MAX_CONNECTIONS, getDatabaseMaxConnections().toString());
        } else {
            configuration.setProperty(DATABASE_MAX_CONNECTIONS, StringUtils.EMPTY);
        }

        if (getDatabaseTestIdleTime() != null) {
            configuration.setProperty(DATABASE_TEST_IDLE_TIME, getDatabaseTestIdleTime().toString());
        } else {
            configuration.setProperty(DATABASE_TEST_IDLE_TIME, "10000");
        }

        return ConfigurationConverter.getProperties(configuration);
    }

    public String getMappedTestQuery() {
        return "SELECT 1";
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public String getDatabaseDriver() {
        return databaseDriver;
    }

    public void setDatabaseDriver(String databaseDriver) {
        this.databaseDriver = databaseDriver;
    }

    public String getDatabaseUsername() {
        return databaseUsername;
    }

    public void setDatabaseUsername(String databaseUsername) {
        this.databaseUsername = databaseUsername;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public void setDatabasePassword(String databasePassword) {
        this.databasePassword = databasePassword;
    }

    public Integer getDatabaseMaxConnections() {
        return databaseMaxConnections;
    }

    public void setDatabaseMaxConnections(Integer databaseMaxConnections) {
        this.databaseMaxConnections = databaseMaxConnections;
    }

    public String getDatabasePool() {
        return databasePool;
    }

    public void setDatabasePool(String databasePool) {
        this.databasePool = databasePool;
    }

    public Integer getDatabaseTestIdleTime() {
        return databaseTestIdleTime;
    }

    public void setDatabaseTestIdleTime(Integer databaseTestIdleTime) {
        this.databaseTestIdleTime = databaseTestIdleTime;
    }
}
