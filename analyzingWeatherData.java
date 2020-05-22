
/**
 * Analyzes weather data from the state of north carolina
 * 
 * @author (Deontee Hendricks) 
 * @version (1.0)
 */

import edu.duke.*;
import org.apache.commons.csv.*;

public class Part1 {

    public CSVRecord coldestHourInFile(CSVParser parser){
        
        CSVRecord coldestRecordSoFar = null;
        
        
        for(CSVRecord record : parser){
            
            if(coldestRecordSoFar == null){
                coldestRecordSoFar = record;
            }
            else{
                double currentTemp = Double.parseDouble(record.get("TemperatureF"));
                double coldestTemp = Double.parseDouble(coldestRecordSoFar.get("TemperatureF"));
                if(coldestTemp > currentTemp && currentTemp != -9999){
                    coldestRecordSoFar = record;
                }
            }            
        }
        return coldestRecordSoFar;
    }
    
    public void testColdestHourInFile(){
    
        FileResource fr = new FileResource("weather-2012-01-01.csv");
        CSVRecord coldest = coldestHourInFile(fr.getCSVParser());
        System.out.println("Coldest temperature: " + coldest.get("TemperatureF")
                            +" at time:" + coldest.get("TimeEST"));
    }    
}
