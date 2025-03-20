package com.vgb;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class FileOutputWriter {
    
    static void writeToFile(String content, String filePath) throws IOException {
        // Create directories if they don't exist
        File file = new File(filePath);
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }
        
        try (java.io.FileWriter writer = new java.io.FileWriter(file)) {
            writer.write(content);
        }
    }
    
    public static void main(String[] args) {
        Map<UUID, Person> persons = PersonLoader.loadPerson();
        Map<UUID, Company> companies = CompanyLoader.loadCompany();
        Map<UUID, Item> items = ItemLoader.loadItem();
        
        // Define output directory
        String outputDir = "output/";
        
        try {
            // Convert and write JSON files
            writeToFile(DataConverter.convertToJson(persons), outputDir + "persons.json");
            writeToFile(DataConverter.convertToJson(companies), outputDir + "companies.json");
            writeToFile(DataConverter.convertToJson(items), outputDir + "items.json");
            
            // Convert and write XML files
            writeToFile(DataConverter.convertToXml(persons), outputDir + "persons.xml");
            writeToFile(DataConverter.convertToXml(companies), outputDir + "companies.xml");
            writeToFile(DataConverter.convertToXml(items), outputDir + "items.xml");
            
            System.out.println("All files written successfully to " + new File(outputDir).getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error writing files: " + e.getMessage());
            e.printStackTrace();
        }
    }
}