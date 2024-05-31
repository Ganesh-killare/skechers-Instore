package testcases;

import java.io.IOException;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import base.BaseClass;
import requestbuilder.GCB;
import requestbuilder.Return;
import requestbuilder.Sale;
import requestbuilder.TicketDisplay;
import responsevalidator.Response_Parameters;

public class DemoSandBox extends BaseClass {
	static List<String> amount;
	String FILE_NAME = "Skechers";

	@BeforeClass
	public void ticketDisplayAndGetCardBin() throws Exception, IOException, InterruptedException {

		String ticketRequest = TicketDisplay.request();
		sendRequestToAESDK(ticketRequest);
		// System.out.println(ticketRequest);
		amount = (List<String>) TicketDisplay.getTransactionAmount(ticketRequest);
		System.out.println("Amount is : " + amount.get(2));

	 receiveResponseFromAESDK();
		

	}

	@Test(priority = 1)
	public void testRefundOfSale() throws Exception, IOException, InterruptedException {

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

			
				String RefundResult = returnRes.getParameterValue("ResponseText");
				String RATicketNumber = returnRes.getParameterValue("AurusPayTicketNum");
				String RATransactionID = returnRes.getParameterValue("TransactionIdentifier");

				if (RefundResult.contentEquals("APPROVAL")) {
					String VoidRequest = Return.Request("06", amount, RATicketNumber, RATransactionID);
					sendRequestToAESDK(VoidRequest);
					String VoidResponse = receiveResponseFromAESDK();
					Response_Parameters VoidRes = new Response_Parameters(VoidResponse);
					List<String> VoidData = VoidRes.print_Response("Void", parameters);

				

				}

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
		List<String> gcbParameters = GCBPrameter.print_Response("GCB", parameters);
		xl.WriteGCBData(GCB_Parameters, gcbParameters);
		String result = GCBPrameter.getParameterValue("ResponseText");

		if (result.equalsIgnoreCase("APPROVAL")) {

			String salerequest = Sale.Request(GCBPrameter.getParameterValue("CardToken"), amount);

			sendRequestToAESDK(salerequest);
			// System.out.println(salerequest);
			String saleResponse = receiveResponseFromAESDK();
			// System.out.println(saleResponse);

			
			// System.out.println(saleResponse);

			Response_Parameters SaleParam = new Response_Parameters(saleResponse);
			List<String> saleData = SaleParam.print_Response(" Sale  ", parameters);
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
			List<String> saleData = SaleParam.print_Response("Refund Without Sale  ", parameters);

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

			

			}

		}

	}

}
