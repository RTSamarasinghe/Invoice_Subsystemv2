package com.vgb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ReportUtils {
	
	/**
     * Distributes Invoices and invoiceItems to another map comparing UUID Strings
     * @return
     */
    public static Map<Invoice, List<InvoiceItem>> populateInvoice(){
    	
    	Map<Invoice, List<InvoiceItem>> invoicesReport = new HashMap<>();
    	
    	
    	 Map<UUID, Invoice> invoiceMap = InvoiceLoader.loadInvoice();
    	    Map<UUID, List<InvoiceItem>> invoiceItemMap = InvoiceItemLoader.loadInvoiceItem();
    	    
    	for(UUID i : invoiceMap.keySet()) {
	    	if(invoiceItemMap.containsKey(i)) {
	    		
	    		invoicesReport.put(invoiceMap.get(i), invoiceItemMap.get(i));	    		
	    	}	    	
	    }
    	return invoicesReport;
    	
    	
    }
    
    /**
     * Calculates the total amount per company.
     * @return Map of Company to total invoice amount.
     */
    public static Map<Company, Double> calculateCompanyTotals() {
        
		Map<Invoice, List<InvoiceItem>> invoiceItems = ReportUtils.populateInvoice();
		
	    Map<UUID, Company> companies = CompanyLoader.loadCompany();
	    
	    Map<Company, Double> companyInvoiceTotals = new HashMap<>();

	    for(Map.Entry<Invoice, List<InvoiceItem>> entry : invoiceItems.entrySet()) {
			Invoice invoice = entry.getKey();
			List<InvoiceItem> items = entry.getValue();
			
			Company company = companies.get(invoice.getCustomer().getUuid());
			
			if(company == null) continue;
			
			double invoiceTotal = invoice.grandTotal(items);
			companyInvoiceTotals.put(company, companyInvoiceTotals.getOrDefault(company, 0.0) + invoiceTotal);
	    }
	    
        return companyInvoiceTotals;
    }
    
    /**
     * Calculates the count of invoices per company.
     * @return Map of Company to the number of invoices.
     */
    public static Map<Company, Integer> calculateCompanyInvoiceCount() {
        Map<Invoice, List<InvoiceItem>> invoiceItems = populateInvoice();
        Map<UUID, Company> companies = CompanyLoader.loadCompany();
        Map<Company, Integer> count = new HashMap<>();

        for (Invoice invoice : invoiceItems.keySet()) {
            Company company = companies.get(invoice.getCustomer().getUuid());
            if (company != null) {
                count.put(company, count.getOrDefault(company, 0) + 1);
            }
        }
        return count;
    }
    
    /**
	 * Printing and grand Totals logic
	 * @param companyTotals 
	 * @param count
	 */
	
	public static void companySummary() {
		
		Map<Company, Double> companyTotals = calculateCompanyTotals();
		Map<Company, Integer> count = calculateCompanyInvoiceCount();
		
		for(Company company: companyTotals.keySet()) {
			double totalAmount = companyTotals.get(company);
			int invoiceCount = count.getOrDefault(company, 0);
			System.out.println(String.format("%s \n %70s %s %8s $%s", company.getName(), 
					" ",invoiceCount, "",  totalAmount));
		}
		
		double companyTotal = companyTotals.values().stream().mapToDouble(d -> d).sum();
		
		System.out.println(String.format("%95s"
				+ "\n%70s %s %8s $%s","------------------------"," ", count.size()+1, " ", companyTotal));
		
		
			
	}
	
	
		public static Map<Invoice, double[]> invoiceSummary() {
		    Map<Invoice, List<InvoiceItem>> invoiceItems = populateInvoice();
		    Map<Invoice, double[]> summary = new HashMap<>();

		    for (Map.Entry<Invoice, List<InvoiceItem>> entry : invoiceItems.entrySet()) {
		        Invoice invoice = entry.getKey();
		        List<InvoiceItem> items = entry.getValue();

		        int itemCount = items.size();
		        double subTotal = 0;
		        subTotal = invoice.grandSubTotal(items);

		        double tax = invoice.grandTaxTotal(items); 
		        double total = invoice.grandTotal(items);

		        summary.put(invoice, new double[]{itemCount, tax, total});
		    }

		    return summary;
		}

		
	

}
