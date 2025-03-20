package com.vgb;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Invoice implements Expenses {

	private UUID invoiceUUID;
    private Company customer;
    private Person salesperson;
    private LocalDate invoiceDate;
    private Map<UUID, InvoiceItem> invoiceItems;
    
	public Invoice(UUID invoiceUUID, Company customer, Person salesperson, LocalDate invoiceDate) {
		super();
		this.invoiceUUID = invoiceUUID;
		this.customer = customer;
		this.salesperson = salesperson;
		this.invoiceDate = invoiceDate;
		
		
	}
	
	
	public UUID getInvoiceUUID() {
		return invoiceUUID;
	}
	public Company getCustomer() {
		return customer;
	}
	public Person getSalesperson() {
		return salesperson;
	}
	public LocalDate getInvoiceDate() {
		return invoiceDate;
	}


	public Map<UUID, InvoiceItem> getInvoiceItems() {
		return invoiceItems;
	}


	@Override
	public double getTaxes() {
		Map<UUID, InvoiceItem> invoiceItem = getInvoiceItems();
			
		
		return 0;
	}


	@Override
	public double getTotal() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public double getSubTotal() {
		// TODO Auto-generated method stub
		return 0;
	}
    
	
    
}
