package com.vgb;

import java.util.UUID;

public class Rental extends Equipment{

	private double hours;
	private static final double TAX = 0.0438;
	
	public Rental(UUID uuid, String name, String modelName, double price, double hours) {
		super(uuid, name, modelName, price);
		this.hours = hours;
	}

	public Rental(Equipment equipment, double hours) {
		super(equipment.getUUID(), equipment.getName(), equipment.getModelName(), equipment.getPrice());
		this.hours = hours;
	}
	
	public double calculateRate() {
		return super.getPrice() * 0.001;
	}
	
	@Override
	public double getTaxes() {
		return super.getPrice() * TAX;
		
	}
	
	@Override
	public double getSubTotal() {
		return getHours() * calculateRate();
	}
	
	@Override
	public double getTotal() {
		return getTaxes() + getSubTotal();
	}


	public double getHours() {
		return hours;
	}
}
