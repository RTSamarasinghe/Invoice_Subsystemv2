package com.vgb;

import java.util.UUID;

public class Rental extends Equipment{

	public Rental(UUID uuid, String name, String modelName, double price) {
		super(uuid, name, modelName, price);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public double getTaxes() {
		return 0;
		
	}
	
	@Override
	public double getTotal() {
		return 0;
	}
}
