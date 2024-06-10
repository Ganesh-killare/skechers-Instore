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
import requestbuilder.Gift;
import requestbuilder.Return;
import requestbuilder.TicketDisplay;
import responsevalidator.Response_Parameters;

public class TC_GiftCard extends BaseClass {  

	static List<String> amount;
	String FILE_NAME = "Gift_Skechers";   

	@BeforeMethod
	public void ticketDisplayAndGetCardBin() throws Exception, IOException, InterruptedException {

		String ticketRequest = TicketDisplay.request();
		sendRequestToAESDK(ticketRequest);
		// System.out.println(ticketRequest);
		amount = (List<String>) TicketDisplay.getTransactionAmount(ticketRequest);

	receiveResponseFromAESDK();
		// System.out.println(ticketResponse);
	

	}

	@Test(priority = 1)
	public void testGiftActivation() throws Exception, IOException {
		String gcbRequest = GCB.giftRequest(amount.get(2));
		// System.out.println(gcbRequest);
		sendRequestToAESDK(gcbRequest);
		String gcbResponse = receiveResponseFromAESDK();
		// System.out.println(gcbResponse);
	

		Response_Parameters GCBPrameter = new Response_Parameters(gcbResponse);
		List<String> gcbParameters = GCBPrameter.print_Response("GCB", Parameters);
		xl.WriteGCBData(giftParameters, gcbParameters);
		String result = GCBPrameter.getParameterValue("ResponseText");

		if (result.equalsIgnoreCase("APPROVAL")) {

			String giftRequest = Gift.Request(GCBPrameter.getParameterValue("CardToken"), amount, "11");

			sendRequestToAESDK(giftRequest);
		//	System.out.println(giftRequest);
			String giftResponse = receiveResponseFromAESDK();
			// System.out.println(giftResponse);

		
			// System.out.println(giftResponse);

			Response_Parameters giftParam = new Response_Parameters(giftResponse);
			List<String> giftData = giftParam.print_Response("Activation ", Parameters);
			giftData.add(1, " Activation ");
			xl.writeTransactionData(giftData);
			/*
			 * String saleResult = giftParam.getParameterValue("ResponseText"); String
			 * ATicketNumber = giftParam.getParameterValue("AurusPayTicketNum"); String
			 * ATransactionID = giftParam.getParameterValue("TransactionIdentifier");
			 */
		}
	}

	@Test(priority = 2)
	public void testGiftReload() throws Exception, IOException {
			String gcbRequest = GCB.giftRequest(amount.get(2));
		// System.out.println(gcbRequest);
		sendRequestToAESDK(gcbRequest);
		String gcbResponse = receiveResponseFromAESDK();
		// System.out.println(gcbResponse);
	

		Response_Parameters GCBPrameter = new Response_Parameters(gcbResponse);
		List<String> gcbParameters = GCBPrameter.print_Response("GCB", Parameters);
		xl.WriteGCBData(giftParameters, gcbParameters);
		String result = GCBPrameter.getParameterValue("ResponseText");

		if (result.equalsIgnoreCase("APPROVAL")) {

			String giftRequest = Gift.Request(GCBPrameter.getParameterValue("CardToken"), amount, "16");

			sendRequestToAESDK(giftRequest);
		//	System.out.println(giftRequest);
			String giftResponse = receiveResponseFromAESDK();
			// System.out.println(giftResponse);

		
			// System.out.println(giftResponse);

			Response_Parameters giftParam = new Response_Parameters(giftResponse);
			List<String> giftData = giftParam.print_Response("Reload ", Parameters);
			giftData.add(1, " Reload ");
			xl.writeTransactionData(giftData);
			/*
			 * String saleResult = giftParam.getParameterValue("ResponseText"); String
			 * ATicketNumber = giftParam.getParameterValue("AurusPayTicketNum"); String
			 * ATransactionID = giftParam.getParameterValue("TransactionIdentifier");
			 */
		}

	}

