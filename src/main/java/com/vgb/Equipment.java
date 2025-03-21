package com.vgb;

import java.util.UUID;

public class Equipment extends Item {
	
	private String modelName;
	private static final double TAX_RATE = 0.0525;
	
	
	public Equipment(UUID uuid, String name, String modelName, double price) {
		super(uuid, name,price);
		this.modelName = modelName;
		
	}
	
	
	public String getModelName() {
		return modelName;
	}

	@Override
	public double getTaxes() {
		return roundToCent(super.getPrice() * TAX_RATE);
	}	


	@Override
	public double getTotal() {
		return roundToCent(super.getPrice() + this.getTaxes());
	}


	@Override
	public double getSubTotal() {
		return super.getPrice();
	}

	@Override
	public String toString() {
		return String.format("%s (Purchase) \n %s-%s \n %70s $%s $%s $%s", this.getUUID(), 
				this.getModelName(), this.getName()," ", this.getSubTotal(), this.getTaxes(), this.getTotal());
	}
	
}
