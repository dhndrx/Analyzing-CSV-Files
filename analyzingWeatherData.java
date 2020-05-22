
/**
 * Analyzes weather data from the state of north carolina
 * 
 * @author (Deontee Hendricks) 
 * @version (1.0)
 */

import edu.duke.*;
import java.io.*;
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
                if(coldestTemp > currentTemp && currentTemp != -9999){//-9999 means there wasnt a valid reading
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
    
    public String fileWithColdestTemperature()throws IOException{
    
        DirectoryResource dr = new DirectoryResource();
        String coldestFile = "";
        
        for(File f : dr.selectedFiles()){
        
            FileResource fr = new FileResource(f.getCanonicalPath());
            
            CSVRecord currentRecord = coldestHourInFile(fr.getCSVParser());
            
            if(coldestFile.equals("")){
                coldestFile = f.getCanonicalPath();
            }
            
            else{
                FileResource coldestFr = new FileResource(coldestFile);
                CSVRecord coldestRecord = coldestHourInFile(coldestFr.getCSVParser());
                double currentTemp = Double.parseDouble(currentRecord.get("TemperatureF"));
                double coldestTemp = Double.parseDouble(coldestRecord.get("TemperatureF"));
                if(currentTemp < coldestTemp && currentTemp != -9999){
                    coldestFile = f.getCanonicalPath();
                }
            }
        }
        
        return coldestFile;
    }
    
    public void testFileWithColdestTemperature()throws IOException{
    
        String coldDay = fileWithColdestTemperature();
        int found = coldDay.indexOf("weather-");
        System.out.println(coldDay.substring(found));
    
    }
}
