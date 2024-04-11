package testcases;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Scanner;

import org.jdom2.JDOMException;
import org.testng.Assert;
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
import utilities.Logger;

public class TC_TestCases extends BaseClass {    

	static List<String> amount;
	String FILE_NAME = "Skechers";

	@BeforeMethod
	public void ticketDisplayAndGetCardBin() throws Exception, IOException, InterruptedException {
	
		

		String ticketRequest = TicketDisplay.request();
		sendRequestToAESDK(ticketRequest);
		// System.out.println(ticketRequest);
		amount = (List<String>) TicketDisplay.getTransactionAmount(ticketRequest);

		String ticketResponse = receiveResponseFromAESDK();
		// System.out.println(ticketResponse);
		Logger.logRequestandRespons(ticketRequest, ticketResponse);

	}

	@Test(invocationCount = 60)  
	public void testRefundOfSale() throws Exception, IOException, InterruptedException {        

		String gcbRequest = GCB.Request(amount.get(2));
		// System.out.println(gcbRequest);
		sendRequestToAESDK(gcbRequest);
		String gcbResponse = receiveResponseFromAESDK();
		// System.out.println(gcbResponse);
		Logger.logRequestandRespons(gcbRequest, gcbResponse);

		Response_Parameters GCBPrameter = new Response_Parameters(gcbResponse);
		List<String> gcbParameters = GCBPrameter.print_Response("GCB", parameters);
		xl.WriteGCBData(GCB_Parameters, gcbParameters);
		String result = GCBPrameter.getParameterValue("ResponseText");

		if (result.equalsIgnoreCase("APPROVAL")) {   

			String salerequest = Sale.Request(GCBPrameter.getParameterValue("CardToken"), amount);

			sendRequestToAESDK(salerequest);
			// System.out.println(salerequest);
			String saleResponse = receiveResponseFromAESDK();
			// System.out.println(saleResponse);

			Logger.logRequestandRespons(salerequest, saleResponse);
			// System.out.println(saleResponse);

			Response_Parameters SaleParam = new Response_Parameters(saleResponse);
			List<String> saleData = SaleParam.print_Response(" Sale  ", parameters);
			saleData.add(1, "Sale");
			xl.writeTransactionData(saleData);
			String saleResult = SaleParam.getParameterValue("ResponseText");
			String ATicketNumber = SaleParam.getParameterValue("AurusPayTicketNum");
			String ATransactionID = SaleParam.getParameterValue("TransactionIdentifier");
			String cardType = SaleParam.getParameterValue("TransactionIdentifier");
			

			if (saleResult.equalsIgnoreCase("APPROVAL")) {
					

				// Refund Transactions

				String returnRequest = Return.Request("02", amount, ATicketNumber, ATransactionID);
				sendRequestToAESDK(returnRequest);
				String returnResponse = receiveResponseFromAESDK();
				Response_Parameters returnRes = new Response_Parameters(returnResponse);
				List<String> returnData = returnRes.print_Response("Refund", parameters);
				returnData.add(1, "Refund");
				xl.writeTransactionData(returnData);
				Logger.logRequestandRespons(returnRequest, returnResponse);

			}

		}

	}