	@Test(priority = 3)
	public void testCheckBalance() throws Exception {
			String gcbRequest = GCB.giftRequest(amount.get(2));
		// System.out.println(gcbRequest);
		sendRequestToAESDK(gcbRequest);
		String gcbResponse = receiveResponseFromAESDK();
		// System.out.println(gcbResponse);
	

		Response_Parameters GCBPrameter = new Response_Parameters(gcbResponse);
		List<String> gcbParameters = GCBPrameter.print_Response("GCB", Parameters);
		xl.WriteGCBData(giftParameters, gcbParameters);
		String result = GCBPrameter.getParameterValue("ResponseText");

		if (result.equalsIgnoreCase("APPROVAL")) {

			String giftRequest = Gift.Request(GCBPrameter.getParameterValue("CardToken"), amount, "12");

			sendRequestToAESDK(giftRequest);
			// System.out.println(giftRequest);
			String giftResponse = receiveResponseFromAESDK();
		//	System.out.println(giftResponse);

		
			// System.out.println(giftResponse);

			Response_Parameters giftParam = new Response_Parameters(giftResponse);
			List<String> giftData = giftParam.print_Response("Balance Inquiry" + " ", Parameters);
			giftData.add(1, " Balance Inquiry ");
			xl.writeTransactionData(giftData);
			/*
			 * String saleResult = giftParam.getParameterValue("ResponseText"); String
			 * ATicketNumber = giftParam.getParameterValue("AurusPayTicketNum"); String
			 * ATransactionID = giftParam.getParameterValue("TransactionIdentifier");
			 */		}
	}

	@Test(priority = 4)
	public void testGiftRedeem() throws Exception {
			String gcbRequest = GCB.giftRequest(amount.get(2));
		// System.out.println(gcbRequest);
		sendRequestToAESDK(gcbRequest);
		String gcbResponse = receiveResponseFromAESDK();
		// System.out.println(gcbResponse);
	

		Response_Parameters GCBPrameter = new Response_Parameters(gcbResponse);
		List<String> gcbParameters = GCBPrameter.print_Response("GCB", Parameters);
		xl.WriteGCBData(giftParameters, gcbParameters);
		String result = GCBPrameter.getParameterValue("ResponseText");

		if (result.equalsIgnoreCase("APPROVAL")) {

			String giftRequest = Gift.Request(GCBPrameter.getParameterValue("CardToken"), amount, "18");

			sendRequestToAESDK(giftRequest);
			// System.out.println(giftRequest);
			String giftResponse = receiveResponseFromAESDK();
		//	System.out.println(giftResponse);

		
			// System.out.println(giftResponse);

			Response_Parameters giftParam = new Response_Parameters(giftResponse);
			List<String> giftData = giftParam.print_Response("Redeem" + " ", Parameters);
			giftData.add(1, "Redeem");
			xl.writeTransactionData(giftData);
			String saleResult = giftParam.getParameterValue("ResponseText");
			String ATicketNumber = giftParam.getParameterValue("AurusPayTicketNum");
			String ATransactionID = giftParam.getParameterValue("TransactionIdentifier");
			if (saleResult.equalsIgnoreCase("APPROVAL")) {

				// Refund Transactions
				
				  String returnRequest = Return.Request("06", amount, ATicketNumber,
				  ATransactionID); sendRequestToAESDK(returnRequest); String returnResponse =
				  receiveResponseFromAESDK(); Response_Parameters returnRes = new
				  Response_Parameters(returnResponse); List<String> returnData =
				  returnRes.print_Response("Void ", Parameters); returnData.add(1, "Void");
				  xl.writeTransactionData(returnData);
				 

			}
		}
	}
	public void testGiftCancelLast() throws Exception {
		String gcbRequest = GCB.giftRequest(amount.get(2));
		// System.out.println(gcbRequest);
		sendRequestToAESDK(gcbRequest);
		String gcbResponse = receiveResponseFromAESDK();
		// System.out.println(gcbResponse);
	
		
		Response_Parameters GCBPrameter = new Response_Parameters(gcbResponse);
		List<String> gcbParameters = GCBPrameter.print_Response("GCB", Parameters);
		xl.WriteGCBData(giftParameters, gcbParameters);
		String result = GCBPrameter.getParameterValue("ResponseText");
		
		if (result.equalsIgnoreCase("APPROVAL")) {
			
			String giftRequest = Gift.Request(GCBPrameter.getParameterValue("CardToken"), amount, "18");
			
			sendRequestToAESDK(giftRequest);
			// System.out.println(giftRequest);
			String giftResponse = receiveResponseFromAESDK();
			//	System.out.println(giftResponse);
			
		
			// System.out.println(giftResponse);
			
			Response_Parameters giftParam = new Response_Parameters(giftResponse);
			List<String> giftData = giftParam.print_Response("Redeem" + " ", Parameters);
			giftData.add(1, "Redeem");
			xl.writeTransactionData(giftData);
			String saleResult = giftParam.getParameterValue("ResponseText");
			String ATicketNumber = giftParam.getParameterValue("AurusPayTicketNum");
			String ATransactionID = giftParam.getParameterValue("TransactionIdentifier");
			if (saleResult.equalsIgnoreCase("APPROVAL")) {
				
				// Refund Transactions
				
				String giftCancelLast =  Return.cancelLastRequest(ATicketNumber, ATransactionID);
				sendRequestToAESDK(giftCancelLast);
				String returnResponse = receiveResponseFromAESDK();
				Response_Parameters returnRes = new Response_Parameters(returnResponse);
				List<String> returnData = returnRes.print_Response("CancelLast ", Parameters);
				returnData.add(1, "CancelLast");
				xl.writeTransactionData(returnData);
				
				
			}
		}
	}

