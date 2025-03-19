package com.vgb;

import java.util.UUID;

public class Contract extends Item {
	
	private Company customer;
	private double price;
	public Contract(UUID uuid, String name, double price, Company customer) {
		super(uuid, name, price);
		this.customer = customer;
	}
	
	public Contract(Contract contract, double price) {
		super(contract.getUUID(), contract.getName(), contract.getPrice());
		this.price = price;
	}

	public Company getCustomer() {
		return customer;
	}

	@Override
	public double getTaxes() {
		return 0;
	}


	@Override
	public double getTotal() {
		return super.getPrice();
	}	
	
}
