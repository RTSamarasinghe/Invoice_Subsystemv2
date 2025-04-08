package unl.soc.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.vgb.Company;
import com.vgb.Person;
import com.vgb.Address;
import com.vgb.Item;
import com.vgb.Invoice;
import com.vgb.Equipment;
import com.vgb.Material;
import com.vgb.Contract;
import com.vgb.InvoiceItem;
/**
 * DataLoader class for loading entity data from the database
 * Handles Person, Address, Company, Item, Invoice and InvoiceItem entities
 */
public class DataLoader {
    
    private static final Logger logger = LogManager.getLogger(DataLoader.class);
    
    
    /**
     * Loads all persons from the database
     * 
     * @return List of Person objects
     */
    public static List<Person> loadPersons() {
        List<Person> persons = new ArrayList<>();
        Connection conn = null;
        
        try {
            logger.info("Loading persons from database");
            conn = ConnectionFactory.getConnection();
            
            String query = """
            		SELECT uuid, firstName, lastName, phoneNumber, address FROM Person
            		JOIN Email on Email.personId = Person.personId;
            		""";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                UUID uuid = UUID.fromString(rs.getString("uuid"));
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String phone = rs.getString("phoneNumber");
                
                // Parse emails from comma-separated string
                String emailsStr = rs.getString("address");
                List<String> emails = emailsStr != null && !emailsStr.isEmpty() ? 
                        Arrays.asList(emailsStr.split(",")) : new ArrayList<>();
                
                Person person = new Person(uuid, firstName, lastName, phone, emails);
                persons.add(person);
            }
            
            rs.close();
            ps.close();
            
            logger.info("Successfully loaded {} persons from database", persons.size());
            
        } catch (SQLException e) {
            logger.error("Error loading persons from database", e);
            throw new RuntimeException("Failed to load persons from database", e);
        } finally {
            ConnectionFactory.closeConnection(conn);
        }
        
        return persons;
    }
    
}