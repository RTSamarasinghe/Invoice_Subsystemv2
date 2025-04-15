package com.vgb.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.vgb.Invoice;
import com.vgb.InvoiceItem;
import com.vgb.Item;

public class LoadInvoiceItem implements DataMapper<List<InvoiceItem>>{

	@Override
	public List<InvoiceItem> map(ResultSet rs) throws SQLException {
		
		LoadItem mapper = new LoadItem();
		IDLoader<Item> service = new IDLoader<>(mapper);
		
		Item item = service.loadById(
				"""
				SELECT *
				FROM Item 
				WHERE = ?
				""", 
				rs.getInt("itemId"));
		
		LoadInvoice map = new LoadInvoice();
		IDLoader<Invoice> invoice = new IDLoader<>(map);
		
		Invoice inv = invoice.loadById(
				"""
				SELECT * 
				FROM Invoice 
				WHERE invoiceId = ?
				""", 
				rs.getInt("invoiceId"));

		//Make ItemType match lease,Rental, Lease, Contract and Material
		return null;
	}

}
