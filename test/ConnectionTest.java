/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.HashMap;
import java.util.Map;
import org.crz.db.config.Config;
import org.crz.db.config.abs.impl.MySQLConfig;
import org.crz.db.query.SQLQuery;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * 
 * @author crazenezz
 */
public class ConnectionTest {

    /**
     * Test connection to MySQL instance with database named "tryout"
     */
    @Test
    public void connection() {
        Config config = new Config(MySQLConfig.getInstance("tryout"));
        
        assertNotNull(config.getState());
        
        config.closeConnection();
    }

    /**
     * Test selected data from database named "tryout" in table "user".
     */
    @Test
    public void getUser() {
        Config config = new Config(MySQLConfig.getInstance("tryout"));
        SQLQuery query = new SQLQuery(config.getState(), "user");
        Map<String, Object> data;
        
        data = query.getAll();
        
        assertEquals("crazenezz", data.get("user_name"));
        assertEquals("craze", data.get("first_name"));
        
        config.closeConnection();
    }
    
    /**
     * Test selected data from database named "tryout" in table "user"
     *  with specific "user_name" as primary key.
     */
    @Test
    public void getUserByPK() {
        Config config = new Config(MySQLConfig.getInstance("tryout"));
        SQLQuery query = new SQLQuery(config.getState(), "user");
        Map<String, Object> data;
        
        data = query.getAllByPK("user_name", "crazenezz");
        
        assertEquals("crazenezz", data.get("user_name"));
        assertEquals("craze", data.get("first_name"));
        
        config.closeConnection();
    }
    
    /**
     * Test selected data from database named "tryout" in table "user"
     *  with specific "user_name" and "first_name" as primary keys.
     */
    @Test
    public void getUserByPKs() {
        Config config = new Config(MySQLConfig.getInstance("tryout"));
        SQLQuery query = new SQLQuery(config.getState(), "user");
        Map<String, Object> data;
        
        data = query.getAllByPKs(new String[]{"user_name", "first_name"}, new Object[]{"crazenezz", "craze"});
        
        assertEquals("crazenezz", data.get("user_name"));
        assertEquals("craze", data.get("first_name"));
        
        config.closeConnection();
    }
    
    /**
     * Test insert data to database "tryout" in table "user".
     */
    @Test
    public void testInsert() {
        Config config = new Config(MySQLConfig.getInstance("tryout"));
        SQLQuery query = new SQLQuery(config.getState(), "user");
        Map<String, Object> data = new HashMap<>();
        
        data.put("user_name", "user1");
        data.put("first_name", "test user1");
        
        int success = query.insert(data);
        
        assertEquals(1, success);
    }
    
    /**
     * Test update data to database "tryout" in table "user".
     */
    @Test
    public void testUpdate() {
        Config config = new Config(MySQLConfig.getInstance("tryout"));
        SQLQuery query = new SQLQuery(config.getState(), "user");
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> key = new HashMap<>();
        
        data.put("address", "jalan besar");
        key.put("user_name", "user1");
        
        int success = query.update(data, key);
        
        assertEquals(1, success);
    }
    
    /**
     * Test delete data to database "tryout" in table "user".
     */
    @Test
    public void testDelete() {
        Config config = new Config(MySQLConfig.getInstance("tryout"));
        SQLQuery query = new SQLQuery(config.getState(), "user");
        Map<String, Object> key = new HashMap<>();
        
        key.put("user_name", "user1");
        
        int success = query.delete(key);
        
        assertEquals(1, success);
    }
}
