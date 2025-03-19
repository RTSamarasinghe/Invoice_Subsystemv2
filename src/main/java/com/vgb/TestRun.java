package com.vgb;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TestRun {
	
	public static void main(String[] args) {
		
		
		
//		Map<UUID, Person> person = PersonLoader.loadPerson();
//		Map<UUID, Company> company = CompanyLoader.loadCompany();
//		Map<UUID, Item> items = ItemLoader.loadItem();
//		Contract c = (Contract) items.get(UUID.fromString("eaa9fc89-4004-4403-a3ac-fa15e9775231"));
//		System.out.println(c.getCustomer().getName());
//		Map<UUID, Invoice> invoice = InvoiceLoader.loadInvoice();
//		
//		System.out.println(invoice.get(UUID.fromString("e3a4f06d-5aa7-4a8c-937d-472199c84731")).getSalesperson().getFirstName());
		
		//System.out.println(person.get(UUID.fromString("467a2470-03a9-404c-bda3-51d318b5a84c")).getFirstName());
		
		//System.out.println(company.get(UUID.fromString("1d30a64b-8595-4e70-9885-37ba844ecec5")).getName());
		
		Map<UUID, InvoiceItem> invoiceItems = InvoiceItemLoader.loadInvoiceItem();
		System.out.println(invoiceItems.get(UUID.fromString("2bbd815b-d292-4523-81ad-8cb651adf6c3")).getInvoice().getCustomer().getName());
		
	}
}
