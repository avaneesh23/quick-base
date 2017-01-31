package com.quickbase.devint;

import java.sql.Connection;

/**
 * Created by ckeswani on 9/16/15.
 */
public interface DBManager extends IDataAccess {
	
    public Connection getConnection();
    
}
