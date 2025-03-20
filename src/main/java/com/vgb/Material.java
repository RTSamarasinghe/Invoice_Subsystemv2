package com.vgb;

import java.util.UUID;

public class Material extends Item{

	
	
	private String unit;
	private double quantity;
	private final static double TAX_RATE = 0.0715;
	
	public Material(UUID uuid, String name, String unit, double costPerUnit) {
		super(uuid, name, costPerUnit);
		this.unit = unit;
	}
	
	public Material(Material material,  double quantity) {
		super(material.getUUID(), material.getName(), material.getPrice());
		this.quantity = quantity;
	}
	
	public String getUnit() {
		return unit;
	}

	@Override
	public double getSubTotal() {
		return super.getPrice() * this.getQuantity();
	}
	
	@Override
	public double getTaxes() {
		
		return getSubTotal() * TAX_RATE ;
	}
	
	@Override
	public double getTotal() {
		return roundToCent(getSubTotal() + getTaxes());
	}

	public double getQuantity() {
		return quantity;
	}
	
	
}
