package com.vgb;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Invoice {

	private UUID invoiceUUID;
    private Company customer;
    private Person salesperson;
    private LocalDate invoiceDate;
    private List<InvoiceItem> invoiceItems;
    
	public Invoice(UUID invoiceUUID, Company customer, Person salesperson, LocalDate invoiceDate) {
		super();
		this.invoiceUUID = invoiceUUID;
		this.customer = customer;
		this.salesperson = salesperson;
		this.invoiceDate = invoiceDate;
		
		
	}
	
	public Invoice(Invoice invoice, List<InvoiceItem> invoiceItem) {
		this.invoiceUUID = invoice.invoiceUUID;
		this.customer = invoice.customer;
		this.salesperson = invoice.salesperson;
		this.invoiceDate = invoice.invoiceDate;
		this.invoiceItems = invoiceItem;
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


	public List<InvoiceItem> getInvoiceItems() {
		return invoiceItems;
	}


	public double grandTotal(List<InvoiceItem> invoiceItem){
		
		double total = 0.0;
		for(InvoiceItem i : invoiceItem) {
			total += i.getItem().getTotal();
		}
		
		return total;
		
	}
	
public double grandSubTotal(List<InvoiceItem> invoiceItem){
		
		double total = 0.0;
		for(InvoiceItem i : invoiceItem) {
			total += i.getItem().getSubTotal();
		}
		
		return total;
		
	}

public double grandTaxTotal(List<InvoiceItem> invoiceItem){
	
	double total = 0.0;
	for(InvoiceItem i : invoiceItem) {
		total += i.getItem().getTaxes();
	}
	
	return total;
	
}
	
	@Override
	public String toString() {
		return String.format("Invoice ID: %s \n \n"
				+ "Customer: %s \n"
				+ "Salesperson: %s \n", this.getInvoiceUUID(), this.getCustomer().toString(),
				this.getSalesperson().toString());
	}
    
	
    
}
