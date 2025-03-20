package com.vgb;

import java.util.UUID;

public class Contract extends Item {
	
	  private Company customer;
	  private double contractprice;

	    public Contract(UUID uuid, String name, double contractPrice, Company customer) {
	        super(uuid, name, contractPrice);
	        this.customer = customer;
	        this.contractprice = contractPrice;
	    }

	    public Contract(Contract contract, double contractPrice) {
	        super(contract.getUUID(), contract.getName(), contractPrice);
	        this.contractprice = contractPrice;
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
	        return contractprice;
	    }

		@Override
		public double getSubTotal() {
			return contractprice;
		}
	}
