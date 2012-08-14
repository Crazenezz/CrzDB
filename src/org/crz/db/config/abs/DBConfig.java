/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.crz.db.config.abs;

/**
 * Abstract class to configure database connection.
 *
 * @author crazenezz
 */
public abstract class DBConfig {

    private String type;
    private String address;
    private String port;
    private String name;
    private String username;
    private String password;
    private String url;

    /**
     * Set complete url of database configuration.
     * 
     * @param url 
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Return complete url of database configuration.
     * 
     * @return String of complete url.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Return IP address of database configuration.
     * 
     * @return String of IP Address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Set IP address of database configuration.
     * 
     * @param address 
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Return database name.
     * 
     * @return String of database name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set database name.
     * 
     * @param name 
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set database password.
     * 
     * @param password 
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Return database port.
     * 
     * @return String of database port.
     */
    public String getPort() {
        return port;
    }

    /**
     * Set database port.
     * 
     * @param port 
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     * Return database type.
     * 
     * @return String of database type such as MySQL, PostgreSQL, Oracle, etc.
     */
    public String getType() {
        return type;
    }

    /**
     * Set database type.
     * 
     * @param type 
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Set database username.
     * 
     * @param username 
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Return database password.
     * 
     * @return String of database password.
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * Return database username.
     * 
     * @return String of database username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * DBConfig constructor.
     * 
     * @param name String of database name.
     */
    public DBConfig(String name) {
        this.name = name;
    }
}
