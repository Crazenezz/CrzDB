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
                .append(getDB());
        setUrl(builder.toString());
    }
    
    /**
     * Singleton constructor of MySQLConfig.
     * 
     * @param db String of database name.
     */
    private MySQLConfig(String db) {
        super(db);
        setType("mysql");
        setAddress("localhost");
        setPort("3306");
        setUsername("root");
        setPassword("root");
        setUrl();
    }
    
    public void setMySQLConfig(String address, String port) {
        setMySQLConfig(null, address, port, null, null);
    }
    
    public void setMySQLConfig(String address, String port, String username, String password) {
        setMySQLConfig(null, address, port, username, password);
    }
    
    public void setMySQLConfig(String db, String address, String port) {
        setMySQLConfig(db, address, port, null, null);
    }
    
    public void setMySQLConfig(String db, String address, String port, String username, String password) {
        if(db != null)
            setDB(db);
        
        setAddress(address);
        setPort(port);
        
        if(username != null)
            setUsername(username);
        
        if(password != null)
            setPassword(password);
    }
    
    /**
     * Calling instance of MySQLConfig.
     *  For default username = "root", password = "root"
     * 
     * @param db String of database name.
     * @return Instance of MySQLConfig.
     */
    public static MySQLConfig getInstance(String db) {
        if(con == null)
            con = new MySQLConfig(db);
        
        return con;
    }
    
}
