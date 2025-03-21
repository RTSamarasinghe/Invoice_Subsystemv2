package com.vgb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class InvoiceReport {
	
	public Map<Invoice, List<InvoiceItem>> printInvoice() {
	    Map<Invoice, List<InvoiceItem>> invoicesReport = new HashMap<>();

	    Map<UUID, Invoice> invoiceMap = InvoiceLoader.loadInvoice();
	    Map<UUID, List<InvoiceItem>> invoiceItemMap = InvoiceItemLoader.loadInvoiceItem();

	   
	    for (Invoice invoice : invoiceMap.values()) {
	        List<InvoiceItem> itemsList = invoiceItemMap.getOrDefault(invoice.getInvoiceUUID(), new ArrayList<>());
	        invoicesReport.put(invoice, itemsList);
	    }

	    
	    for (Map.Entry<Invoice, List<InvoiceItem>> entry : invoicesReport.entrySet()) {
	        Invoice invoice = entry.getKey();
	        List<InvoiceItem> items = entry.getValue();
	        
	        System.out.println("Invoice ID: " + invoice.getInvoiceUUID());
	        System.out.println("Total Items in Invoice: " + items.size()); 
	        
	        System.out.println("------------------------------------------------------------");

	    
	        System.out.println("Customer: \n" + invoice.getCustomer().toString());
	        System.out.println("Salesperson: \n" + invoice.getSalesperson().toString());
	        System.out.println("Invoice Date: " + invoice.getInvoiceDate());
	        System.out.println("Items:");

	        for (InvoiceItem item : items) {

	        	
	        	System.out.println(item.getItem().toString());
	            
	        }

	        System.out.println("====================================================================");
	    }

	    return invoicesReport;
	

}
}
