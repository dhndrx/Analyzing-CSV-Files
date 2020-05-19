/**
 * Analyzes data in a CSV file about countries and their exports.
 * @author (Deontee Hendricks) 
 * @version (1.0)
 */

import edu.duke.*;
import org.apache.commons.csv.*;

public class Part1 {
        
    public String countryInfo(CSVParser parser, String country){
        
        for(CSVRecord record : parser){
            
            if(record.get("Country").equals(country)){
                return record.get("Country")+": "+record.get("Exports")+": " +record.get("Value (dollars)");
            }
        }
        return "NOT FOUND";
    }
    
    public void listExportersTwoProducts(CSVParser parser, String exportItem1, String exportItem2){
        
        for(CSVRecord record : parser){
            
            String exports = record.get("Exports");
            if(exports.contains(exportItem1)&&exports.contains(exportItem2)){
                System.out.println(record.get("Country"));
            }
        }
    }
    
    public int numberOfExporters(CSVParser parser, String exportItem){
        
        int counter = 0;
        
        for(CSVRecord record : parser){
            if(record.get("Exports").contains(exportItem)){
                counter++;
            }
        }
        
        return counter;
    }
    
    public void bigExporters(CSVParser parser, String amount){
        
        for(CSVRecord record : parser){
            if(record.get("Value (dollars)").length()>amount.length()){
                System.out.println(record.get("Country")+" "+record.get("Value (dollars)"));
            }
        }
    }
    
    public void tester(){
            FileResource fr = new FileResource();
            CSVParser parser = fr.getCSVParser();
            String info = countryInfo(parser, "Nauru");
            System.out.println(info); 
            
            parser = fr.getCSVParser();
            listExportersTwoProducts(parser,"fish", "nuts");
            
            parser = fr.getCSVParser();
            System.out.println("Number of countries that export gold: "+numberOfExporters(parser,"gold"));
            
            parser = fr.getCSVParser();
            bigExporters(parser,"$999,999,999,999,");  
    }
}
