package com.vgb.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class DataFactory  {
	
	private static final Logger LOGGER = LogManager.getLogger();
	
	
	protected static ResultSet runQuery(String query){
		
		Connection conn = null;
		ResultSet rs = null;
		try {
			LOGGER.info("Loading data from Database");
			conn = ConnectionFactory.getConnection();
			
			PreparedStatement ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			
		}catch (SQLException e) {
            LOGGER.error("Error loading table from database", e);
            throw new RuntimeException("Failed to load table from database", e);
        }
		
	
		return rs;
		
	}

}
