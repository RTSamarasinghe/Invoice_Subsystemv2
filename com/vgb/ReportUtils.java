package com.vgb;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vgb.database.ConnectionFactory;
import com.vgb.database.DataLoader;

public class ReportUtils {
	
	private static final Logger LOGGER = LogManager.getLogger();
	
	/**
     * Distributes Invoices and invoiceItems to another map comparing UUID Strings
     * @return
     */
    public static Map<Invoice, List<InvoiceItem>> populateInvoice(){
    	
    	DataLoader dl = new DataLoader();
    	
    	Map<Invoice, List<InvoiceItem>> invoiceItemMap = null;
    	try(Connection conn = ConnectionFactory.getConnection()){
    		
    		  invoiceItemMap = dl.groupData("""
    		  		SELECT * FROM InvoiceItem
    		  		""", new LoadInvoiceItem(), conn);
    		  
    		  
    	}catch(SQLException e) {
    		LOGGER.log(Level.ERROR, "Populating invoice connection error", e);
    	}
    	
    	     
    	    
    	
    	return invoiceItemMap;
    	
    	
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
        DataLoader dl = new DataLoader();
        
        Map<UUID, Company> companies = null;
        try(Connection conn = ConnectionFactory.getConnection()){
        	companies = dl.loadData("""
            		SELECT * FROM Company
        	""", new LoadCompany(),conn);
        }catch(SQLException e) {
        	LOGGER.log(Level.ERROR, "calculating company bad connection");
        }
        
         
        		
        Map<Company, Integer> companyInvoiceCounts = new HashMap<>();
        
        for (Company company : companies.values()) {
            companyInvoiceCounts.put(company, 0);
        }
        
        for(Map.Entry<Invoice, List<InvoiceItem>> entry : invoiceItems.entrySet()) {
			Invoice invoice = entry.getKey();
			
			
			Company company = companies.get(invoice.getCustomer().getUuid());			
			if(company == null) continue;
			
			companyInvoiceCounts.put(company, companyInvoiceCounts.getOrDefault(company, 0) + 1);
	    }
        return companyInvoiceCounts;
    }
    
    /**
	 * Printing and grand Totals logic
	 * @param companyTotals 
	 * @param count
	 */
	
	public static String companySummary() {
		
		StringBuilder report = new StringBuilder();
		
		Map<Company, Double> companyTotals = calculateCompanyTotals();
		Map<Company, Integer> count = calculateCompanyInvoiceCount();
		
		for(Company company: companyTotals.keySet()) {
			
			double totalAmount = companyTotals.get(company);
			int invoiceCount = count.getOrDefault(company, 0);
			
			report.append(String.format("%s \n %70s %s %8s $%s\n", company.getName(), 
					" ",invoiceCount, "",  totalAmount));
		}
		
		double companyTotal = Math.round(companyTotals.values().stream().mapToDouble(d -> d).sum());
		int invoiceCountTotal = count.values().stream().mapToInt(d -> d).sum();
		report.append(String.format("%95s"
				+ "\n%70s %s %8s $%s\n","------------------------"," ", invoiceCountTotal, " ", companyTotal));
		
		return report.toString();		
			
	}
	/**
	 * Logic for calculating the totals for invoice Summary
	 * @return sum of items, taxes and totals of invoices
	 */
	
		public static Map<Invoice, double[]> invoiceSummary() {
		    Map<Invoice, List<InvoiceItem>> invoiceItems = populateInvoice();
		    Map<Invoice, double[]> summary = new HashMap<>();

		    for (Map.Entry<Invoice, List<InvoiceItem>> entry : invoiceItems.entrySet()) {
		        Invoice invoice = entry.getKey();
		        List<InvoiceItem> items = entry.getValue();

		        int itemCount = items.size();

		        double tax = invoice.grandTaxTotal(items); 
		        double total = invoice.grandTotal(items);

		        summary.put(invoice, new double[]{itemCount, tax, total});
		    }

		    return summary;
		}

		
	

}
