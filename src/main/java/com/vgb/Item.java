package com.vgb;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;
/**
 * Represents an abstract item in the system.
 * Base class for equipment, materials, and contracts.
 */
public abstract class Item implements Expenses{
	
	private UUID uuid;
	private String name;
	private double price;
	
	/**
	 * Constructs an Item based on the given attributes
	 * @param uuid key mostly used for mapping
	 * @param name Item name
	 * @param price item price
	 */
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
	
	/**
	 * Rounds value parsed into the nearest cent.
	 * @param value
	 * @return rounded price
	 */
	protected double roundToCent(double value) {
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
	
	


	
}
