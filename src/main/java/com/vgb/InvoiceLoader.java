package com.vgb;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;

public class InvoiceLoader {
	
	public static final String FILE_PATH = "data/Invoices.csv";
	
	
	
	public static Map<UUID, Invoice> loadInvoice(){
		
		Map<UUID, Person> person = PersonLoader.loadPerson();
		Map<UUID, Company> company = CompanyLoader.loadCompany();
		
		Map<UUID, Invoice> invoices = new HashMap<>();
		String line = null;
		try(Scanner s = new Scanner(new File(FILE_PATH))){
			int numRecords = Integer.parseInt(s.nextLine());
			s.nextLine();
			for (int i = 0; i < numRecords; i++) {
				
				line = s.nextLine();
				
				
				if(!line.trim().isEmpty()) {
					Invoice inv = null;
					String parts[] = line.split(",");
					UUID invoiceuuid = null;
					UUID salesPersonuid;
					UUID customerUuid;
					
					String invoiceidstr = parts[0];
					String customeridstr = parts[1];
					String salespersonidstr = parts[2];
					
					LocalDate invoiceDate = LocalDate.parse(parts[3]);
					
					Person salesPerson = null;
					Company customer = null;
					
					
					try {
						invoiceuuid = UUID.fromString(invoiceidstr);
					}catch(IllegalArgumentException e){
						System.err.print("Something went wrong uuid :(" + invoiceidstr );
						e.printStackTrace();
					}
					
					try {
						customerUuid = UUID.fromString(customeridstr);
						customer = company.get(customerUuid);
					}catch(IllegalArgumentException e) {
						System.err.println("Customer uuid: " + customeridstr);
					}
					
					try {
						salesPersonuid = UUID.fromString(salespersonidstr);
						salesPerson = person.get(salesPersonuid);
					}catch(IllegalArgumentException e) {
						System.err.println("SalesPerson uuid: " + salespersonidstr);
					}
					
					
					inv = new Invoice(invoiceuuid, customer, salesPerson, invoiceDate);
					invoices.put(invoiceuuid, inv);
				}
				
			}
			s.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return invoices;
	}

}
