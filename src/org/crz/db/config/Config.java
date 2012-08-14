/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.crz.db.config;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.crz.db.config.abs.DBConfig;

/**
 * Class configuration to initiate database connection
 *  based on database instance.
 * 
 * @author crazenezz
 */
public class Config {

    private Connection conn;
    private Statement state;
    
    /**
     * Return statement.
     * 
     * @return Object of Statement.
     */
    public Statement getState() {
        return state;
    }
    
    /**
     * Config constructor.
     * 
     * @param dbConfig 
     */
    public Config(DBConfig dbConfig) {
        try {
            conn = (Connection) DriverManager.getConnection(
                    dbConfig.getUrl(), 
                    dbConfig.getUsername(), 
                    dbConfig.getPassword());
            
            state = (Statement) conn.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Close the connection of current database.
     */
    public void closeConnection() {
        try {
            state.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
