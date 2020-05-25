
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
        
        int totalBirths = 0, totalNames = 0, boysNames = 0, girlsNames = 0; 
        
        for(CSVRecord record : parser){
            
            totalBirths += Integer.parseInt(record.get(2));
            totalNames++;
            
            if(record.get(1).equals("F")){
                girlsNames+= Integer.parseInt(record.get(2));
            }
            else{
                boysNames+= Integer.parseInt(record.get(2));
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
    
        String filename = "us_babynames_small\\testing\\yob"+year+"short.csv";
            
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
    
        int year = 2012; String name = "Mason"; String gender = "M";
        
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
            String filename = "us_babynames_small\\testing\\yob"+newYear+"short.csv";
            
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
    
        String name = "Olivia"; int year = 2012; int newYear = 2014; String gender = "F";
        
        whatIsNameInYear(name,year,newYear,gender);
    
    }
}


