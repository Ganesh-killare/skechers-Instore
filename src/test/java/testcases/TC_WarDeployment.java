package testcases;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

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
import utilities.P_XL_Utility;
import utilities.Utils;

public class TC_WarDeployment extends BaseClass {
	static List<String> amount;
	String FILE_NAME = "_SkechersWarTesting";
	P_XL_Utility vXL = new P_XL_Utility();
	public static int DecisionVar;

	@BeforeMethod
	public void ticketDisplayAndGetCardBin() throws Exception, IOException, InterruptedException {

		String ticketRequest = TicketDisplay.request();
		sendRequestToAESDK(ticketRequest);
		// System.out.println(ticketRequest);
		amount = (List<String>) TicketDisplay.getTransactionAmount(ticketRequest);

		receiveResponseFromAESDK();

	}

	@Test(invocationCount = 36, priority = 1)
	public void testSale() throws Exception, IOException, InterruptedException {   

		String gcbRequest = GCB.Request(amount.get(2));
		// System.out.println(gcbRequest);
		sendRequestToAESDK(gcbRequest);
		String gcbResponse = receiveResponseFromAESDK();
		// System.out.println(gcbResponse);

		Response_Parameters GCBPrameter = new Response_Parameters(gcbResponse);
		List<String> gcbParameters = GCBPrameter.print_Response("GCB", parameters);
		xl.WriteGCBData(GCB_Parameters, gcbParameters);
		String result = GCBPrameter.getParameterValue("ResponseText");
		String CardName = GCBPrameter.getParameterValue("FirstName") + "  "
				+ GCBPrameter.getParameterValue("CardToken");

		if (result.equalsIgnoreCase("APPROVAL") || result.equalsIgnoreCase("VALIDATION")) {

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
			String ATicketNumber = SaleParam.getParameterValue("AurusPayTicketNum");
			String ATransactionID = SaleParam.getParameterValue("TransactionIdentifier");
			String Amount = SaleParam.getParameterValue("TransactionAmount");

			if (DecisionVar % 2 == 0) {
				List<String> data = Arrays.asList(ATransactionID, ATicketNumber, Amount, "06", CardName);
				vXL.writeDataForVoid(data);

			} else {
				List<String> data = Arrays.asList(ATransactionID, ATicketNumber, Amount, "02", CardName);
				vXL.writeDataForVoid(data);

			}

			Assert.assertEquals("APPROVAL", SaleParam.getParameterValue("ResponseText"));
			DecisionVar++;

		}

	}

	@Test(priority = 3, dataProvider = "VoidData", dataProviderClass = Utils.class)
	public void testAllVoidTransactions(String TransID, String AurusPayTicketNumber, String amount, String transType,
			String CardName) throws Exception {

		if (!amount.equalsIgnoreCase("0.00") && !amount.isEmpty()) {
			System.out.println("We are performed Return of card " + CardName);

			String returnRequest = Return.Request(transType, amount, AurusPayTicketNumber, TransID);
			sendRequestToAESDK(returnRequest);
			String returnResponse = receiveResponseFromAESDK();
			Response_Parameters returnRes = new Response_Parameters(returnResponse);
			if (transType.equalsIgnoreCase("02")) {
				List<String> returnData = returnRes.print_Response("Refund", parameters);
				returnData.add(1, "Refund");
				xl.writeTransactionReturnData(returnData);
			} else {
				List<String> VoidData = returnRes.print_Response("Void", parameters);
				VoidData.add(1, "Void");
				xl.writeTransactionReturnData(VoidData);

			}

		}
	}

	@AfterMethod
	public void afterTransactionsOparations()
			throws UnknownHostException, IOException, InterruptedException, JDOMException {

		sendRequestToAESDK(Close.Request());
		receiveResponseFromAESDK();

		xl.saveExcelFile(FILE_NAME);
		vXL.saveExcelFile();

	}
}
