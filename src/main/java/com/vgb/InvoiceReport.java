package com.vgb;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

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
			
			for(InvoiceItem it : pair.getValue()) {
				report.append(it.getItem().toString());
								
			}
			report.append(
					String.format("\nInvoice Total %57s -------------------------"
							+ " \n %70s $%s $%s $%s\n", " ",
					" ",pair.getKey().grandSubTotal(items),
					pair.getKey().grandTaxTotal(items),
					pair.getKey().grandTotal(items)));
			report.append("\n+=====================================================================+ \n");
		}
	return report.toString();

}
	/**
	 * Prints company summary report
	 */
	public static void printCompanySummary() {
		
		Map<Invoice, List<InvoiceItem>> invoiceItems = ReportUtils.populateInvoice();
		
	    Map<UUID, Company> companies = CompanyLoader.loadCompany();
	    
	    Map<Company, Double> companyInvoiceTotals = new HashMap<>();
	    Map<Company, Integer> companyInvoiceCounts = new HashMap<>();
	    
		System.out.println("==========================================\n"
				+ "Company Summary\n"
				+ "==========================================\n");
		
		System.out.println(String.format("%70s %s %s %s", " ", "#Invoices", "", "Grand Total"));
	    
		for(Map.Entry<Invoice, List<InvoiceItem>> entry : invoiceItems.entrySet()) {
			Invoice invoice = entry.getKey();
			List<InvoiceItem> items = entry.getValue();
			
			Company company = companies.get(invoice.getCustomer().getUuid());
			
			if(company == null) continue;
			
			double invoiceTotal = invoice.grandTotal(items);
			companyInvoiceTotals.put(company, companyInvoiceTotals.getOrDefault(company, 0.0) + invoiceTotal);
			companyInvoiceCounts.put(company, companyInvoiceCounts.getOrDefault(company, 0) + 1);
			
		}
		ReportUtils.companySummary();
	}
	
	/**
	 * Prints Invoice Summary report
	 */
	
	public static String printInvoiceSummary() {
		StringBuilder report = new StringBuilder();
	    Map<Invoice, double[]> summary = ReportUtils.invoiceSummary();
	    
	    report.append("+----------------------------------------------------------------------------------------+");
	    report.append("| Summary Report - By Total                                                              |");
	    report.append("+----------------------------------------------------------------------------------------+");
	    report.append(String.format("%-40s %-30s %10s %12s %12s", "Invoice #", "Customer", "Num Items", "Tax", "Total"));

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

	        report.append(String.format("%-40s %-30s %10d %12.2f %12.2f", 
	            invoice.getInvoiceUUID(), 
	            invoice.getCustomer().getName(), 
	            itemCount, 
	            tax, 
	            total));
	    }

	    report.append("+----------------------------------------------------------------------------------------+");
	    report.append(String.format("%-72s %10d %12.2f %12.2f", " ", totalItems, totalTax, grandTotal));
	    
	    return report.toString();
	}

	
	public static void main(String[] args) {
		
		System.out.println(InvoiceReport.printInvoice());
		
		InvoiceReport.printCompanySummary();		
		InvoiceReport.printInvoiceSummary();
		
		FileOutputWriter.writeReportsToFile("output/myoutput.txt");
	}
	
	
	
}
