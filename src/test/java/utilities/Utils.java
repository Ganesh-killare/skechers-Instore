package utilities;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Utils {


	public static List<String> generateDateTimeIPHostName() {
		Date now = new Date();
		SimpleDateFormat dateFormatter = new SimpleDateFormat("MMddyyyy");
		SimpleDateFormat timeFormatter = new SimpleDateFormat("HHmmss");

		String formattedTime = timeFormatter.format(now);
		String finalDate = dateFormatter.format(now);
		String IP = getHostIP();
		String HOSTNAME =  getHostName();
		

	

		List<String> utils = new ArrayList<>();
		utils.add(formattedTime);
		utils.add(finalDate);
		utils.add(IP);
		utils.add(HOSTNAME);
		
	

		return utils;
	}

	public static String setFileName(String fileName) {
		Date now = new Date();
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
		String finalDate = dateFormatter.format(now);
		String FILE_NAME = finalDate + "_" + fileName + ".xlsx";

		return FILE_NAME;

	}
	

    public static String generateTransactionAmount() {
        double amount = ThreadLocalRandom.current().nextDouble(30.00, 99.99);
        
        // Round the double value to the nearest integer
        int roundedAmount = (int) Math.round(amount);
        String roundedAmountString = Integer.toString(roundedAmount);
        
        return roundedAmountString;
    }
	public static String getHostIP() {
		InetAddress localhost = null;
		try {
			localhost = InetAddress.getLocalHost();    
		} catch (UnknownHostException e) {

			e.printStackTrace();
		}
		String ipAddress = localhost.getHostAddress();
		return ipAddress;
	}
	
	  public static String getHostName() {
		  String hostName = null ;
	        try {
	            // Get the local host
	            InetAddress localhost = InetAddress.getLocalHost();

	            // Get the host name
	            String hostname = localhost.getHostName();
	            hostName = hostname.toUpperCase();
	          
	        } catch (UnknownHostException e) {
	            System.err.println("Error getting host name: " + e.getMessage());
	            e.printStackTrace();
	        }
			return hostName;
	    }
	public String[] getParameters() {
		String[] parameters = { "CardToken", "CardIdentifier", "CRMToken", "CardEntryMode", "TransactionTypeCode",
				"TransactionSequenceNumber", "CardType", "SubCardType", "TotalApprovedAmount", "ResponseText",
				"ResponseCode", "TransactionIdentifier", "AurusPayTicketNum", "ApprovalCode", "ProcessorToken", "AID" };
		return parameters ;
	}
	public List<String> gcbParameters() {
		String[] parameters = { "CardToken", "CardIdentifier", "CRMToken", "CardEntryMode", "TransactionTypeCode",
				"TransactionSequenceNumber", "CardType", "SubCardType", "TotalApprovedAmount", "ResponseText",
				"ResponseCode", "TransactionIdentifier", "AurusPayTicketNum", "ApprovalCode", "ProcessorToken", "AID" };
		List<String> GCB_Parameters = new ArrayList<>(Arrays.asList(parameters));
		return GCB_Parameters ;
	}
	
	

	@DataProvider(name = "VoidData")
	public String[][] allTransactionsVoid() throws IOException {
		String path = "./test-Data\\VoidTransactions.xlsx";// taking xl file from testData

		ExcelUtility xlutil = new ExcelUtility(path);// creating an object for XLUtility

		int totalrows = xlutil.getRowCount("Transactions");
		int totalcols = xlutil.getCellCount("Transactions", 1);

		String logindata[][] = new String[totalrows][totalcols];// created for two dimension array which can store the
																// data user and password

		for (int i = 1; i <= totalrows; i++) // 1 //read the data from xl storing in two deminsional array
		{
			for (int j = 0; j < totalcols; j++) // 0 i is rows j is col
			{
				logindata[i - 1][j] = xlutil.getCellData("Transactions", i, j); // 1,0
			}
		}
		return logindata;// returning two dimension array
	}
	
}

