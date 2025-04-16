package com.vgb.database;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.HashMap;
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
import com.vgb.InvoiceItem;

/**
 * DataLoader class for loading entity data from the database
 * Handles Person, Address, Company, Item, Invoice and InvoiceItem entities
 */
public class DataLoader {
    
	
    private static final Logger LOGGER = LogManager.getLogger();
   
    
    /**
     * 
     * Loads data from Database 
     * 
     * @param <T> Generic that should be corresponding to the mapper base class </br>
     * Example : UUID, Company
     * @param query The SQL query that will load the required the tables from the DB
     * @param mapper The factory class required to create the relevant POJOs
     * @return Required Map to be used in ReportUtils
     */
    public <T> Map<UUID,T> loadData(String query, DataMapper<T> mapper) {
    	Connection conn = null;
    	try {
    	    conn = ConnectionFactory.getConnection();
    	    }catch (SQLException e) {
    	    	LOGGER.info("Bad Connection");
    	    }
    	
        Map<UUID, T> results = new HashMap<>();
        try {
        	ResultSet rs = DataFactory.runQuery(query);
        	
        	//Debug
        	ResultSetMetaData meta = rs.getMetaData();
        	int columnCount = meta.getColumnCount();
        	System.out.println("Columns returned:");
        	System.out.print(meta.getTableName(columnCount));
        	for (int i = 1; i <= columnCount; i++) {
        	    System.out.println(meta.getColumnName(i));
        	}
        	
        	
            while (rs.next()) {
            	
            	UUID uuid = UUID.fromString(rs.getString("uuid"));
                results.put(uuid, mapper.map(rs));
            }
            
            rs.close();
        } catch (SQLException e) {
            LOGGER.error("Error loading data", e);
        }
        
    	try {
			if(!conn.isClosed()) {
			ConnectionFactory.closeConnection(conn);
			}
		} catch (SQLException e) {
			LOGGER.error("Closing connection no no :(");
			e.printStackTrace();
		}
        
        return results;
    }
  
    /**
     * Loads a single person record from the database
     * @param personId
     * @return Single person object
     */
    protected static Person loadPersonById(int personId) {
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
                
                ConnectionFactory.closeConnection(conn);
                
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
    
   
    

    
    protected static Address loadAddressById(int addressId) {
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
    		ConnectionFactory.closeConnection(conn);
    		ps.close();
    		rs.close();
    		
    	}catch (SQLException e) {
            LOGGER.error("Error loading AN ADDRESS from database", e);
    	
    	}
    	return address;
    }
    
    
    protected static Company loadCompanyById(int companyId) {
    	Person contact = null;
    	Address address = null;
    	Connection conn = null;
    	Company company = null;
    	
    	LoadPerson p = new LoadPerson();
    	IDLoader<Person> servPerson = new IDLoader<>(p);
    	
    	
    	try {
    		conn = ConnectionFactory.getConnection();
    		String query = """
    				SELECT uuid, companyName, personId, addressId
    				FROM Company
    				WHERE companyId = ?
    				""";
    		  PreparedStatement ps = conn.prepareStatement(query);
              ps.setInt(1, companyId);
              ResultSet rs = ps.executeQuery();
              if(rs.next()) {
              UUID uuid = UUID.fromString(rs.getString("uuid"));
              String name = rs.getString("companyName");
              int personId = rs.getInt("personId");
              
              
              contact = servPerson.loadById("""
              		SELECT p.uuid, p.firstName, p.lastName, p.phoneNumber, e.address
            		FROM Person p JOIN Email e on e.personId = p.personId
            		WHERE p.personId = ?
              		""", personId);
              
              
              address = loadAddressById(rs.getInt("addressId"));
              company = new Company(uuid, name, contact, address);
              } else {
                  LOGGER.error("No company found with companyId = " + companyId);
              }
              
              ConnectionFactory.closeConnection(conn);
              
              ps.close();
              rs.close();
              
    	}catch(SQLException e) {
    		LOGGER.info("COMPANY NOT LOADING BY ID");
    	}
            return company;
            
    }
    
    public Item loadItemById(int itemId) {
    	Item item = null;
    	Connection conn = null;
    	
    	char itemType;
    	try {
    		conn = ConnectionFactory.getConnection();
    		
    		String query = """
    				SELECT uuid, itemName, itemPrice,
    				itemType, model
    				FROM Item
    				WHERE itemId = ?;
    				""";
    		
    		PreparedStatement ps = conn.prepareStatement(query);
    		ps.setInt(1, itemId);
    		ResultSet rs = ps.executeQuery();

    		
    	}catch(SQLException e) {
    		LOGGER.info("ITEM BY ID LOADER FAIL");
    	}
    	
    	return item;
    }
    
    public Invoice loadInvoiceById(int invoiceId) {
    	Invoice inv = null;
    	
    	return inv;
    }
    
    public InvoiceItem loadInvoiceItemById(int invoiceItemId) {
    	InvoiceItem it = null;
    	
    	return it;
    }
    
}