	@Test(invocationCount = 500)
	public void testVoidOfSale() throws Exception, IOException, InterruptedException {         

		String gcbRequest = GCB.Request(amount.get(2));
		// System.out.println(gcbRequest);
		sendRequestToAESDK(gcbRequest);
		String gcbResponse = receiveResponseFromAESDK();
		// System.out.println(gcbResponse);
		Logger.logRequestandRespons(gcbRequest, gcbResponse);

		Response_Parameters GCBPrameter = new Response_Parameters(gcbResponse);
		List<String> gcbParameters = GCBPrameter.print_Response("GCB", parameters);
		xl.WriteGCBData(GCB_Parameters, gcbParameters);
		String result = GCBPrameter.getParameterValue("ResponseText");   

		if (result.equalsIgnoreCase("APPROVAL")) {

			String salerequest = Sale.Request(GCBPrameter.getParameterValue("CardToken"), amount);

			sendRequestToAESDK(salerequest);
			// System.out.println(salerequest);
			String saleResponse = receiveResponseFromAESDK();
			// System.out.println(saleResponse);

			Logger.logRequestandRespons(salerequest, saleResponse);
			// System.out.println(saleResponse);

			Response_Parameters SaleParam = new Response_Parameters(saleResponse);
			List<String> saleData = SaleParam.print_Response(" Sale  ", parameters);
			saleData.add(1, "Sale");
			xl.writeTransactionData(saleData);
			String saleResult = SaleParam.getParameterValue("ResponseText");
			String ATicketNumber = SaleParam.getParameterValue("AurusPayTicketNum");
			String ATransactionID = SaleParam.getParameterValue("TransactionIdentifier");

			
			  if (saleResult.equalsIgnoreCase("APPROVAL")) {
			  
			  // Refund Transactions
			  
			  String returnRequest = Return.Request("06", amount, ATicketNumber,
			  ATransactionID); sendRequestToAESDK(returnRequest); String returnResponse =
			  receiveResponseFromAESDK(); Response_Parameters returnRes = new
			  Response_Parameters(returnResponse); List<String> returnData =
			  returnRes.print_Response("Void", parameters); returnData.add(1, "Void");
			  xl.writeTransactionData(returnData);
			  Logger.logRequestandRespons(returnRequest, returnResponse);
			  
			  }
			 

		}

	}

	@Test(invocationCount = 500)
	public void testVoidOfRefundWithoutSale() throws Exception, IOException, InterruptedException {          
    
		String gcbRequest = GCB.Request(amount.get(2));
		// System.out.println(gcbRequest);
		sendRequestToAESDK(gcbRequest);
		String gcbResponse = receiveResponseFromAESDK();
		// System.out.println(gcbResponse);
		Logger.logRequestandRespons(gcbRequest, gcbResponse);

		Response_Parameters GCBPrameter = new Response_Parameters(gcbResponse);
		List<String> gcbParameters = GCBPrameter.print_Response("GCB", parameters);    
		xl.WriteGCBData(GCB_Parameters, gcbParameters);
		String result = GCBPrameter.getParameterValue("ResponseText");

		if (result.equalsIgnoreCase("APPROVAL")) {

			String salerequest = Sale.refundRequest(GCBPrameter.getParameterValue("CardToken"), amount);

			sendRequestToAESDK(salerequest);
			// System.out.println(salerequest);
			String saleResponse = receiveResponseFromAESDK();
			// System.out.println(saleResponse);

			Logger.logRequestandRespons(salerequest, saleResponse);
			// System.out.println(saleResponse);

			Response_Parameters SaleParam = new Response_Parameters(saleResponse);
			List<String> saleData = SaleParam.print_Response("Refund Without Sale  ", parameters);
			saleData.add(1, "Refund Without Sale ");
			xl.writeTransactionData(saleData);
			String saleResult = SaleParam.getParameterValue("ResponseText");
			String ATicketNumber = SaleParam.getParameterValue("AurusPayTicketNum");
			String ATransactionID = SaleParam.getParameterValue("TransactionIdentifier");
			
			

			if (saleResult.equalsIgnoreCase("APPROVAL")) {

				// Refund Transactions

				String returnRequest = Return.Request("06", amount, ATicketNumber, ATransactionID);
				sendRequestToAESDK(returnRequest);
				String returnResponse = receiveResponseFromAESDK();
				Response_Parameters returnRes = new Response_Parameters(returnResponse);
				List<String> returnData = returnRes.print_Response("Void", parameters);
				returnData.add(1, "Void");
				xl.writeTransactionData(returnData);
				Logger.logRequestandRespons(returnRequest, returnResponse);

			}

		}

	}

