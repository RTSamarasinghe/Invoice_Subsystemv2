package com.vgb.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Utility class for managing database connections.
 * Handles connection creation and safe closure with logging.
 */

public abstract class ConnectionFactory {
	
	private static final Logger logger = LogManager.getLogger(ConnectionFactory.class);
	public static final String URL = "jdbc:mysql://nuros.unl.edu/rsamarasinghe2";
    public static final String USERNAME = "rsamarasinghe2"; 
    public static final String PASSWORD = "mohmao4Coaha"; 

	
	public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            logger.info("Attempting to establish a connection to the database...");
            connection = DriverManager.getConnection(ConnectionFactory.URL, ConnectionFactory.USERNAME, ConnectionFactory.PASSWORD);
            logger.info("Connection established successfully.");
        } catch (SQLException e) {
            logger.error("Error while establishing database connection.", e);
            throw new SQLException("Error while connecting to the database", e);
        }
        return connection;
    }
    
    
     public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                logger.info("Database connection closed successfully.");
            } catch (SQLException e) {
                logger.error("Error while closing database connection.", e);
            }
        }
    }
     
}

