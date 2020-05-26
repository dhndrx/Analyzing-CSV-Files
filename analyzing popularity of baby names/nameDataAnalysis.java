
/**
 * Analyzes data on popularity of baby names
 * 
 * @author (Deontee Hendricks) 
 * @version (1.0)
 */

import edu.duke.*;
import java.io.*;
import org.apache.commons.csv.*;

public class part1 {
    
    public void totalBirths(FileResource fr){
        
        CSVParser parser = fr.getCSVParser(false);
        
        int totalBirths = 0, totalNames = 0,boysBorn = 0, boysNames = 0,girlsBorn = 0, girlsNames = 0; 
        
        for(CSVRecord record : parser){
            
            totalBirths += Integer.parseInt(record.get(2));
            totalNames++;
            
            if(record.get(1).equals("F")){
                girlsBorn+= Integer.parseInt(record.get(2));
                girlsNames++;
            }
            else{
                boysBorn+= Integer.parseInt(record.get(2));
                boysNames++;
            }
        
        }
        System.out.println("Total Births: "+totalBirths+"\nTotal names: "+totalNames+"\nTotal boy names: "
                            +boysNames+"\nTotal girl names: "+girlsNames);
    }
    
    public void testTotalBirths(){
        
        FileResource fr = new FileResource();
        totalBirths(fr);
    
    }
    
    public int getRank(int year, String name, String gender){
    
        String filename = "us_babynames\\us_babynames_by_year\\yob"+year+".csv";
            
        FileResource fr = new FileResource(filename);
            
        CSVParser parser = fr.getCSVParser(false);
        
        int rank = 0;
        for(CSVRecord record : parser){
            
            if(record.get(1).equals(gender)){                
                rank++;  
            }
            if(record.get(0).equals(name) && record.get(1).equals(gender)){
                return rank;
            }
            
        }            
        return -1;
    }
    
    public void testGetRank(){
    
        int year = 1982; String name = "Forrest"; String gender = "M";
        
        int rank = getRank(year, name, gender);
        
        if(rank == -1){
           System.out.println("NAME NOT FOUND"); 
        }
        else{
            System.out.println(name+ "'s rank: "+rank);
        }
        
    }
    
    public void whatIsNameInYear(String name, int year, int newYear, String gender){
        
        int originalYearRank = 0, newYearRank = 0;
        
        originalYearRank = getRank(year, name, gender);
        
        if(originalYearRank != -1){
            String filename = "us_babynames\\us_babynames_by_year\\yob"+year+".csv";;
            
            FileResource fr = new FileResource(filename);
            
            CSVParser parser = fr.getCSVParser(false);
        
            for(CSVRecord record: parser){
        
                if(originalYearRank == getRank(newYear, record.get(0),gender)){
                    if(gender.equals("F")){
                        System.out.println(name+" born in "+year+" would be "+record.get(0)+" if she was born in "+newYear);
                    }
                    else{
                        System.out.println(name+" born in "+year+" would be "+record.get(0)+" if he was born in "+newYear);
                    }
                    
                }        
            }
        }
        else{
            System.out.println("No babies named "+name+" were born in "+year);
        }
    }
    
    public void testWhatIsNameInYear(){
    
        String name = "Owen"; int year = 1974; int newYear = 2014; String gender = "M";
        
        whatIsNameInYear(name,year,newYear,gender);
    
    }
    
    public int yearOfHighestRank(String name, String gender){
    
        DirectoryResource  dr = new DirectoryResource();
        
        int highestRankingYear = -1, highestRank = 0, currentRank = 0;
                
        
        for(File f : dr.selectedFiles()){
        
            FileResource fr = new FileResource(f);
            
            String filename = f.getName();
            
            int found = filename.indexOf("yob");
            int year = Integer.parseInt(filename.substring(found+3,found+7));
            currentRank = getRank(year,name,gender);
            
            if(highestRank == 0 && currentRank != -1){
                highestRank = currentRank;
                highestRankingYear = year;
            }
            
            else{
                if(currentRank < highestRank && currentRank != -1){
                    highestRank = currentRank;
                    highestRankingYear = year;
                }
            }
        }
        
        
        return  highestRankingYear;
    }
    
    public void testYearOfHighestRank(){
        
        String name = "Genevieve", gender = "F";
        int year = yearOfHighestRank(name,gender);
        System.out.println(year);
    
    }
    
    public double getAverageRank(String name, String gender){
        
        double averageRank = -1.0; double total = 0.0; int counter = 0;
        
        DirectoryResource dr = new DirectoryResource();
        
        for(File f : dr.selectedFiles()){
        
            FileResource fr = new FileResource(f);
            
            String filename = f.getName();
            
            int found = filename.indexOf("yob");
            int year = Integer.parseInt(filename.substring(found+3,found+7));
            int currentRank = getRank(year,name,gender);
            
            if(currentRank != -1){
                counter++;
                total += currentRank;
            }            
        }
        
        averageRank = total/counter;
        return averageRank;
    }
    
    public void testGetAverageRank(){
        
        String name = "Robert", gender = "M";
        double avgRank =  getAverageRank(name,gender);
        System.out.println(avgRank);        
    }
    
    public int getTotalBirthsRankedHigher(int year, String name, String gender){
        
        int total = 0;
        
        String filename = "us_babynames\\us_babynames_by_year\\yob"+year+".csv";
        FileResource fr = new FileResource(filename);
        CSVParser parser = fr.getCSVParser(false);     
        
        for(CSVRecord record : parser){
        
            if(record.get(0).equals(name) && record.get(1).equals(gender)){
                break;
            }
            
            else{
                if(record.get(1).equals(gender)){
                    total += Integer.parseInt(record.get(2));
                }                
            }
        }
        
        return total;
    }
    
    public void testGetTotalBirthsRankedHigher(){
    
        int year = 1990; String name = "Emily", gender = "F";
        
        int totalBRH = getTotalBirthsRankedHigher(year,name,gender);
        
        System.out.println(totalBRH);
    }
    
}



