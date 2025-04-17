package com.vgb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.vgb.database.DataMapper;

public class LoadAddress implements DataMapper<Address> {
	
	
	@Override
	public Address map(ResultSet rs, Connection conn) throws SQLException {
		
		String street = rs.getString("street");
		String city = rs.getString("city");
		String state = rs.getString("stateName");
		String zip = rs.getString("zip");
		
		return new Address(street, city, state, zip);
	}
	
	

}
