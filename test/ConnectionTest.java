/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.crz.db.config.Config;
import org.crz.db.query.SQLQuery;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * JUnit class to test database connection and CRUD function.
 *
 * @author crazenezz
 */
public class ConnectionTest {

    private static Config config;

    /**
     * Prepare connection to MySQL instance with database named "tryout"
     */
    @BeforeClass
    public static void openConnection() {
        try {
            config = Config.getInstance("mysql", "tryout");
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test selected data from database named "tryout" in table "user".
     */
    @Test
    public void getUsers() {
        SQLQuery query = new SQLQuery(config.getState(), "user");
        List<Map> datas;

        datas = query.getAll();

        Assert.assertEquals(6, datas.size());
    }

    /**
     * Test selected data from database named "tryout" in table "user" with
     * specific "user_name" as primary key.
     */
    @Test
    public void getUserByPK() {
        SQLQuery query = new SQLQuery(config.getState(), "user");
        List<Map> datas;
        Map<String, Object> data = null;

        datas = query.getAllByCustom("user_name", "crazenezz");

        Assert.assertEquals("crazenezz", datas.get(0).get("user_name"));
        Assert.assertEquals("craze", datas.get(0).get("first_name"));
    }

    /**
     * Test selected data from database named "tryout" in table "user" with
     * specific "user_name" and "first_name" as primary keys.
     */
    @Test
    public void getUserByPKs() {
        SQLQuery query = new SQLQuery(config.getState(), "user");
        List<Map> datas;

        datas = query.getAllByCustom(new String[]{"user_name", "first_name"}, new Object[]{"crazenezz", "craze"});

        Assert.assertEquals("crazenezz", datas.get(0).get("user_name"));
        Assert.assertEquals("craze", datas.get(0).get("first_name"));
    }

    /**
     * Test insert data to database "tryout" in table "user".
     */
    @Test
    public void testInsert() {
        SQLQuery query = new SQLQuery(config.getState(), "user");
        Map<String, Object> data = new HashMap<>();

        data.put("user_name", "user1");
        data.put("first_name", "test user1");

        int success = query.insert(data);

        Assert.assertEquals(1, success);
    }

    /**
     * Test update data to database "tryout" in table "user".
     */
    @Test
    public void testUpdate() {
        SQLQuery query = new SQLQuery(config.getState(), "user");
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> key = new HashMap<>();

        data.put("address", "jalan besar");
        key.put("user_name", "user1");

        int success = query.update(data, key);

        Assert.assertEquals(1, success);
    }

    /**
     * Test delete data to database "tryout" in table "user".
     */
    @Test
    public void testDelete() {
        SQLQuery query = new SQLQuery(config.getState(), "user");
        Map<String, Object> key = new HashMap<>();

        key.put("user_name", "user1");

        int success = query.delete(key);

        Assert.assertEquals(1, success);
    }

    /**
     * Close connection
     */
    @AfterClass
    public static void closeConnection() {
        config.closeConnection();
    }
}
