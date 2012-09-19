/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.crz.db.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.crz.db.config.abs.DBConfig;
import org.crz.db.config.abs.impl.MySQLConfig;
import org.crz.db.config.abs.impl.OracleConfig;
import org.crz.db.config.abs.impl.PostgreSQLConfig;

/**
 * Class configuration to initiate database connection
 *  based on database instance.
 * 
 * @author crazenezz
 */
public class Config {

    private Connection conn;
    private Statement state;
    private static DBConfig dbConfig;
    private static Config config;
    
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
     * @param db String of database server name such as: "mysql, postgresql, oracle"
     */
    private Config(String db, String dbName) throws SQLException {
        setConfig(db, dbName);
        
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
    
    public static Config getInstance(String db, String dbName) throws SQLException {
        if(config == null)
            config = new Config(db, dbName);
        
        return config;
    }
    
    private static void setConfig(String db, String dbName) throws SQLException {
        if(dbConfig == null)
            switch(db) {
                case "mysql":
                    dbConfig = MySQLConfig.getInstance(dbName);
                    break;
                case "postgresl":
                    dbConfig = PostgreSQLConfig.getInstance(dbName);
                    break;
                case "oracle":
                    dbConfig = OracleConfig.getInstance(dbName);
                    break;
                default: 
                    throw new SQLException("There is no instance for " + db);
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
