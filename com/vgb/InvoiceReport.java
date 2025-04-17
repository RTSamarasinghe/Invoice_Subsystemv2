package com.vgb;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.Logger;

import com.vgb.database.ConnectionFactory;
import com.vgb.database.DataFactory;
import com.vgb.database.DataLoader;
import com.vgb.database.IDLoader;

import org.apache.logging.log4j.LogManager;
/*
 * Contains Printing functions for the three reports
 * 
 */
public class InvoiceReport {  
    

    /**
     * 
     * Prints a detailed report of Invoice total
     * Invoice Sub totals, Invoice Tax Totals, Invoice Grand Totals
     */
    
	public static String printInvoice() {
	    
StringBuilder report = new StringBuilder();
		
		Map<Invoice,List<InvoiceItem>> invoicesReport = ReportUtils.populateInvoice();
		
	    for (Map.Entry<Invoice, List<InvoiceItem>> pair : invoicesReport.entrySet()) {
	    	List<InvoiceItem> items = pair.getValue();
	    	
			report.append(pair.getKey().toString());
			report.append(String.format("Items(%s)", pair.getValue().size()));
			report.append("\n----------------------------------------------------------------------\n");
			
			report.append(String.format("\n %70s %s %s %s \n", " ", "SUBTOTAL", "TAX", "TOTAL"));
			
			report.append(pair.getKey().itemList(items));
			report.append("\n+=====================================================================+ \n");
		}
	return report.toString();

}
	/**
	 * Prints company summary report
	 */
	public static String printCompanySummary() {
	    
		StringBuilder report = new StringBuilder();
		
		report.append("==========================================\n"
				+ "Company Summary\n"
				+ "==========================================\n");
		
		report.append(String.format("%70s %s %s %s\n", " ", "#Invoices", "", "Grand Total"));
	    
		report.append(ReportUtils.companySummary());
		
		return report.toString();
		
	}
	
	/**
	 * Prints Invoice Summary report
	 */
	
	public static String printInvoiceSummary() {
		StringBuilder report = new StringBuilder();
	    Map<Invoice, double[]> summary = ReportUtils.invoiceSummary();
	    
	    report.append("+----------------------------------------------------------------------------------------+ \n");
	    report.append("| Summary Report - By Total                                                              | \n ");
	    report.append("+----------------------------------------------------------------------------------------+ \n");
	    report.append(String.format("%-40s %-30s %10s %12s %12s \n", "Invoice #", "Customer", "Num Items", "Tax", "Total"));

	    double grandTotal = 0;
	    double totalTax = 0;
	    int totalItems = 0;

	    for (Map.Entry<Invoice, double[]> entry : summary.entrySet()) {
	        Invoice invoice = entry.getKey();
	        double[] values = entry.getValue();

	        int itemCount = (int) values[0];
	        double tax = values[1];
	        double total = values[2];

	        grandTotal += total;
	        totalTax += tax;
	        totalItems += itemCount;

	        report.append(String.format("%-40s %-30s %10d %12.2f %12.2f \n", 
	            invoice.getInvoiceUUID(), 
	            invoice.getCustomer().getName(), 
	            itemCount, 
	            tax, 
	            total));
	    }

	    report.append("+----------------------------------------------------------------------------------------+ \n");
	    report.append(String.format("%-72s %10d %12.2f %12.2f", " ", totalItems, totalTax, grandTotal));
	    
	    return report.toString();
	}

	
	public static void main(String[] args) {
		
		System.out.println(InvoiceReport.printInvoice());
		System.out.println(InvoiceReport.printInvoiceSummary());
		System.out.println(InvoiceReport.printCompanySummary());
		
		DataLoader dl = new DataLoader();
		
//		Map<UUID, Person> persons = dl.loadData("""
//				SELECT p.uuid, p.firstName, p.lastName, p.phoneNumber, e.address
//            		FROM Person p JOIN Email e on e.personId = p.personId
//				""", new LoadPerson());
//		
//		System.out.println(persons);
		
		
//		Map<UUID, Company> companies = dl.loadData("""
//				SELECT uuid, companyName, personId, addressId
//				FROM Company
//				""", new LoadCompany());
//		System.out.println(companies);
//		
//		Map<UUID, Item> items = dl.loadData("""
//				SELECT * FROM Item
//				""", new LoadItem());
//		
//		System.out.println(items);
//		
//		LoadPerson p = new LoadPerson();
//		IDLoader<Person> s = new IDLoader<>(p);
//	
//		
//		
//		
//		Person person = s.loadById("""
//				SELECT p.uuid, p.firstName, p.lastName, p.phoneNumber, e.address
//            		FROM Person p JOIN Email e on e.personId = p.personId
//            		WHERE p.personId = ?
//				""", 1);
//		
//		System.out.println(person.toString());
//		
//		Map<UUID, Invoice> invoices = dl.loadData("""
//				SELECT * FROM Invoice
//				""", new LoadInvoice());
//		
//		System.out.println(invoices);
//		String query = "SELECT * FROM InvoiceItem";
//		try {
//			Map<UUID, List<InvoiceItem>> invoiceItems = dl.groupData(query, new LoadInvoiceItem());
//			System.out.println(invoiceItems);
//		} catch (SQLException e) {
//			
//			e.printStackTrace();
//		}
//		
		
		DataLoader n = new DataLoader();
////		try {
////			
////			IDLoader<Invoice> idff = new IDLoader<>(new LoadInvoice()); 
////			Invoice c = idff.loadById("""
////					SELECT * FROM Invoice WHERE invoiceId = ?
////					""",4, ConnectionFactory.getConnection());
////			System.out.println(c.toString());
////		} catch (SQLException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
//		
//		try {
//			
//			Connection conn = ConnectionFactory.getConnection();
//			Map<UUID, Invoice> invoices = n.loadData("""
//					SELECT * FROM Invoice
//					""", new LoadInvoice(), conn );
//			
//			Map<Invoice, List<InvoiceItem>> invItem = n.groupData("""
//					SELECT * FROM InvoiceItem
//					""", new LoadInvoiceItem(), conn);
//			System.out.println(invItem);
//////			
////			
////	
//			
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	/*List<Person> persons = DataLoader.loadPersons();
	System.out.println("\n--- Persons ---");
	for(Person p : persons) {
			System.out.println(p.toString());
	}
		
		
		
		List<Address> addresses = DataLoader.loadAddresses();
		System.out.println("\n--- Addresses ---");
		for(Address a : addresses) {
		    System.out.println(a.toString());
		}
		
		
		List<Company> companies = DataLoader.loadCompanies();
		System.out.println("\n--- Companies ---");
		for(Company c : companies) {
		    System.out.println(c.toString());
		}
		
		List<Item> items = DataLoader.loadItems();
		System.out.println("\n--- Items ---");
		for(Item i : items) {
		    System.out.println(i.toString());		}
		System.out.println(DataLoader.loadAddressById(1));*/
		
		//FileOutputWriter.writeReportsToFile("data/output.txt");
		
	}
	
	
	}	

