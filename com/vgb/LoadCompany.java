package com.vgb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import com.vgb.database.DataLoader;
import com.vgb.database.DataMapper;
import com.vgb.database.IDLoader;

/**
 * Utility class to create Company POJO from database 
 */
public class LoadCompany implements DataMapper<Company> {

	@Override
	public Company map(ResultSet rs, Connection conn) throws SQLException {
		
		UUID companyUuid = UUID.fromString(rs.getString("uuid"));
		String companyName = rs.getString("companyName");
		
		Integer personId = rs.getInt("personId");
		Person contact = IDLoader.loadPersonById(personId, conn);
		
		
		Integer addressId = rs.getInt("addressId");
		Address address = IDLoader.loadAddressById(addressId, conn);
		
		return new Company(companyUuid, companyName, contact, address);
	}

}
