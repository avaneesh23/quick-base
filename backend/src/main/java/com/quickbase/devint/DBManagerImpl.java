package com.quickbase.devint;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This DBManager implementation provides a connection to the database containing population data.
 *
 * Created by ckeswani on 9/16/15.
 */
public class DBManagerImpl implements DBManager {
	
	private static final String JDBC_DRIVER = "org.sqlite.JDBC";
	private static final String DATABASE_URL = "jdbc:sqlite:resources/data/citystatecountry.db";
	
	
    /*
     * @return Connection object
     */
    public Connection getConnection() {
        Connection conn = null;
        System.out.println("Starting.");
        System.out.print("Getting DB Connection...");
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DATABASE_URL);
            if (conn != null) {
            	System.out.println("Opened database successfully");
                return conn;
            }
        } catch (ClassNotFoundException cnf) {
            System.err.println("could not load driver" + cnf.getMessage());
        } catch (SQLException sqle) {
            System.err.println("sql exception:" + sqle.getStackTrace());
        } 
        return conn;
    }
    
    /**
     * to query the db for population data by country
     * 
     * @param country
     * @return total_population by country
     */
    public Integer getPopulationBy(String country) {
    	
    	int total = 0;
    	Connection conn = this.getConnection();
    	Statement stmt = null;
    	
    	String sql = "SELECT SUM(population) FROM city WHERE stateid IN "
    			+ "(SELECT stateid FROM state WHERE countryid = "
    			+ "(select countryid from country where countryname = '"+country+"'))";
    	
    	try {
    		stmt = conn.createStatement();
    		ResultSet rs = stmt.executeQuery(sql);
    		if (rs != null) {
    			total = rs.next() ? rs.getInt(1) : 0;
    		}
    	} catch (SQLException sqle) {
    		System.err.println("SQLException : " + sqle.getMessage());
    	} finally {
        	try {
        		if (conn != null)
        			conn.close();
			} catch (SQLException sqle) {
				System.err.println("sql exception while closing conn :" 
			                                    + sqle.getMessage());
			}
        }
    	
		return total;
    	
    }
}
