package unl.soc.database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
import com.vgb.DataFactory;
import com.vgb.InvoiceItem;
/**
 * DataLoader class for loading entity data from the database
 * Handles Person, Address, Company, Item, Invoice and InvoiceItem entities
 */
public class DataLoader {
    
    private static final Logger LOGGER = LogManager.getLogger();
    
    /**
     * Loads a single person from the database
     * @param personId
     * @return
     */
    public static Person loadPersonById(int personId) {
        Person person = null;
        Connection conn = null;
        
        try {
            conn = ConnectionFactory.getConnection();            
            // First, get the album basic information
            String query = """
            		SELECT p.uuid, p.firstName, p.lastName, p.phoneNumber, e.address
            		FROM Person p JOIN Email e on e.personId = p.personId
            		WHERE p.personId = ?
            		""";
            		
            
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, personId);
            ResultSet rs = ps.executeQuery();
            UUID uuid = null;
            List<String> emails = new ArrayList<>();
            if (rs.next()) {
            	
            	try {
            	    String uuidStr = rs.getString("uuid");
            	    if (uuidStr != null && !uuidStr.isEmpty()) {
            	        uuid = UUID.fromString(uuidStr);
            	        
            	    } else {
            	        LOGGER.info("UUID string is null or empty");
            	    }
            	} catch (IllegalArgumentException e) {
            	    LOGGER.info("Invalid UUID format: {}", e.getMessage());
            	}
            	
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String phoneNumber = rs.getString("phoneNumber");
                
                rs.close();
                ps.close();
                
                query = """
                		SELECT address FROM Email 
                		WHERE Email.personId = ?
                		""";
                
                ps = conn.prepareStatement(query);
                ps.setInt(1, personId);
                rs = ps.executeQuery();
                
                while(rs.next()) {
                	String email = rs.getString("address");
                	emails.add(email);
                }
                
                // Create the album object with all basic information
                person = new Person(uuid, firstName, lastName, phoneNumber, emails);
                
                rs.close();
                ps.close();
                
            }
            
            // If rs.next() didn't return true, album will remain null
            
        } catch (SQLException e) {
            LOGGER.info("SQLException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
            	LOGGER.info("SQLException");
                throw new RuntimeException(e);
            }
        }
        
