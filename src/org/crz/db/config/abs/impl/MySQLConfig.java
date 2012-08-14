/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.crz.db.config.abs.impl;

import org.crz.db.config.abs.DBConfig;

/**
 * Class to configure MySQL default configuration.
 * 
 * @author crazenezz
 */
public class MySQLConfig extends DBConfig {

    private static MySQLConfig con;
    
    /**
     * Set url for MySQL, default will be "jdbc:mysql://localhost:3306/database_name".
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
     * Singleton constructor of MySQLConfig.
     * 
     * @param name String of database name.
     */
    private MySQLConfig(String name) {
        super(name);
        setType("mysql");
        setAddress("localhost");
        setPort("3306");
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
    public static MySQLConfig getInstance(String name) {
        if(con == null)
            con = new MySQLConfig(name);
        
        return con;
    }
}
