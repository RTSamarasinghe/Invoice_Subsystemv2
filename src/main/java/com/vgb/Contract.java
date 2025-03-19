package com.vgb;

import java.util.UUID;

public class Contract extends Item {
	
	private Company customer;

	public Contract(UUID uuid, String name, double price, Company customer) {
		super(uuid, name, price);
		this.customer = customer;
	}

	public Company getCustomer() {
		return customer;
	}

	@Override
	public double getTaxes() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public double getTotal() {
		// TODO Auto-generated method stub
		return 0;
	}	
	
}
