package com.vgb;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Invoice {

	private UUID invoiceUUID;
    private Company customer;
    private Person salesperson;
    private LocalDate invoiceDate;
    
    
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
    
    
}
