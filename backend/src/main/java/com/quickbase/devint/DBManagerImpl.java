package com.quickbase.devint;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

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
    
    
	@Override
	public List<Pair<String, Integer>> GetCountryPopulations() {
		List<Pair<String, Integer>> list = null;
		Connection conn = this.getConnection();
		Statement stmt = null;
		String sql = "SELECT country.countryname, SUM(city.population) FROM city, state, country "
				+ "WHERE city.stateid = state.stateid and state.countryid = country.countryid "
				+ "GROUP BY country.countryid";
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (null == rs) {
				throw new NullPointerException("Result Set rs returned null");
			} else {
				System.out.println("Opened database successfully");
				list = new ArrayList<Pair<String, Integer>>();
				while (rs.next()) {
					list.add(new ImmutablePair<String, Integer>(rs.getString(1), rs.getInt(2)));
				}
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
		
		
		
		return list;
	}
    
    /**
     * to query the DB for population data by country
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
    		if (null == rs) {
    			throw new NullPointerException("Result Set rs returned null");
    		} else {
    			System.out.println("Opened database successfully");
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
