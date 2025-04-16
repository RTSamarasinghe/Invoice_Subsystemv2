package com.vgb.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.vgb.Contract;
import com.vgb.Equipment;
import com.vgb.Invoice;
import com.vgb.InvoiceItem;
import com.vgb.Item;
import com.vgb.Lease;
import com.vgb.Material;
import com.vgb.Rental;

public class LoadInvoiceItem implements DataMapper<InvoiceItem>{

	@Override
	public InvoiceItem map(ResultSet rs) throws SQLException {

	    LoadItem mapper = new LoadItem();
	    IDLoader<Item> service = new IDLoader<>(mapper);

	    UUID uuid = UUID.fromString(rs.getString("uuid"));
	    char EQType = rs.getString("typeEquipment").charAt(0);

	    Item item = service.loadById(
	        "SELECT * FROM Item WHERE itemId = ?",
	        rs.getInt("itemId")
	    );

	    LoadInvoice map = new LoadInvoice();
	    IDLoader<Invoice> invoiceLoader = new IDLoader<>(map);

	    Invoice inv = invoiceLoader.loadById(
	        "SELECT * FROM Invoice WHERE invoiceId = ?",
	        rs.getInt("invoiceId")
	    );

	    switch (EQType) {
	        case 'L':
	            LocalDate startDate = LocalDate.parse(rs.getString("startDate"));
	            LocalDate endDate = LocalDate.parse(rs.getString("endDate"));
	            return new InvoiceItem(inv, new Lease((Equipment) item, startDate, endDate));

	        case 'R':
	            double hours = rs.getDouble("numberOfHours");
	            return new InvoiceItem(inv, new Rental((Equipment) item, hours));

	        case 'P':
	            return new InvoiceItem(inv, item);

	        case 'M':
	            double quantity = rs.getDouble("quantity");
	            return new InvoiceItem(inv, new Material((Material) item, quantity));

	        case 'C':
	            double contractPrice = rs.getDouble("price");
	            return new InvoiceItem(inv, new Contract((Contract) item, contractPrice));

	        default:
	            throw new SQLException("Unknown Equipment Type: " + EQType);
	    }
	}

}
