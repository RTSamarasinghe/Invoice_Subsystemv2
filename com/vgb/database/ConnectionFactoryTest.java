package com.vgb.database;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import com.vgb.database.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

public class ConnectionFactoryTest {

    private static final Logger logger = LogManager.getLogger(ConnectionFactoryTest.class);

    @Test
    public void testGetConnectionSuccess() {
        logger.info("Starting test: testGetConnectionSuccess");

        try (Connection connection = ConnectionFactory.getConnection()) {
            assertNotNull(connection, "Connection should not be null");
            assertFalse(connection.isClosed(), "Connection should be open");
            logger.info("Connection obtained and verified successfully.");
        } catch (SQLException e) {
            logger.error("Failed to get connection in testGetConnectionSuccess", e);
            fail("Exception thrown while getting connection: " + e.getMessage());
        }

        logger.info("Finished test: testGetConnectionSuccess");
    }

    @Test
    public void testCloseConnection() {
        logger.info("Starting test: testCloseConnection");

        try {
            Connection connection = ConnectionFactory.getConnection();
            assertNotNull(connection, "Connection should not be null");

            ConnectionFactory.closeConnection(connection);
            assertTrue(connection.isClosed(), "Connection should be closed");
            logger.info("Connection closed successfully in test.");
        } catch (SQLException e) {
            logger.error("Exception during testCloseConnection", e);
            fail("Exception thrown during connection close test: " + e.getMessage());
        }

        logger.info("Finished test: testCloseConnection");
    }
}
