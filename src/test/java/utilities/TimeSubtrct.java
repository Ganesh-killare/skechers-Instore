package utilities;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import testcases.TimeSubtraction;

public class TimeSubtrct {
    
    @Test(dataProvider = "MeijerTransData", dataProviderClass = TimeSubtrct.class)
    public void subtrct(String timefirst, String timeSecond) {
    	
    	   // Define the date-time format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss,SSS");

        // Parse the strings into LocalDateTime objects
        LocalDateTime dt1 = LocalDateTime.parse("2024-05-30 " + timefirst.trim(), formatter);
        LocalDateTime dt2 = LocalDateTime.parse("2024-05-30 " + timeSecond.trim(), formatter);

        // Calculate the difference in milliseconds
        long differenceInMillis = ChronoUnit.MILLIS.between(dt1, dt2);
        
      //  System.out.println(dt1 +" - "+ dt2 + "Diffrence in Milisecound "+ differenceInMillis);

        // Convert milliseconds to seconds
        double differenceInSeconds = differenceInMillis / 1000.0;

        // Format the result to three decimal places
        String formattedDifference = String.format("%.3f", differenceInSeconds);

        // Print the difference
        System.out.println(timefirst + " - " + timeSecond + " Difference in seconds: " + formattedDifference);
    }
    
    
    
    
    @Test(dataProvider = "AddMilisecound", dataProviderClass = TimeSubtrct.class)
    public static void addMilliseconds(String  time1, String time2) {
    	
    	 long value1 = Long.parseLong(time1.trim());
         long value2 = Long.parseLong(time2.trim());
        // Sum the values in milliseconds
        long totalMillis = value1 + value2;

        // Convert milliseconds to seconds
        double totalSeconds = totalMillis / 1000.0;

        // Format the result to three decimal places
        String formattedTotalSeconds = String.format("%.3f", totalSeconds);

        // Print the result
        System.out.println("Sum of " + value1 + " ms and " + value2 + " ms is: " + formattedTotalSeconds + " seconds");
   
}

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @DataProvider(name = "MeijerTransData")
    public String[][] allTransactionsVoid() throws IOException {
        String path = "./test-Data/MeijerTransTimes.xlsx"; // taking xl file from testData

        ExcelUtility xlutil = new ExcelUtility(path); // creating an object for ExcelUtility

        int totalRows = xlutil.getRowCount("Sheet1");
        int totalCols = xlutil.getCellCount("Sheet1", 1);

        String[][] logindata = new String[totalRows][totalCols]; // created for two-dimensional array which can store the data

        for (int i = 1; i <= totalRows; i++) { // read the data from xl storing in two-dimensional array
            for (int j = 0; j < totalCols; j++) {
                logindata[i - 1][j] = xlutil.getCellData("Sheet1", i, j);
            }
        }
        return logindata; // returning two-dimensional array
    }
    
    @DataProvider(name = "AddMilisecound")
    public String[][] AddMilisecound() throws IOException {
    	String path = "./test-Data/MeijerTransTimes.xlsx"; // taking xl file from testData
    	
    	ExcelUtility xlutil = new ExcelUtility(path); // creating an object for ExcelUtility
    	
    	int totalRows = xlutil.getRowCount("Sheet2");
    	int totalCols = xlutil.getCellCount("Sheet2", 1);
    	
    	String[][] logindata = new String[totalRows][totalCols]; // created for two-dimensional array which can store the data
    	
    	for (int i = 1; i <= totalRows; i++) { // read the data from xl storing in two-dimensional array
    		for (int j = 0; j < totalCols; j++) {
    			logindata[i - 1][j] = xlutil.getCellData("Sheet2", i, j);
    		}
    	}
    	return logindata; // returning two-dimensional array
    }
}