	@Test(invocationCount = 30)
	public void testCancelLast() throws Exception, IOException, InterruptedException {

		String gcbRequest = GCB.Request(amount.get(2));
		// System.out.println(gcbRequest);
		sendRequestToAESDK(gcbRequest);
		String gcbResponse = receiveResponseFromAESDK();
		// System.out.println(gcbResponse);
		Logger.logRequestandRespons(gcbRequest, gcbResponse);

		Response_Parameters GCBPrameter = new Response_Parameters(gcbResponse);
		List<String> gcbParameters = GCBPrameter.print_Response("GCB", parameters);
		xl.WriteGCBData(GCB_Parameters, gcbParameters);
		String result = GCBPrameter.getParameterValue("ResponseText");

		if (result.equalsIgnoreCase("APPROVAL")) {

			String salerequest = Sale.Request(GCBPrameter.getParameterValue("CardToken"), amount);

			sendRequestToAESDK(salerequest);
			// System.out.println(salerequest);
			String saleResponse = receiveResponseFromAESDK();
			// System.out.println(saleResponse);

			Logger.logRequestandRespons(salerequest, saleResponse);
			// System.out.println(saleResponse);

			Response_Parameters SaleParam = new Response_Parameters(saleResponse);
			List<String> saleData = SaleParam.print_Response("Sale ", parameters);
			saleData.add(1, " Sale ");
			xl.writeTransactionData(saleData);
			String saleResult = SaleParam.getParameterValue("ResponseText");
			String ATicketNumber = SaleParam.getParameterValue("AurusPayTicketNum");
			String ATransactionID = SaleParam.getParameterValue("TransactionIdentifier");

			if (saleResult.equalsIgnoreCase("APPROVAL")) {

				// Refund Transactions

				String returnRequest = Return.cancelLastRequest(ATicketNumber, ATransactionID);
				sendRequestToAESDK(returnRequest);
				String returnResponse = receiveResponseFromAESDK();
				Response_Parameters returnRes = new Response_Parameters(returnResponse);
				List<String> returnData = returnRes.print_Response("Cancel Last", parameters);
				returnData.add(1, "Cancel Last");
				xl.writeTransactionData(returnData);
				Logger.logRequestandRespons(returnRequest, returnResponse);

			}

		}

	}

	@Test()
	public void testCheckTransactions() throws Exception, IOException, InterruptedException {

		String salerequest = Sale.CheckRequest(amount);

		sendRequestToAESDK(salerequest);
        // System.out.println(salerequest);
		String saleResponse = receiveResponseFromAESDK();   
		// System.out.println(saleResponse);

		Logger.logRequestandRespons(salerequest, saleResponse);
		// System.out.println(saleResponse);

		Response_Parameters SaleParam = new Response_Parameters(saleResponse);
		List<String> saleData = SaleParam.print_Response(" Sale  ", parameters);
		saleData.add(1, "Sale");
		xl.writeTransactionData(saleData);
		String saleResult = SaleParam.getParameterValue("ResponseText");
		String ATicketNumber = SaleParam.getParameterValue("AurusPayTicketNum");
		String ATransactionID = SaleParam.getParameterValue("TransactionIdentifier");

		if (saleResult.equalsIgnoreCase("APPROVAL")) {

			// Refund Transactions

			String returnRequest = Return.Request("06", amount, ATicketNumber, ATransactionID);
			sendRequestToAESDK(returnRequest);
			String returnResponse = receiveResponseFromAESDK();
			Response_Parameters returnRes = new Response_Parameters(returnResponse);
			List<String> returnData = returnRes.print_Response("Void", parameters);
			returnData.add(1, "Void");
			xl.writeTransactionData(returnData);
			Logger.logRequestandRespons(returnRequest, returnResponse);

		}

	}

   @AfterMethod
	public void afterTransactionsOparations()
			throws UnknownHostException, IOException, InterruptedException, JDOMException {

		sendRequestToAESDK(Close.Request());
		receiveResponseFromAESDK();

		xl.saveExcelFile(FILE_NAME);

	}
}
