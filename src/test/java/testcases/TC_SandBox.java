package testcases;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import org.jdom2.JDOMException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.BaseClass;
import requestbuilder.Close;
import requestbuilder.GCB;
import requestbuilder.Return;
import requestbuilder.Sale;
import requestbuilder.TicketDisplay;
import responsevalidator.Response_Parameters;
import utilities.Logger;

public class TC_SandBox extends BaseClass {
	static List<String> amount;
	String FILE_NAME = "Skechers";
	
	
	
	@BeforeClass
	public void ticketDisplayAndGetCardBin() throws Exception, IOException, InterruptedException {
	
		

		String ticketRequest = TicketDisplay.request();
		sendRequestToAESDK(ticketRequest);
		// System.out.println(ticketRequest);
		amount = (List<String>) TicketDisplay.getTransactionAmount(ticketRequest);
		System.out.println("Amount is : " + amount.get(2));

		String ticketResponse = receiveResponseFromAESDK();
		// System.out.println(ticketResponse);
	

	}

	@Test(priority = 1)
	public void testSetOfTransactions() throws Exception, IOException, InterruptedException {
		String gcbRequest = GCB.Request(amount.get(2));
		// System.out.println(gcbRequest);
		sendRequestToAESDK(gcbRequest);
		String gcbResponse = receiveResponseFromAESDK();
		// System.out.println(gcbResponse);
	

		Response_Parameters GCBPrameter = new Response_Parameters(gcbResponse);
		String result = GCBPrameter.getParameterValue("ResponseText");

		if (result.equalsIgnoreCase("APPROVAL")) {

			String salerequest = Sale.Request(GCBPrameter.getParameterValue("CardToken"), amount);

			sendRequestToAESDK(salerequest);
			// System.out.println(salerequest);
			String saleResponse = receiveResponseFromAESDK();
			// System.out.println(saleResponse);

		
			// System.out.println(saleResponse);

			Response_Parameters SaleParam = new Response_Parameters(saleResponse);
			String saleResult = SaleParam.getParameterValue("ResponseText");
			String ATicketNumber = SaleParam.getParameterValue("AurusPayTicketNum");
			String ATransactionID = SaleParam.getParameterValue("TransactionIdentifier");
			System.out.println("Sale :   " + ATransactionID + "    Card Type : " +SaleParam.getParameterValue("CardType") );

			if (saleResult.equalsIgnoreCase("APPROVAL")) {

				// Refund Transactions

				String returnRequest = Return.Request("02", amount, ATicketNumber, ATransactionID);
				sendRequestToAESDK(returnRequest);
				String returnResponse = receiveResponseFromAESDK();
				Response_Parameters returnRes = new Response_Parameters(returnResponse);

				

				String RefundResult = returnRes.getParameterValue("ResponseText");
				String RATicketNumber = returnRes.getParameterValue("AurusPayTicketNum");
				String RATransactionID = returnRes.getParameterValue("TransactionIdentifier");
				
				  System.out.println("Rsale :  " + RATransactionID + "    Card Type : " +returnRes.getParameterValue("CardType") ); 
				  
					/*
					 * if (RefundResult.contentEquals("APPROVAL")) {
					 * 
					 * String VoidRequest = Return.Request("06", amount, RATicketNumber,
					 * RATransactionID); sendRequestToAESDK(VoidRequest); String VoidResponse =
					 * receiveResponseFromAESDK(); Response_Parameters VoidRes = new
					 * Response_Parameters(VoidResponse); String VoidTransactionID =
					 * VoidRes.getParameterValue("TransactionIdentifier");
					 * System.out.println("vRSale : " + VoidTransactionID);
					 * 
					 * 
					 * }
					 */
				 
			}

		}
	}

	@Test(priority = 2)
	public void testVoidOfSale() throws Exception, IOException, InterruptedException {

		String gcbRequest = GCB.Request(amount.get(2));
		// System.out.println(gcbRequest);
		sendRequestToAESDK(gcbRequest);
		String gcbResponse = receiveResponseFromAESDK();
		// System.out.println(gcbResponse);
	

		Response_Parameters GCBPrameter = new Response_Parameters(gcbResponse);
		String result = GCBPrameter.getParameterValue("ResponseText");

		if (result.equalsIgnoreCase("APPROVAL")) {

			String salerequest = Sale.Request(GCBPrameter.getParameterValue("CardToken"), amount);

			sendRequestToAESDK(salerequest);
			// System.out.println(salerequest);
			String saleResponse = receiveResponseFromAESDK();
			// System.out.println(saleResponse);

		
			// System.out.println(saleResponse);

			Response_Parameters SaleParam = new Response_Parameters(saleResponse);
			String saleResult = SaleParam.getParameterValue("ResponseText");
			String ATicketNumber = SaleParam.getParameterValue("AurusPayTicketNum");
			String ATransactionID = SaleParam.getParameterValue("TransactionIdentifier");
		//	System.out.println("Sale  :  " + ATransactionID);

			if (saleResult.equalsIgnoreCase("APPROVAL")) {

				// Refund Transactions

				String returnRequest = Return.Request("06", amount, ATicketNumber, ATransactionID);
				sendRequestToAESDK(returnRequest);
				String returnResponse = receiveResponseFromAESDK();
				Response_Parameters returnRes = new Response_Parameters(returnResponse);
				System.out.println("Void :   "  + returnRes.getParameterValue("TransactionIdentifier")+ "   Card Type " + returnRes.getParameterValue("CardType"));
				

			}

		}

	}

	@Test(priority = 3)
	public void testVoidOfRefundWithoutSale() throws Exception, IOException, InterruptedException {

		String gcbRequest = GCB.Request(amount.get(2));
		// System.out.println(gcbRequest);
		sendRequestToAESDK(gcbRequest);
		String gcbResponse = receiveResponseFromAESDK();
		// System.out.println(gcbResponse);
	

		Response_Parameters GCBPrameter = new Response_Parameters(gcbResponse);
		String result = GCBPrameter.getParameterValue("ResponseText");

		if (result.equalsIgnoreCase("APPROVAL")) {

			String salerequest = Sale.refundRequest(GCBPrameter.getParameterValue("CardToken"), amount);

			sendRequestToAESDK(salerequest);
			// System.out.println(salerequest);
			String saleResponse = receiveResponseFromAESDK();
			// System.out.println(saleResponse);

		
			// System.out.println(saleResponse);

			Response_Parameters SaleParam = new Response_Parameters(saleResponse);
			String saleResult = SaleParam.getParameterValue("ResponseText");
			String ATicketNumber = SaleParam.getParameterValue("AurusPayTicketNum");
			String ATransactionID = SaleParam.getParameterValue("TransactionIdentifier");
			System.out.println("RWSale : " + ATransactionID + "    Card type   : "  + SaleParam.getParameterValue("CardType"));

			if (saleResult.equalsIgnoreCase("APPROVAL")) {

				// Refund Transactions

				String returnRequest = Return.Request("06", amount, ATicketNumber, ATransactionID);
				sendRequestToAESDK(returnRequest);
				String returnResponse = receiveResponseFromAESDK();
				Response_Parameters returnRes = new Response_Parameters(returnResponse);
				System.out.println("Void :   "+returnRes.getParameterValue("TransactionIdentifier") +  "    Card type :  "+ returnRes.getParameterValue("CardType"));
				
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println();

			}

		}

	}
	
	   @AfterMethod
		public void afterTransactionsOparations()
				throws UnknownHostException, IOException, InterruptedException, JDOMException {

			sendRequestToAESDK(Close.Request());
			receiveResponseFromAESDK();


		}

}
