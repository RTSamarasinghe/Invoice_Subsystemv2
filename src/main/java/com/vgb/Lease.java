package com.vgb;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class Lease extends Equipment {

	private LocalDate startDate;
	private LocalDate endDate;
	private static final double FLAT_TAX = 1500.00;
	
//	public Lease(UUID uuid, String name, String modelName, double price, LocalDate startDate, LocalDate endDate) {
//		super(uuid, name, modelName, price);
//		this.startDate = startDate;
//		this.endDate = endDate;
//		
//	}
	
	public Lease(Equipment equipment, LocalDate startDate, LocalDate endDate) {
		super(equipment.getUUID(), equipment.getName(), equipment.getModelName(), equipment.getPrice());
		this.startDate = startDate;
		this.endDate = endDate;
	}

	
	public double calculateDays() {	
		return ChronoUnit.DAYS.between(getStartDate(), getEndDate()) + 1;
	}
	
	public double calculatePrice() {	     
	    double years = calculateDays() / 365.0;
	    return (years / 5) * super.getPrice() * 1.5;
	}
	
	@Override
	public double getTaxes() {
		
		if (calculatePrice() > 12500.00) {
			return FLAT_TAX;
		}
		return 0;
	}
	
	@Override
	public double getPrice() {
		return roundToCent(calculatePrice() + getTaxes());
	}
	
	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}
	
	
	
}