	@Test(priority = 5)
	public void testGiftReturn() throws Exception {
			String gcbRequest = GCB.giftRequest(amount.get(2));
		// System.out.println(gcbRequest);
		sendRequestToAESDK(gcbRequest);
		String gcbResponse = receiveResponseFromAESDK();
		// System.out.println(gcbResponse);
	

		Response_Parameters GCBPrameter = new Response_Parameters(gcbResponse);
		List<String> gcbParameters = GCBPrameter.print_Response("GCB", Parameters);
		xl.WriteGCBData(giftParameters, gcbParameters);
		String result = GCBPrameter.getParameterValue("ResponseText");

		if (result.equalsIgnoreCase("APPROVAL")) {

			String giftRequest = Gift.Request(GCBPrameter.getParameterValue("CardToken"), amount, "19");

			sendRequestToAESDK(giftRequest);
			// System.out.println(giftRequest);
			String giftResponse = receiveResponseFromAESDK();
			//System.out.println(giftResponse);

		
			// System.out.println(giftResponse);

			Response_Parameters giftParam = new Response_Parameters(giftResponse);
			List<String> giftData = giftParam.print_Response("Return" + " ", Parameters);
			giftData.add(1, "Return");
			xl.writeTransactionData(giftData);
			
		
		}
	}

	@Test(priority = 6)
	public void testGiftCashOut() throws Exception {
			String gcbRequest = GCB.giftRequest(amount.get(2));
		// System.out.println(gcbRequest);
		sendRequestToAESDK(gcbRequest);
		String gcbResponse = receiveResponseFromAESDK();
		// System.out.println(gcbResponse);
	

		Response_Parameters GCBPrameter = new Response_Parameters(gcbResponse);
		List<String> gcbParameters = GCBPrameter.print_Response("GCB", Parameters);
		xl.WriteGCBData(giftParameters, gcbParameters);
		String result = GCBPrameter.getParameterValue("ResponseText");

		if (result.equalsIgnoreCase("APPROVAL")) {

			String giftRequest = Gift.Request(GCBPrameter.getParameterValue("CardToken"), amount, "21");

			sendRequestToAESDK(giftRequest);
			// System.out.println(giftRequest);
			String giftResponse = receiveResponseFromAESDK();
		//	System.out.println(giftResponse);

		
			// System.out.println(giftResponse);

			Response_Parameters giftParam = new Response_Parameters(giftResponse);
			List<String> giftData = giftParam.print_Response("CashOut" + " ", Parameters);
			giftData.add(1, " CashOut");
			xl.writeTransactionData(giftData);

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
