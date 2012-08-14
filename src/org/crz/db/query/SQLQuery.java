/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.crz.db.query;

import com.mysql.jdbc.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Abstract class that has standard functionality of CRUD
 *  (Create, Read, Update and Delete).
 * 
 * @author crazenezz
 */
public class SQLQuery {
    
    private Map<String, Object> data;
    private List<String> columns;
    private StringBuilder builder;
    private Statement state;
    private ResultSet rSet;
    private int columnCount;
    private String table;
    
    /**
     * SQLQuery constructor.
     * 
     * @param state Statement from Config object.
     * @param table String of table name.
     */
    public SQLQuery(Statement state, String table) {
        data = new HashMap<>();
        columns = new ArrayList<>();
        this.state = state;
        this.table = table;
        generateColumns();
    }
    
    /**
     * Generate columns from table given in constructor or
     *  from changeTable method.
     */
    private void generateColumns() {
        builder = new StringBuilder("SELECT * FROM ")
                .append(table);
        try {
            rSet = state.executeQuery(builder.toString());
            columnCount = rSet.getMetaData().getColumnCount();
            
            for(int i = 1; i <= columnCount; i++) {
                columns.add(rSet.getMetaData().getColumnName(i));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SQLQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Collection of string of column name that will be used for 
     *  get all data query.
     * 
     * @return Collection of string of table name.
     */
    private String columnBuilder() {
        StringBuilder colsBuilder = new StringBuilder();
        
        for(int i = 0; i < columns.size(); i++) {
            colsBuilder.append(columns.get(i));
            
            if(i < columns.size() - 1)
                colsBuilder.append(", ");
            else
                colsBuilder.append(" ");
        }
        
        return colsBuilder.toString();
    }
    
    /**
     * Collection of string of primary keys and values
     *  that will be used for condition query.
     * 
     * @param pks Collection of string of column name of primary keys.
     * @param vals Collection of object of primary keys values.
     * @return Collection of string of primary keys and values,
     *          separated by "AND".
     */
    private String pkBuilder(String[] pks, Object[] vals) {
        StringBuilder pksBuilder = new StringBuilder();
        
        for(int i = 0; i < pks.length; i++) {
            pksBuilder.append(pks[i])
                    .append(" = \'")
                    .append(vals[i])
                    .append("\'");
            
            if(i < pks.length - 1)
                pksBuilder.append(" AND ");
            else
                pksBuilder.append(" ");
        }
        
        return pksBuilder.toString();
    }
    
    /**
     * Change table name and generate the columns.
     * 
     * @param table String of table name.
     */
    public void changeTable(String table) {
        this.table = table;
        generateColumns();
    }
    
    /**
     * Get all data from specific table name.
     * 
     * @return Collection of data in Map&lt;column, value&gt; object.
     */
    public Map getAll() {
        builder = new StringBuilder("SELECT ")
                .append(columnBuilder())
                .append(" FROM ")
                .append(table);
        
        retrieveData();
        
        return data;
    }
    
    /**
     * Get all data from specific table name and primary key.
     * 
     * @param pk String of primary key column.
     * @param val Object of primary key value.
     * @return Collection of data in Map&lt;column, value&gt; object.
     */
    public Map getAllByPK(String pk, Object val) {
        return getAllByPKs(new String[] {pk}, new Object[] {val});
    }
    
    /**
     * Get all data from specific table name and primary keys.
     * 
     * @param pks Array of string of primary key columns.
     * @param vals Array of object of primary key values.
     * @return Collection of data in Map&lt;column, value&gt; object.
     */
    public Map getAllByPKs(String[] pks, Object[] vals) {
        builder = new StringBuilder("SELECT ")
                .append(columnBuilder())
                .append(" FROM ")
                .append(table)
                .append(" WHERE ")
                .append(pkBuilder(pks, vals));
        
        retrieveData();
        
        return data;
    }
    
    /**
     * Execute query of get all data query
     *  and close the result set after that.
     */
    private void retrieveData() {
        try {
            rSet = state.executeQuery(builder.toString());
            
            while(rSet.next()) {
                for(String column : columns) {
                    data.put(column, rSet.getObject(column));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLQuery.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rSet.close();
            } catch (SQLException ex) {
                Logger.getLogger(SQLQuery.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Insert data to table.
     * 
     * @param data Map of data, key for the column name and value as the column value.
     * @return 1 if data success to insert, 0 if failed.
     */
    public int insert(Map<String, Object> data) {
        builder = new StringBuilder("INSERT INTO ")
                .append(table)
                .append("(")
                .append(valueBuilder(data.keySet().toArray(), false))
                .append(")")
                .append(" VALUES(")
                .append(valueBuilder(data.values().toArray(), true))
                .append(")");
        
        int success = 0;
        try {
            success = state.executeUpdate(builder.toString());
        } catch (SQLException ex) {
            Logger.getLogger(SQLQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return success;
    }
    
    /**
     * Builder string of column name or values on insert query.
     * 
     * @param values Array of column name or values.
     * @param isVal Indicate value or column, True as value and False as column.
     * @return String of column name or values, separated by comma.
     */
    private String valueBuilder(Object[] values, boolean isVal) {
        StringBuilder valBuilder = new StringBuilder();
        
        for(int i = 0; i < values.length; i++) {
            if(isVal)
                valBuilder.append("\'")
                        .append(values[i])
                        .append("\'");
            else
                valBuilder.append(values[i]);
            
            if(i < values.length - 1)
                valBuilder.append(", ");
        }
        
        return valBuilder.toString();
    }
    
    /**
     * Update data to table.
     * 
     * @param data Map of updated columns and values.
     * @param key Map of primary keys.
     * @return 1 if data success to update, 0 if failed.
     */
    public int update(Map<String, Object> data, Map<String, Object> key) {
        builder = new StringBuilder("UPDATE ")
                .append(table)
                .append(" SET ")
                .append(setBuilder(data, false));
        
        if(!key.isEmpty()) {
            builder.append(" WHERE ")
                    .append(setBuilder(key, true));
        }
        
        int success = 0;
        try {
            success = state.executeUpdate(builder.toString());
        } catch (SQLException ex) {
            Logger.getLogger(SQLQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return success;
    }
    
    /**
     * Builder string of columns and values 
     *  for update and delete query.
     * 
     * @param data Map of columns and values.
     * @param isKey Indicate condition if true and set value if false.
     * @return String of columns and values separated by comma for
     *          set update value, by "AND" for condition.
     */
    private String setBuilder(Map<String, Object> data, boolean isKey) {
        StringBuilder setBuilder = new StringBuilder();
        String[] cols = data.keySet().toArray(new String[data.keySet().size()]);
        Object[] vals = data.values().toArray();
        
        for(int i = 0; i < data.size(); i++) {
            setBuilder.append(cols[i])
                    .append(" = \'")
                    .append(vals[i])
                    .append("\'");
            
            if(i < data.size() - 1) {
                if(isKey)
                    setBuilder.append(" AND ");
                else
                    setBuilder.append(", ");
            }
            
        }
        
        return setBuilder.toString();
    }
    
    /**
     * Delete selected record based on primary keys.
     * 
     * @param key Map of primary key columns and values.
     * @return 1 if data success to delete, 0 if failed.
     */
    public int delete(Map<String, Object> key) {
        builder = new StringBuilder("DELETE FROM ")
                .append(table)
                .append(" WHERE ")
                .append(setBuilder(key, true));
        
        int success = 0;
        try {
            success = state.executeUpdate(builder.toString());
        } catch (SQLException ex) {
            Logger.getLogger(SQLQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return success;
    }
}
