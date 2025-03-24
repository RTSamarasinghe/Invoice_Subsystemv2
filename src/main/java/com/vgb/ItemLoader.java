package com.vgb;


import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Utility class for loading and constructing Items from a flat file
 */
public class ItemLoader {

	public static final String FILE_PATH = "data/Items.csv";
	
	public static Map<UUID,Item> loadItem(){
		
		Map<UUID, Company> company = CompanyLoader.loadCompany();	
		
		Map<UUID,Item> items = new HashMap<UUID,Item>();
		
		
		
		try(Scanner s = new Scanner(new File(FILE_PATH))){
			
			int numRecords = Integer.parseInt(s.nextLine());
			Item item = null;
			Company customer = null;
			String line = s.nextLine();
			for (int i = 0; i < numRecords; i++) {
				line = s.nextLine();
				if(!line.trim().isEmpty()) {
					
					String parts[] = line.split(",");
					UUID uuid = UUID.fromString(parts[0]);
					String name = parts[2];
					String field = parts[3];
					double price = 0.0, costPerUnit = 0.0; 
					UUID customerUUID;
					
					if(parts[1].equals("E")) {
						
						String modelName = field;
						price = Double.parseDouble(parts[4]);
						
						item = new Equipment(uuid, name, modelName, price);
					} else if(parts[1].equals("M")) {
						
						String unit = parts[3];
						costPerUnit = Double.parseDouble(parts[4]);
						
						item = new Material(uuid, name, unit, costPerUnit);
					}else if(parts[1].equals("C")) {
						
						try {
							customerUUID = UUID.fromString(field);
							}catch(IllegalArgumentException e) {
								throw new IllegalArgumentException("Invalid UUID format: " + field);
							}
						customer = company.get(customerUUID);
						price = Double.parseDouble(parts[4]);
											
						item = new Contract(uuid, name, price, customer);
						 
						
					}	
					items.put(uuid, item);
					
				}
				}
		} catch (FileNotFoundException e) {
			System.err.print("Something went wrong");
			e.printStackTrace();
		}

		return items;
		
	}
	
}
