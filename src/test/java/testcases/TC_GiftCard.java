package testcases;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import org.jdom2.JDOMException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.BaseClass;
import reporting.BaseReport;
import requestbuilder.Close;
import requestbuilder.GCB;
import requestbuilder.Gift;
import requestbuilder.Return;
import requestbuilder.TicketDisplay;
import responsevalidator.Response_Parameters;
import utilities.ConfigReader;
import utilities.Utils;

public class TC_GiftCard extends BaseClass {

	static List<String> amount;
	String FILE_NAME = "Gift_Skechers";



	@Test(priority = 1)
	public void gift_Activation() throws Exception, IOException {
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
			// System.out.println(giftRequest);
			String giftResponse = receiveResponseFromAESDK();
			// System.out.println(giftResponse);

			// System.out.println(giftResponse);

			Response_Parameters giftParam = new Response_Parameters(giftResponse);
			List<String> giftData = giftParam.print_Response("Activation ", Parameters);
			if (ConfigReader.isReportingEnabled()) {
				System.out.println(ConfigReader.isReportingEnabled());
				BaseReport.Transaction_Reporting(giftData, giftRequest, giftResponse);
			}
			String transdata = Utils.convertListToString(giftData, ",");

			giftData.add(1, " Activation ");
			xl.writeTransactionData(giftData);

			// Assertions

			Assert.assertTrue(giftData.get(10).equals(approvalText) || giftData.get(10).equals(validationText),
					"We are not getting ResponseText as " + approvalText + " or " + validationText + " " + transdata);

		}
	}

	@Test(priority = 2)
	public void gift_Reload() throws Exception, IOException {
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
			// System.out.println(giftRequest);
			String giftResponse = receiveResponseFromAESDK();
			// System.out.println(giftResponse);

			// System.out.println(giftResponse);

			Response_Parameters giftParam = new Response_Parameters(giftResponse);
			List<String> giftData = giftParam.print_Response("Reload ", Parameters);

			if (ConfigReader.isReportingEnabled()) {
				System.out.println(ConfigReader.isReportingEnabled());
				BaseReport.Transaction_Reporting(giftData, giftRequest, giftResponse);
			}
			String transdata = Utils.convertListToString(giftData, ",");

			giftData.add(1, " Reload ");
			xl.writeTransactionData(giftData);

			Assert.assertTrue(giftData.get(10).equals(approvalText) || giftData.get(10).equals(validationText),
					"We are not getting ResponseText as " + approvalText + " or " + validationText + " " + transdata);
		}

	}

	@Test(priority = 3)
	public void Balance_Enquiry() throws Exception {
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
			// System.out.println(giftResponse);

			// System.out.println(giftResponse);

			Response_Parameters giftParam = new Response_Parameters(giftResponse);
			List<String> giftData = giftParam.print_Response("Balance Inquiry" + " ", Parameters);
			if (ConfigReader.isReportingEnabled()) {
				System.out.println(ConfigReader.isReportingEnabled());
				BaseReport.Transaction_Reporting(giftData, giftRequest, giftResponse);
			}
			String transdata = Utils.convertListToString(giftData, ",");

			giftData.add(1, " Balance Inquiry ");
			xl.writeTransactionData(giftData);

			Assert.assertTrue(giftData.get(10).equals(approvalText) || giftData.get(10).equals(validationText),
					"We are not getting ResponseText as " + approvalText + " or " + validationText + " " + transdata);
		}
	}

	@Test(priority = 4)
	public void Gift_Redeem() throws Exception {
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
			// System.out.println(giftResponse);

			// System.out.println(giftResponse);

			Response_Parameters giftParam = new Response_Parameters(giftResponse);
			List<String> giftData = giftParam.print_Response("Redeem" + " ", Parameters);
			if (ConfigReader.isReportingEnabled()) {
				System.out.println(ConfigReader.isReportingEnabled());
				BaseReport.Transaction_Reporting(giftData, giftRequest, giftResponse);
			}
			String transdata = Utils.convertListToString(giftData, ",");

			giftData.add(1, "Redeem");
			xl.writeTransactionData(giftData);

			Assert.assertTrue(giftData.get(10).equals(approvalText) || giftData.get(10).equals(validationText),
					"We are not getting ResponseText as " + approvalText + " or " + validationText + " " + transdata);

			String saleResult = giftParam.getParameterValue("ResponseText");
			String ATicketNumber = giftParam.getParameterValue("AurusPayTicketNum");
			String ATransactionID = giftParam.getParameterValue("TransactionIdentifier");
			if (saleResult.equalsIgnoreCase("APPROVAL")) {

				// Refund Transactions

				String returnRequest = Return.Request("06", amount, ATicketNumber, ATransactionID);
				sendRequestToAESDK(returnRequest);
				String returnResponse = receiveResponseFromAESDK();
				Response_Parameters returnRes = new Response_Parameters(returnResponse);
				List<String> returnData = returnRes.print_Response("Void ", Parameters);
				if (ConfigReader.isReportingEnabled()) {
					System.out.println(ConfigReader.isReportingEnabled());
					BaseReport.Transaction_Reporting(returnData, returnRequest, returnResponse);
				}
				String Voiddata = Utils.convertListToString(giftData, ",");

				returnData.add(1, "Void");
				xl.writeTransactionData(returnData);
				Assert.assertTrue(giftData.get(10).equals(approvalText) || giftData.get(10).equals(validationText),
						"We are not getting ResponseText as " + approvalText + " or " + validationText + " " + Voiddata);

			}
		}
	}

	public void Gift_CancelLast() throws Exception {
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
			// System.out.println(giftResponse);

			// System.out.println(giftResponse);

			Response_Parameters giftParam = new Response_Parameters(giftResponse);
			List<String> giftData = giftParam.print_Response("Redeem" + " ", Parameters);
			if (ConfigReader.isReportingEnabled()) {
				System.out.println(ConfigReader.isReportingEnabled());
				BaseReport.Transaction_Reporting(giftData, giftRequest, giftResponse);
			}
			String transdata = Utils.convertListToString(giftData, ",");
			
			giftData.add(1, "Redeem");
			xl.writeTransactionData(giftData);
			
			Assert.assertTrue(giftData.get(10).equals(approvalText) || giftData.get(10).equals(validationText),
					"We are not getting ResponseText as " + approvalText + " or " + validationText + " " + transdata);
			
			String saleResult = giftParam.getParameterValue("ResponseText");
			String ATicketNumber = giftParam.getParameterValue("AurusPayTicketNum");
			String ATransactionID = giftParam.getParameterValue("TransactionIdentifier");
			if (saleResult.equalsIgnoreCase("APPROVAL")) {

				// Refund Transactions

				String giftCancelLast = Return.cancelLastRequest(ATicketNumber, ATransactionID);
				sendRequestToAESDK(giftCancelLast);
				String returnResponse = receiveResponseFromAESDK();
				Response_Parameters returnRes = new Response_Parameters(returnResponse);
				List<String> returnData = returnRes.print_Response("CancelLast ", Parameters);
				if (ConfigReader.isReportingEnabled()) {
					System.out.println(ConfigReader.isReportingEnabled());
					BaseReport.Transaction_Reporting(returnData, giftCancelLast, returnResponse);
				}
				String Voiddata = Utils.convertListToString(returnData, ",");
				returnData.add(1, "CancelLast");
				xl.writeTransactionData(returnData);
				Assert.assertTrue(giftData.get(10).equals(approvalText) || giftData.get(10).equals(validationText),
						"We are not getting ResponseText as " + approvalText + " or " + validationText + " " + Voiddata);

			}
		}
	}

	@Test(priority = 5)
	public void Gift_Return() throws Exception {
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
			// System.out.println(giftResponse);

			// System.out.println(giftResponse);

			Response_Parameters giftParam = new Response_Parameters(giftResponse);
			List<String> giftData = giftParam.print_Response("Return" + " ", Parameters);
			
			if (ConfigReader.isReportingEnabled()) {
				System.out.println(ConfigReader.isReportingEnabled());
				BaseReport.Transaction_Reporting(giftData, giftRequest, giftResponse);
			}
			String transdata = Utils.convertListToString(giftData, ",");
			
			giftData.add(1, "Return");
			xl.writeTransactionData(giftData);

			Assert.assertTrue(giftData.get(10).equals(approvalText) || giftData.get(10).equals(validationText),
					"We are not getting ResponseText as " + approvalText + " or " + validationText + " " + transdata);

		}
	}

	@Test(priority = 6)
	public void gift_CashOut() throws Exception {
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
			// System.out.println(giftResponse);

			// System.out.println(giftResponse);

			Response_Parameters giftParam = new Response_Parameters(giftResponse);
			List<String> giftData = giftParam.print_Response("CashOut" + " ", Parameters);
			
			if (ConfigReader.isReportingEnabled()) {
				System.out.println(ConfigReader.isReportingEnabled());
				BaseReport.Transaction_Reporting(giftData, giftRequest, giftResponse);
			}
			String transdata = Utils.convertListToString(giftData, ",");
			
			giftData.add(1, " CashOut");
			xl.writeTransactionData(giftData);

			Assert.assertTrue(giftData.get(10).equals(approvalText) || giftData.get(10).equals(validationText),
					"We are not getting ResponseText as " + approvalText + " or " + validationText + " " + transdata);
		}
	}

	@AfterMethod
	public void afterTransactionsOparations()
			throws UnknownHostException, IOException, InterruptedException, JDOMException {

		sendRequestToAESDK(Close.Request());
		receiveResponseFromAESDK();
		String className = this.getClass().getSimpleName();

		xl.saveExcelFile(FILE_NAME + "_" + className);

	}
}
