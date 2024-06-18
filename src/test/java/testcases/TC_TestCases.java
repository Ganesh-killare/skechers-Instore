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

	
	@Test(invocationCount = 2)
	public void testRefundOfSale() throws Exception, IOException, InterruptedException {
		 String methodName = new Exception().getStackTrace()[0].getMethodName();
	        System.out.println("Method name: " + methodName);

		try {
		   
			List<String> saleResult = performSaleTransaction(amount);

			if (saleResult.get(0).equalsIgnoreCase(approvalText) || saleResult.get(0).equalsIgnoreCase(validationText)) {

		//		Thread.sleep(3000);

				performRefundTransaction(saleResult);
			}
		} catch (Exception e) {
			System.out.println("we are not able to perform REFUND OF SALE transaction");
		}

	}

	@Test(invocationCount = 1)
	public void testVoidOfSale() throws Exception, IOException, InterruptedException {
		try {
			List<String> saleResult = performSaleTransaction(amount);

			if (saleResult.get(0).equalsIgnoreCase(approvalText) || saleResult.get(0).equalsIgnoreCase(validationText)) {

				performVoidTransaction(saleResult);

			}

		} catch (Exception e) {
			System.out.println("We are not able to perform VOID OF SALE transaction");
		}

	}

	@Test(invocationCount = 2)
	public void testVoidOfRefundWithoutSale() throws Exception, IOException, InterruptedException {
		try {
			List<String> saleResult = performRefundWithoutSaleTransaction(amount);

			if (saleResult.get(0).equalsIgnoreCase(approvalText) || saleResult.get(0).equalsIgnoreCase(validationText)) {

				performVoidTransaction(saleResult);

			}

		} catch (Exception e) {
			System.out.println("We are not able to perform VOID_OF_REFUND_WITHOUT_SALE ");
		}

	}

//	@Test(invocationCount = 1)
	public void testCancelLast() throws Exception, IOException, InterruptedException {

		List<String> saleResult = performSaleTransaction(amount);

		if (saleResult.get(0).equalsIgnoreCase(approvalText) || saleResult.get(0).equalsIgnoreCase(validationText)) {

			PerformCancelLast(saleResult);

		}

	}

	@Test()
	public void testCheckTransactions() throws Exception, IOException, InterruptedException {

		List<String> saleResult = performCheckSaleTransaction(amount);

		if (saleResult.get(0).equalsIgnoreCase(approvalText) || saleResult.get(0).equalsIgnoreCase(validationText)) {

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
