package com.vgb.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import com.vgb.Address;
import com.vgb.Company;
import com.vgb.Person;

/**
 * Utility class to create Company POJO from database 
 */
public class LoadCompany implements DataMapper<Company> {

	@Override
	public Company map(ResultSet rs) throws SQLException {
		
		UUID companyUuid = UUID.fromString(rs.getString("uuid"));
		String companyName = rs.getString("companyName");
		
		Integer personId = rs.getInt("personId");
		Person contact = DataLoader.loadPersonById(personId);
		
		Integer addressId = rs.getInt("addressId");
		Address address = DataLoader.loadAddressById(addressId);
		
		return new Company(companyUuid, companyName, contact, address);
	}

}
