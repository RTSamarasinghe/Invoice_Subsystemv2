package com.vgb.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

import com.vgb.Company;
import com.vgb.Contract;
import com.vgb.Equipment;
import com.vgb.Item;
import com.vgb.Material;

public class LoadItem implements DataMapper<Item> {

	@Override
	public Item map(ResultSet rs) throws SQLException {
		
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
	            customer = DataLoader.loadCompanyById(customerId);
	        }

	        item = new Contract(uuid, name, price, customer);
	        break;
		}
		}
			
		return item;
	}

}
