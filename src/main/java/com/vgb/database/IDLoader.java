package com.vgb.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



/**
 * Service Layer class to help load ONE record from the database
 * @param <T> 
 */
public class IDLoader <T> {
    private final DataMapper<T> mapper;
    private static final Logger LOGGER = LogManager.getLogger();
    

    public IDLoader(DataMapper<T> mapper) {
        this.mapper = mapper;
    }
    
    public T loadById(String query, int id) {
    	T entity = null;
    	Connection conn = null;
    	
    	try {
    		
    		conn = ConnectionFactory.getConnection();
    		PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
    		
            if(rs.next()) {
    			entity = mapper.map(rs);
    		}
    		
    	}catch(SQLException e) {
    		LOGGER.info("Something bad happening loading generic by ID :(", e);
    	}
    	
    	return entity;
    	
    }
    

}
