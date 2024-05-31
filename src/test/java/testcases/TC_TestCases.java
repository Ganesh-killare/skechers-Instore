package testcases;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import org.jdom2.JDOMException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.BaseClass;
import requestbuilder.Close;
import requestbuilder.GCB;
import requestbuilder.Return;
import requestbuilder.Sale;
import requestbuilder.TicketDisplay;
import responsevalidator.Response_Parameters;

public class TC_TestCases extends BaseClass {

	static List<String> amount;
	String FILE_NAME = "Skechers";

	@BeforeMethod
	public void ticketDisplay() throws Exception, IOException, InterruptedException {  
   
		String ticketRequest = TicketDisplay.request();
		sendRequestToAESDK(ticketRequest);
		// System.out.println(ticketRequest);
		amount = (List<String>) TicketDisplay.getTransactionAmount(ticketRequest);      

		receiveResponseFromAESDK();
		// System.out.println(ticketResponse);    

	}

	@Test(invocationCount = 4)
	public void testRefundOfSale() throws Exception, IOException, InterruptedException {

		List<String> saleResult = performSaleTransaction(amount);

		if (saleResult.get(0).equalsIgnoreCase("APPROVAL") || saleResult.get(0).equalsIgnoreCase("VALIDATION")) {

			performRefundTransaction(saleResult);

		}

	}

	@Test(invocationCount = 4)
	public void testVoidOfSale() throws Exception, IOException, InterruptedException {

		List<String> saleResult = performSaleTransaction(amount);

		if (saleResult.get(0).equalsIgnoreCase("APPROVAL") || saleResult.get(0).equalsIgnoreCase("VALIDATION")) {

			performVoidTransaction(saleResult);

		}

	}

	@Test(invocationCount = 4)
	public void testVoidOfRefundWithoutSale() throws Exception, IOException, InterruptedException {

		List<String> saleResult = performRefundWithoutSaleTransaction(amount);

		if (saleResult.get(0).equalsIgnoreCase("APPROVAL") || saleResult.get(0).equalsIgnoreCase("VALIDATION")) {

			performVoidTransaction(saleResult);

		}
	}

//	@Test(invocationCount = 1)
	public void testCancelLast() throws Exception, IOException, InterruptedException {

		List<String> saleResult = performSaleTransaction(amount);

		if (saleResult.get(0).equalsIgnoreCase("APPROVAL") || saleResult.get(0).equalsIgnoreCase("VALIDATION")) {

			PerformCancelLast(saleResult);

		}

	}

//	@Test()
	public void testCheckTransactions() throws Exception, IOException, InterruptedException {

		List<String> saleResult = performCheckSaleTransaction(amount);

		if (saleResult.get(0).equalsIgnoreCase("APPROVAL") || saleResult.get(0).equalsIgnoreCase("VALIDATION")) {

			// Refund Transactions
			performVoidTransaction(saleResult);

		}

	}

//	@Test(invocationCount = 1)
	public void testSale() throws Exception, IOException, InterruptedException {

		performSaleTransaction(amount);

	}

	@AfterMethod
	public void afterTransactionsOparations()
			throws UnknownHostException, IOException, InterruptedException, JDOMException {

		sendRequestToAESDK(Close.Request());
		receiveResponseFromAESDK();

		xl.saveExcelFile(FILE_NAME);

	}
}
