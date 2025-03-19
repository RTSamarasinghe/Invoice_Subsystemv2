package com.vgb;

import java.util.UUID;

public class Material extends Item{

	
	
	private String unit;
	

	
	public Material(UUID uuid, String name, String unit, double costPerUnit) {
		super(uuid, name, costPerUnit);
		this.unit = unit;
	}
	
	public String getUnit() {
		return unit;
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
