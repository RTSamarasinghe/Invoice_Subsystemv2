package com.vgb;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

public abstract class Item implements Expenses{
	
	private UUID uuid;
	private String name;
	private double price;
	
	public Item(UUID uuid, String name, double price) {
		super();
		this.uuid = uuid;
		this.name = name;
		this.price = price;
	}
	
	
	public UUID getUUID() {
		return uuid;
	}
	public String getName() {
		return name;
	}


	public double getPrice() {
		return price;
	}
	
	protected double roundToCent(double value) {
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
	
	


	
}
