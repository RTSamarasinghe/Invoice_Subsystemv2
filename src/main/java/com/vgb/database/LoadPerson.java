package com.vgb.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.vgb.Person;

/**
 * Utility class for object instantiation of a Person from a ResultSet
 */
public class LoadPerson implements DataMapper<Person>{

	
	/**
	 * 
	 * Creates a single <code>Person</code> object using DataMappers generic method
	 * Loose coupled implementation to dynamically load tables from the database 
	 * 
	 * @param The Result set after executing a query using DataFactory
	 */
	@Override
	public Person map(ResultSet rs) throws SQLException {
		
		UUID uuid = UUID.fromString(rs.getString("uuid"));
        String firstName = rs.getString("firstName");
        String lastName = rs.getString("lastName");
        String phone = rs.getString("phoneNumber");
        
        String emailsStr = rs.getString("address");
        List<String> emails = emailsStr != null && !emailsStr.isEmpty() ? 
                Arrays.asList(emailsStr.split(",")) : new ArrayList<>();
		
		return new Person(uuid,firstName, lastName, phone, emails);
	}

}
