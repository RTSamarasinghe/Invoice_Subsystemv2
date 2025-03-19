package com.vgb;

import java.util.UUID;

public class Company {

	
	private UUID uuid;
	private String name;
	private Person contact;
	private Address address;
	
	
	public Company(UUID uuid, String name, Person contact, Address address) {
		super();
		this.uuid = uuid;
		this.name = name;
		this.contact = contact;
		this.address = address;
	}


	public UUID getUuid() {
		return uuid;
	}


	public String getName() {
		return name;
	}


	public Person getContact() {
		return contact;
	}

	public boolean uuidMatch(UUID uuid) {
		if(this.getUuid().equals(uuid)){
		return true;	
		}
		return false;
	}

	public Address getAddress() {
		return address;
	}
	
	
	
}
