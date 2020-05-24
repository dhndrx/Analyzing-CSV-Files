
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
    
    public void printAllData(CSVParser parser){
    
        for(CSVRecord record : parser){
            System.out.println(record.get("DateUTC")+": "+record.get("TemperatureF"));
        }
    }
    
    

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
    
        FileResource fr = new FileResource("weather-2014-05-01.csv");
        CSVRecord coldest = coldestHourInFile(fr.getCSVParser());
        System.out.println("Coldest temperature: " + coldest.get("TemperatureF")
                            +" at time:" + coldest.get("TimeEDT"));
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
        System.out.println("Coldest day was in the file " +coldDay.substring(found));
        
        FileResource fr = new FileResource(coldDay);
        CSVRecord coldest = coldestHourInFile(fr.getCSVParser());
        System.out.println("Coldest temperature on that day was " +coldest.get("TemperatureF"));
        System.out.println("All the temperatures on the coldest day were:");
        
        fr = new FileResource(coldDay);
        
        printAllData(fr.getCSVParser());
    }
    
    public CSVRecord lowestHumidityInFile(CSVParser parser){
        
        CSVRecord lowestHumiditySoFar = null;
        
        for(CSVRecord record: parser){
            
            if(record.get("Humidity").equals("N/A")){
                continue;
            }
            
            else if(lowestHumiditySoFar == null){
                lowestHumiditySoFar = record;
            }
            
            else{
                
                double currentHumidity = Double.parseDouble(record.get("Humidity"));
                double lowestHumidity = Double.parseDouble(lowestHumiditySoFar.get("Humidity"));
                
                if(lowestHumidity>currentHumidity){
                    lowestHumiditySoFar = record;
                }
            
            }
        }
        
        return lowestHumiditySoFar;
    }
    
    public void testlowestHumidityInFile(){
        
        String filename = "weather-2014-04-01.csv";
        FileResource fr = new FileResource(filename);
        CSVParser parser = fr.getCSVParser();
        CSVRecord lowestHumidity = lowestHumidityInFile(parser);
        
        System.out.println("Lowest Humidity was "+lowestHumidity.get("Humidity")+ " at "+ lowestHumidity.get("DateUTC"));
        
        
    }
    
    public CSVRecord lowestHumidityInManyFiles()throws IOException{
    
        DirectoryResource dr = new DirectoryResource();
        CSVRecord lowestHumiditySoFar = null;
        
        for(File f : dr.selectedFiles()){
        
            FileResource fr = new FileResource(f.getCanonicalPath());
            
            CSVRecord currentRecord = lowestHumidityInFile(fr.getCSVParser());
            
            if(lowestHumiditySoFar == null){
                lowestHumiditySoFar = currentRecord;  
            }
            
            else{
            
                double lowestHumiditySoFarValue = Double.parseDouble(lowestHumiditySoFar.get("Humidity"));
                double currentHumidity = Double.parseDouble(currentRecord.get("Humidity"));
                
                if(lowestHumiditySoFarValue > currentHumidity){
                    lowestHumiditySoFar = currentRecord;
                }
            }
        }    
        return lowestHumiditySoFar;
    }
    
    public void testLowestHumidityInManyFiles()throws IOException{
    
        CSVRecord lowest = lowestHumidityInManyFiles();
        System.out.println("Lowest Humidity was " + lowest.get("Humidity") + " at "+lowest.get("DateUTC"));
    }
    
    public double averageTemperatureInFile(CSVParser parser){
        
        double average = 0.0;
        int counter = 0;
        double total = 0.0;
        
        for(CSVRecord record : parser){
            
            total += Double.parseDouble(record.get("TemperatureF"));
            counter++;
        }
        
        average = total/counter;
        return average;
    
    }
    
    public void testAverageTemperatureInFile(){
    
        String filename = "weather-2014-06-01.csv";
        FileResource fr = new FileResource(filename);
        
        double averageTemp = averageTemperatureInFile(fr.getCSVParser());
        
        System.out.println("Average temperature in file: "+averageTemp);    
    }
    
    public double averageTemperatureWithHighHumidityinFile(CSVParser parser, int value){
    
        double average = 0.0;
        int counter = 0;
        double total = 0.0;
        
        for(CSVRecord record : parser){
            
            if(record.get("Humidity").equals("N/A")){
                continue;
            }
            
            else{
                double humidity = Double.parseDouble(record.get("Humidity"));
                if(humidity >= value){
                    total += Double.parseDouble(record.get("TemperatureF"));
                    counter++; 
                }          
            }
            
        }
        
        average = total/counter;
        return average;
    }
    
    public void testAverageTemperatureWithHighHumidityinFile(){
    
        String filename = "weather-2014-03-30.csv";
        FileResource fr = new FileResource(filename);
        int humidityThreshold = 80;
        double averageTemp = averageTemperatureWithHighHumidityinFile(fr.getCSVParser(),humidityThreshold);
        
        System.out.println("Average temperature in high humidity file: "+averageTemp);    
    }
}
