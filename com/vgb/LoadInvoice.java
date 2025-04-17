package com.vgb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.vgb.database.DataMapper;
import com.vgb.database.IDLoader;

public class LoadInvoice implements DataMapper<Invoice> {

	/**
	 * 
	 * Creates a single <code>Invoice</code> object using DataMappers generic method
	 * Loose coupled implementation to dynamically load tables from the database 
	 * 
	 * @param The Result set after executing a query using DataFactory
	 */
	@Override
	public Invoice map(ResultSet rs, Connection conn) throws SQLException {
		
		UUID uuid = UUID.fromString(rs.getString("uuid"));
		int salesPersonId = rs.getInt("salesPersonId");
		int companyId = rs.getInt("companyId");
		LocalDate invoiceDate = null;
		Person salesPerson = null;
		Company customer = null;
		
		try {
		invoiceDate = LocalDate.parse(rs.getString("invoiceDate"));
		} catch(DateTimeParseException e) {
			throw new RuntimeException(e);
		}
		
   
        salesPerson = IDLoader.loadPersonById(salesPersonId, conn);
        customer = IDLoader.loadCompanyById(companyId,conn);
        
		
		return new Invoice(uuid,customer, salesPerson, invoiceDate);
	}
	
}
