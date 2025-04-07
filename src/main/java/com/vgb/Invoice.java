package com.vgb;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
/**
 * Represents an Invoice in the System
 */
public class Invoice {

	private UUID invoiceUUID;
    private Company customer;
    private Person salesperson;
    private LocalDate invoiceDate;
    private List<InvoiceItem> invoiceItems;
    
    /**
     * Constructs an Invoice based on the given attributes
     * @param invoiceUUID Invoice
     * @param customer Company the transaction was done to
     * @param salesperson Person who carried out the sale
     * @param invoiceDate date of the invoice
     */
	public Invoice(UUID invoiceUUID, Company customer, Person salesperson, LocalDate invoiceDate) {
		super();
		this.invoiceUUID = invoiceUUID;
		this.customer = customer;
		this.salesperson = salesperson;
		this.invoiceDate = invoiceDate;
		
		
	}
	
	/**
	 * Constructs a mapped Invoice with the list of items belonging to that invoice
	 * @param invoice
	 * @param invoiceItem
	 */
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

	/**
	 * Returns the sum of Totals in an invoice
	 */
	public double grandTotal(List<InvoiceItem> invoiceItem){
		
		double total = 0.0;
		for(InvoiceItem i : invoiceItem) {
			total += i.getItem().getTotal();
		}
		
		return total;
		
	}
	
	/**
	 * Returns the sum of SubTotals in an invoice
	 */
public double grandSubTotal(List<InvoiceItem> invoiceItem){
		
		double total = 0.0;
		for(InvoiceItem i : invoiceItem) {
			total += i.getItem().getSubTotal();
		}
		
		return total;
		
	}
/**
 * Returns the sum of taxes in an invoice
 */
public double grandTaxTotal(List<InvoiceItem> invoiceItem){
	
	double total = 0.0;
	for(InvoiceItem i : invoiceItem) {
		total += i.getItem().getTaxes();
	}
	
	return total;
	
}

public String listTotal(Map<Invoice,List<InvoiceItem>> invoicesReport) {
	StringBuilder report = new StringBuilder();
	
	 for (Map.Entry<Invoice, List<InvoiceItem>> pair : invoicesReport.entrySet()) {
	    	List<InvoiceItem> items = pair.getValue();
	
	report.append(String.format("\nInvoice Total %57s -------------------------"
			+ " \n %70s $%s $%s $%s\n", " ",
	" ",pair.getKey().grandSubTotal(items),
	pair.getKey().grandTaxTotal(items),
	pair.getKey().grandTotal(items)));
	 }
	 
	 return report.toString();
}

public String itemList(List<InvoiceItem> invoiceItem) {
	StringBuilder report = new StringBuilder();

for(InvoiceItem it : invoiceItem) {
	report.append( it.getItem().toString());
}
return report.toString();
					
}
	
	@Override
	public String toString() {
		return String.format("Invoice ID: %s \n \n"
				+ "Customer: %s \n"
				+ "Salesperson: %s \n", this.getInvoiceUUID(), this.getCustomer().toString(),
				this.getSalesperson().toString());
	}
    
	
    
}
