package com.vgb;

import java.util.Map;
import java.util.UUID;

public class Test {

	public static void main(String[] args) {
		
		
		Map<UUID, InvoiceItem> invoiceItems = InvoiceItemLoader.loadInvoiceItem();	
		Rental r = (Rental) invoiceItems.get(UUID.fromString("9d1bef3d-e36b-45eb-a308-40b3a1d7df14")).getItem();
		System.out.println(r.getSubTotal());
		}
	
}
