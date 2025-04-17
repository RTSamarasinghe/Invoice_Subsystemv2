package com.vgb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

import com.vgb.database.DataLoader;
import com.vgb.database.DataMapper;
import com.vgb.database.IDLoader;

public class LoadItem implements DataMapper<Item> {
	
	DataLoader dl = new DataLoader();
	
	
	IDLoader<Company> cLoader = new IDLoader<>(new LoadCompany());
	
	@Override
	public Item map(ResultSet rs, Connection conn) throws SQLException {
		
		UUID uuid = UUID.fromString(rs.getString("uuid"));
		String name = rs.getString("itemName");
		double price = rs.getDouble("itemPrice");
		String itemType = rs.getString("itemType");
		Item item = null;
		
		switch(itemType) {
		case "E": {
			String modelName = rs.getString("model");
			item = new Equipment(uuid, name, modelName, price);
			break;
		}
		
		case "M": {
			String unit = rs.getString("unit");
			double unitPrice = rs.getDouble("unitPrice");
			item = new Material(uuid, name, unit, unitPrice);
			break;
		}
			
		case "C": {
			
			int customerId = rs.getInt("customerId");
	        Company customer = null;

	        if (!rs.wasNull()) { 
	            customer = cLoader.loadById("""
	            		SELECT c.uuid, c.companyName, c.personId, c.addressId
	            		FROM Company c
	            		WHERE companyId = ? 
	            		""",customerId, conn);
	        }

	        item = new Contract(uuid, name, price, customer);
	        break;
		}
		}
			
		return item;
	}

}
