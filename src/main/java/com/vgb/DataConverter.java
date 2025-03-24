package com.vgb;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;

import java.util.Map;
import java.util.UUID;
/**
 * 
 * Converts data to JSON and XML files using gson and XML stream
 * Outputs to output folder
 * 
 */
public class DataConverter {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final XStream xStream = new XStream();

    static {
        xStream.alias("data", Map.class);
        xStream.alias("entry", Map.Entry.class);
    }

    public static String convertToJson(Map<UUID, ?> data) {
        return gson.toJson(data);
    }

    public static String convertToXml(Map<UUID, ?> data) {
        return xStream.toXML(data);
    }

    public static void main(String[] args) {
        Map<UUID, Person> persons = PersonLoader.loadPerson();
        Map<UUID, Company> companies = CompanyLoader.loadCompany();
        Map<UUID, Item> items = ItemLoader.loadItem();

        System.out.println("Persons JSON:\n" + convertToJson(persons));
        System.out.println("Persons XML:\n" + convertToXml(persons));

        System.out.println("Companies JSON:\n" + convertToJson(companies));
        System.out.println("Companies XML:\n" + convertToXml(companies));

        System.out.println("Items JSON:\n" + convertToJson(items));
        System.out.println("Items XML:\n" + convertToXml(items));
    }
}