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
    
    public void tester(){
            FileResource fr = new FileResource();
            CSVParser parser = fr.getCSVParser();
            String info = countryInfo(parser, "Germany");
            
            System.out.println(info);        
    }
}