        return person;
    }
    
    /**
     * Loads all persons from the database
     * 
     * @return List of Person objects
     */
    public static List<Person> loadPersons() {
        List<Person> persons = new ArrayList<>();
        Connection conn = null;
        
        try {
            LOGGER.info("Loading persons from database");
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
            
            LOGGER.info("Successfully loaded {} persons from database", persons.size());
            
        } catch (SQLException e) {
            LOGGER.error("Error loading persons from database", e);
            throw new RuntimeException("Failed to load persons from database", e);
        } finally {
            ConnectionFactory.closeConnection(conn);
        }
        
        return persons;
    }
    
    /**
     * Loads all addresses from the database
     *
     * @return List of Address objects
     */
    /**
     * Loads all addresses from the database
     *
     * @return List of Address objects
     */
    public static List<Address> loadAddresses() {
        List<Address> addresses = new ArrayList<>();
        Connection conn = null;

        try {
            LOGGER.info("Loading addresses from database");
            conn = ConnectionFactory.getConnection();

            String query = """
                SELECT a.street, a.city, s.stateName, z.zip
                FROM Address a
                JOIN State s ON a.stateId = s.stateId
                JOIN ZipCode z ON a.zipId = z.zipId
                """;
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String street = rs.getString("street");
                String city = rs.getString("city");
                String state = rs.getString("stateName");
                String zip = rs.getString("zip");

                Address address = new Address(street, city, state, zip);
                addresses.add(address);
            }

            rs.close();
            ps.close();

            LOGGER.info("Successfully loaded {} addresses from database", addresses.size());

        } catch (SQLException e) {
            LOGGER.error("Error loading addresses from database", e);
            throw new RuntimeException("Failed to load addresses from database", e);
        } finally {
            ConnectionFactory.closeConnection(conn);
        }

        return addresses;
    }
    
    public static Address loadAddressById(int addressId) {
    	Address address = null;
    	Connection conn = null;
    	
    	try {
    		LOGGER.info("Loading addres from DB");
    		conn = ConnectionFactory.getConnection();
    		
    		String query = """
    				SELECT a.addressId, a.street, a.city, s.stateName, z.zip
    				FROM Address a
    				JOIN State s ON a.stateId = s.stateId
    				JOIN ZipCode z ON a.zipId = z.zipId
    				WHERE a.addressId = ?
    				""";
    		PreparedStatement ps = conn.prepareStatement(query);
    		ps.setInt(1, addressId);
    		ResultSet rs = ps.executeQuery();
    		
    		if (rs.next()) {
    			String street = rs.getString("street");
    			String city = rs.getString("city");
    			String state = rs.getString("stateName");
    			String zip = rs.getString("zip");
    			
    			address = new Address(street, city, state, zip);
    		}
    		
    		ps.close();
    		rs.close();
    		
    	}catch (SQLException e) {
            LOGGER.error("Error loading AN ADDRESS from database", e);
    	
    	}
    	return address;
    }
    
    /**
     * Loads all companies from the database
     *
     * @return List of Company objects
     */
    public static List<Company> loadCompanies() {
        List<Company> companies = new ArrayList<>();
        Connection conn = null;

        try {
            LOGGER.info("Loading companies from database");
            conn = ConnectionFactory.getConnection();

            String query = """
                SELECT 
                    c.companyuuid, c.companyName, c.personId,
                    c.addressId
                    
                FROM Company c
               
              
                """;
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Extract company data
                UUID companyUuid = UUID.fromString(rs.getString("companyuuid"));
                String companyName = rs.getString("companyName");
                
                Integer personId = rs.getInt("personId");
                Person contact = loadPersonById(personId);
                
                Integer addressId = rs.getInt("addressId");
                Address address = loadAddressById(addressId);
   
                // Create Company object
                Company company = new Company(companyUuid, companyName, contact, address);
                companies.add(company);
            }

            rs.close();
            ps.close();

            LOGGER.info("Successfully loaded {} companies from database", companies.size());

        } catch (SQLException e) {
            LOGGER.error("Error loading companies from database", e);
            throw new RuntimeException("Failed to load companies from database", e);
        } finally {
            ConnectionFactory.closeConnection(conn);
        }

        return companies;
    }
    
    /**
     * Loads all items from the database
     *
     * @return List of Item objects (Equipment, Material, or Contract instances)
     */
    public static List<Item> loadItems() {
        List<Item> items = new ArrayList<>();
        Connection conn = null;

        try {
            LOGGER.info("Loading items from database");
            conn = ConnectionFactory.getConnection();

            String query = """
                SELECT 
                    i.uuid, i.itemName, i.itemPrice, i.itemType, 
                    i.model, i.unit,
                    c.companyuuid, c.companyName,
                    p.uuid AS personUuid, p.firstName, p.lastName, p.phoneNumber,
                    a.street, a.city, s.stateName, z.zip,
                    e.email_list
                FROM Item i
                LEFT JOIN Company c ON i.companyId = c.companyId
                LEFT JOIN Person p ON c.personId = p.personId
                LEFT JOIN Address a ON c.addressId = a.addressId
                LEFT JOIN State s ON a.stateId = s.stateId
                LEFT JOIN ZipCode z ON a.zipId = z.zipId
                LEFT JOIN (
                    SELECT personId, GROUP_CONCAT(address) AS email_list
                    FROM Email
                    GROUP BY personId
                ) e ON p.personId = e.personId
                """;
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                UUID uuid = UUID.fromString(rs.getString("uuid"));
                String name = rs.getString("itemName");
                double price = rs.getDouble("itemPrice");
                String itemType = rs.getString("itemType");
                
                // Create  item subclass based on itemType
                switch (itemType) {
                    case "E":
                        // Create Equipment object
                        String modelName = rs.getString("model");
                        Equipment equipment = new Equipment(uuid, name, modelName, price);
                        items.add(equipment);
                        break;
                        
                    case "M":
                        // Create Material object
                        String unit = rs.getString("unit");
                        Material material = new Material(uuid, name, unit, price);
                        items.add(material);
                        break;
                        
                    case "C":
                        // Create Contract object
                        // Build the associated company
                        if (rs.getString("companyuuid") != null) {
                            UUID companyUuid = UUID.fromString(rs.getString("companyuuid"));
                            String companyName = rs.getString("companyName");
                            
                            // Build contact person
                            UUID personUuid = UUID.fromString(rs.getString("personUuid"));
                            String firstName = rs.getString("firstName");
                            String lastName = rs.getString("lastName");
                            String phone = rs.getString("phoneNumber");
                            
                            // Parse emails 
                            String emailsStr = rs.getString("email_list");
                            List<String> emails = emailsStr != null && !emailsStr.isEmpty() ?
                                Arrays.asList(emailsStr.split(",")) : new ArrayList<>();
                            
                            Person contact = new Person(personUuid, firstName, lastName, phone, emails);
                            
                            // Build address
                            String street = rs.getString("street");
                            String city = rs.getString("city");
                            String state = rs.getString("stateName");
                            String zip = rs.getString("zip");
                            
                            Address address = new Address(street, city, state, zip);
                            
                            // Create the company
                            Company company = new Company(companyUuid, companyName, contact, address);
                            
                            // Create the contract
                            Contract contract = new Contract(uuid, name, price, company);
                            items.add(contract);
                        }
                        break;
                        
                    default:
                        LOGGER.warn("Unknown item type found: {}", itemType);
                }
            }

            rs.close();
            ps.close();

            LOGGER.info("Successfully loaded {} items from database", items.size());

        } catch (SQLException e) {
            LOGGER.error("Error loading items from database", e);
            throw new RuntimeException("Failed to load items from database", e);
        } finally {
            ConnectionFactory.closeConnection(conn);
        }

        return items;
    }
}