package com.vgb;

public class Address {
	
	
	private String street;
	private String city;
	private String state;
	private String zip;
	
	public Address(String street, String city, String state, String zip) {
		super();
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}

	public String getStreet() {
		return street;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getZip() {
		return zip;
	}
	
	@Override
	public String toString() {
		return String.format("%15s %s, \n %s %s", this.getStreet(), this.getCity(),
				this.getState(), this.getZip());
	}
}
