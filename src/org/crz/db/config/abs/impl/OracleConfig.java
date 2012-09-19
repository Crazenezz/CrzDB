/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.crz.db.config.abs.impl;

import org.crz.db.config.abs.DBConfig;

/**
 * Class to configure Oracle default configuration.
 * 
 * @author crazenezz
 */
public class OracleConfig extends DBConfig {
    
    private static OracleConfig con;
    
    /**
     * Set url for Oracle, default will be "jdbc:oracle:thin:@//localhost:1521/database_name".
     */
    private void setUrl() {
        StringBuilder builder = new StringBuilder("jdbc:")
                .append(getType()).append("://@")
                .append(getAddress()).append(":")
                .append(getPort()).append("/")
                .append(getDB());
        setUrl(builder.toString());
    }
    
    /**
     * Singleton constructor of OracleConfig.
     * 
     * @param name String of database name.
     */
    private OracleConfig(String name) {
        super(name);
        setType("oracle:thin");
        setAddress("localhost");
        setPort("1521");
        setUsername("root");
        setPassword("root");
        setUrl();
    }
    
    public void setOracleConfig(String address, String port) {
        setOracleConfig(null, address, port, null, null);
    }
    
    public void setOracleConfig(String address, String port, String username, String password) {
        setOracleConfig(null, address, port, username, password);
    }
    
    public void setOracleConfig(String db, String address, String port) {
        setOracleConfig(db, address, port, null, null);
    }
    
    public void setOracleConfig(String db, String address, String port, String username, String password) {
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
     * Calling instance of OracleConfig.
     * 
     * @param name String of database name.
     * @return Instance of MySQLConfig.
     */
    public static OracleConfig getInstance(String name) {
        if(con == null)
            con = new OracleConfig(name);
        
        return con;
    }
}
