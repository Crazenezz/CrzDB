/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.crz.db.config.abs.impl;

import org.crz.db.config.abs.DBConfig;

/**
 * Class to configure PostgreSQL default configuration.
 * 
 * @author crazenezz
 */
public class PostgreSQLConfig extends DBConfig {
    
    private static PostgreSQLConfig con;
    
    /**
     * Set url for PostgreSQL, default will be "jdbc:postgresql://localhost:5432/database_name".
     */
    private void setUrl() {
        StringBuilder builder = new StringBuilder("jdbc:")
                .append(getType()).append("://")
                .append(getAddress()).append(":")
                .append(getPort()).append("/")
                .append(getName());
        setUrl(builder.toString());
    }
    
    /**
     * Singleton constructor of PostgreSQLConfig.
     * 
     * @param name String of database name.
     */
    private PostgreSQLConfig(String name) {
        super(name);
        setType("postgresql");
        setAddress("localhost");
        setPort("5432");
        setUsername("root");
        setPassword("root");
        setUrl();
    }
    
    /**
     * Calling instance of MySQLConfig.
     * 
     * @param name String of database name.
     * @return Instance of MySQLConfig.
     */
    public static PostgreSQLConfig getInstance(String name) {
        if(con == null)
            con = new PostgreSQLConfig(name);
        
        return con;
    }
}
