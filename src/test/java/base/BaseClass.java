package base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import reporting.BaseReport;
import requestbuilder.Close;
import requestbuilder.GCB;
import requestbuilder.Return;
import requestbuilder.Sale;
import requestbuilder.TicketDisplay;
import responsevalidator.LogResponse;
import responsevalidator.Response_Parameters;
import utilities.ConfigReader;
import utilities.Logger;
import utilities.TransactionXL;
import utilities.Utils;

public class BaseClass {
	
	
	@BeforeSuite
	public void beforeSuite() {
		System.out.println("Reporting status : "+ConfigReader.isReportingEnabled());
	}

// Normal Use 

	protected String[] parameters = { "CardToken", "CardIdentifier", "CRMToken", "CardEntryMode", "TransactionTypeCode",
			"TransactionSequenceNumber", "CardType", "SubCardType", "TotalApprovedAmount", "ResponseText",
			"ResponseCode", "TransactionIdentifier", "AurusPayTicketNum", "ApprovalCode", "ProcessorToken", "AID" }; // AID

	// Gift Card

	protected String[] Parameters = { "CardToken", "CardIdentifier", "CRMToken", "CardEntryMode", "TransactionTypeCode",
			"TransactionSequenceNumber", "CardType", "SubCardType", "TotalApprovedAmount", "ResponseText",
			"ResponseCode", "TransactionIdentifier", "AurusPayTicketNum", "ApprovalCode", "ProcessorToken",
			"BalanceAmount" };

	protected List<String> GCB_Parameters = new ArrayList<>(Arrays.asList(parameters));
	protected List<String> giftParameters = new ArrayList<>(Arrays.asList(Parameters));
	protected TransactionXL xl = new TransactionXL();
	protected String approvalText = "APPROVAL";
	protected String validationText = "VALIDATION";

//	private String serverAddress = "10.190.10.205";
	private String serverAddress = getHostIP();

	private int serverPort = 15583;
	PrintWriter out = null;
	Socket socket = null;

	private static String getHostIP() {
		InetAddress localhost = null;
		try {
			localhost = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {

			e.printStackTrace();
		}
		String ipAddress = localhost.getHostAddress();
		return ipAddress;
	}

	public void sendRequestToAESDK(String data) throws UnknownHostException, IOException, InterruptedException {
		socket = new Socket(serverAddress, serverPort);
		OutputStream outputStream = socket.getOutputStream();
		out = new PrintWriter(outputStream, true);
		out.println(data);
		Logger.logData(data);

	}

	public String receiveResponseFromAESDK() throws IOException, InterruptedException, JDOMException {
		InputStream inputStream = socket.getInputStream();
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		StringBuilder responseBuilder = new StringBuilder();
		String line;
		while ((line = in.readLine()) != null) {
			responseBuilder.append(line);
		}
		String response = responseBuilder.toString().trim(); // Trim the response

		Logger.logData(response);

		try {
			// Parse the XML string
			SAXBuilder saxBuilder = new SAXBuilder();
			Document document = saxBuilder.build(new StringReader(response));

			// Create a custom format for pretty printing
			Format format = Format.getPrettyFormat();

			// Create an XMLOutputter with the custom format
			XMLOutputter xmlOutput = new XMLOutputter(format);

			// Convert the document to a string with pretty formatting
			StringWriter stringWriter = new StringWriter();
			xmlOutput.output(document, stringWriter);

			return stringWriter.toString();
		} catch (Exception e) {
			System.err.println("Error parsing XML: " + e.getMessage());
			e.printStackTrace(); // Log the full stack trace for debugging
			return null; // Return null or handle the error as needed
		}
	}

	public static List<String> amount;
	public String FILE_NAME = "Skechers";

	@BeforeMethod
	public void ticketDisplay() throws Exception, IOException, InterruptedException {

		String ticketRequest = TicketDisplay.request();
		sendRequestToAESDK(ticketRequest);
		// System.out.println(ticketRequest);
		amount = (List<String>) TicketDisplay.getTransactionAmount(ticketRequest);

		receiveResponseFromAESDK();
		// System.out.println(ticketResponse);

	}

	public List<String> performGCB(String amount) throws Exception {

		List<String> gcbResult = new ArrayList<String>();
		try {

			String gcbRequest = GCB.Request(amount);
			// System.out.println(gcbRequest);
			sendRequestToAESDK(gcbRequest);
			String gcbResponse = receiveResponseFromAESDK();
			// System.out.println(gcbResponse);

			Response_Parameters GCBPrameter = new Response_Parameters(gcbResponse);

			List<String> gcbParameters = GCBPrameter.print_Response("GCB", parameters);
			xl.WriteGCBData(GCB_Parameters, gcbParameters);
			gcbResult.add(GCBPrameter.getParameterValue("ResponseText"));
			gcbResult.add(GCBPrameter.getParameterValue("CardToken"));
			if(!gcbResult.get(0).equalsIgnoreCase(validationText) || !gcbResult.get(0).equalsIgnoreCase(approvalText) ) {
				Thread.sleep(5000);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return gcbResult;
	}

	public List<String> performSaleTransaction(List<String> amount) throws Exception {

		List<String> saleResult = new ArrayList<String>();
		try {

			List<String> result = performGCB(amount.get(2));

			if (result.get(0).equalsIgnoreCase(approvalText) || result.get(0).equalsIgnoreCase(validationText)) {

				String salerequest = Sale.Request(result.get(1), amount);

				sendRequestToAESDK(salerequest);
				// System.out.println(salerequest);
				String saleResponse = receiveResponseFromAESDK();
				// System.out.println(saleResponse);

				// System.out.println(saleResponse);

				Response_Parameters SaleParam = new Response_Parameters(saleResponse);
				List<String> saleData = SaleParam.print_Response(" Sale  ", parameters);

				// Reporting
				
				if(ConfigReader.isReportingEnabled()) {
				 
				BaseReport.Transaction_Reporting(saleData, salerequest, saleResponse);
				}
				
				String transdata = Utils.convertListToString(saleData, ",");

				saleData.add(1, "Sale");
				xl.writeTransactionData(saleData);

				saleResult.add(SaleParam.getParameterValue("ResponseText"));
				saleResult.add(SaleParam.getParameterValue("TotalApprovedAmount"));
				saleResult.add(SaleParam.getParameterValue("AurusPayTicketNum"));
				saleResult.add(SaleParam.getParameterValue("TransactionIdentifier"));

				// Assertions
				// Assertions

				Assert.assertTrue(saleResult.get(0).equals(approvalText) || saleResult.get(0).equals(validationText),
						"We are not getting ResponseText as " + approvalText + " or " + validationText + " "
								+ transdata);
			}
		} catch (Exception e) {
			System.out.println("We are not able to performe sale transaction" + e);

		}
		if (!saleResult.isEmpty()) {
			return saleResult;
		} else {

			return null;
		}

	}

	public List<String> performRefundWithoutSaleTransaction(List<String> amount) throws Exception {

		List<String> saleResult = new ArrayList<String>();
		try {
			List<String> result = performGCB(amount.get(2));

			if (result.get(0).equalsIgnoreCase(approvalText) || result.get(0).equalsIgnoreCase(validationText)) {

				String salerequest = Sale.refundRequest(result.get(1), amount);

				sendRequestToAESDK(salerequest);
				// System.out.println(salerequest);
				String saleResponse = receiveResponseFromAESDK();
				// System.out.println(saleResponse);

				// System.out.println(saleResponse);

				Response_Parameters SaleParam = new Response_Parameters(saleResponse);
				List<String> saleData = SaleParam.print_Response("RefundWithoutSale", parameters);

				// Reporting
				if(ConfigReader.isReportingEnabled()) {
				 
				BaseReport.Transaction_Reporting(saleData, salerequest, saleResponse);
				}
				String transdata = Utils.convertListToString(saleData, ",");

				saleData.add(1, "RefundWithoutSale");
				xl.writeTransactionData(saleData);

				saleResult.add(SaleParam.getParameterValue("ResponseText"));
				saleResult.add(SaleParam.getParameterValue("TotalApprovedAmount"));
				saleResult.add(SaleParam.getParameterValue("AurusPayTicketNum"));
				saleResult.add(SaleParam.getParameterValue("TransactionIdentifier"));

				// Assertions

				Assert.assertTrue(saleResult.get(0).equals(approvalText) || saleResult.get(0).equals(validationText),
						"We are not getting ResponseText as " + approvalText + " or " + validationText + " "
								+ transdata);

			}
		} catch (Exception e) {
			System.out.println("We are not able to performe sale transaction");

		}
		if (!saleResult.isEmpty()) {
			return saleResult;
		} else {

			return null;
		}

	}

	public List<String> performCheckSaleTransaction(List<String> amount)
			throws UnknownHostException, IOException, InterruptedException {
		List<String> saleResult = new ArrayList<String>();

		try {
			String salerequest = Sale.CheckRequest(amount);

			sendRequestToAESDK(salerequest);
			// System.out.println(salerequest);
			String saleResponse = receiveResponseFromAESDK();

			Response_Parameters SaleParam = new Response_Parameters(saleResponse);
			List<String> saleData = SaleParam.print_Response(" Sale  ", parameters);
			saleData.add(1, "Sale");
			xl.writeTransactionData(saleData);

			saleResult.add(SaleParam.getParameterValue("ResponseText"));
			saleResult.add(SaleParam.getParameterValue("TotalApprovedAmount"));
			saleResult.add(SaleParam.getParameterValue("AurusPayTicketNum"));
			saleResult.add(SaleParam.getParameterValue("TransactionIdentifier"));
			String ResponseCode = SaleParam.getParameterValue("TransactionIdentifier");

			// Assertions
			/*
			 * Assert.assertTrue(saleResult.get(0).equals(approvalText) ||
			 * saleResult.get(0).equals(validationText),
			 * "We are not getting ResponseText as " + approvalText + " or " +
			 * validationText + " "+ transdata );
			 */
		} catch (Exception e) {
			// TODO: handle exception
		}
		return saleResult;

	}

	public void performRefundTransaction(List<String> saleData) throws Exception, IOException, InterruptedException {
		// Refund Transactions
		try {
			if (!saleData.get(1).equalsIgnoreCase("0.00")) {
				String returnRequest = Return.Request("02", saleData.get(1), saleData.get(2), saleData.get(3));
				sendRequestToAESDK(returnRequest);
				String returnResponse = receiveResponseFromAESDK();
				Response_Parameters returnRes = new Response_Parameters(returnResponse);
				List<String> returnData = returnRes.print_Response("Refund", parameters);

				if(ConfigReader.isReportingEnabled()) {
				BaseReport.Transaction_Reporting(returnData, returnRequest, returnResponse);
				}
				String transdata = Utils.convertListToString(saleData, ",");
				

				returnData.add(1, "Refund");
				xl.writeTransactionData(returnData);

				// Assertions
				String responseText = returnRes.getParameterValue("ResponseText");
				Assert.assertTrue(responseText.equals(approvalText) || responseText.equals(validationText),
						"We are not getting ResponseText as " + approvalText + " or " + validationText + " "
								+ transdata);
			}
		} catch (Exception e) {
			System.out.println("We are not able to perform refund " + e);
		}

	}

	public void performVoidTransaction(List<String> saleData) throws Exception, IOException, InterruptedException {
		// Refund Transactions
		try {
			if (!saleData.get(1).equalsIgnoreCase("0.00")) {
				String returnRequest = Return.Request("06", saleData.get(1), saleData.get(2), saleData.get(3));
				sendRequestToAESDK(returnRequest);
				String returnResponse = receiveResponseFromAESDK();
				Response_Parameters returnRes = new Response_Parameters(returnResponse);
				List<String> returnData = returnRes.print_Response("Void", parameters);
				
				if(ConfigReader.isReportingEnabled()) {
				BaseReport.Transaction_Reporting(returnData, returnRequest, returnResponse);
				}
				String transdata = Utils.convertListToString(saleData, ",");

				returnData.add(1, "Void");
				xl.writeTransactionData(returnData);

				// Assertions
				String responseText = returnRes.getParameterValue("ResponseText");
				Assert.assertTrue(responseText.equals(approvalText) || responseText.equals(validationText),
						"We are not getting ResponseText as " + approvalText + " or " + validationText + " "
								+ transdata);
			}
		} catch (Exception e) {
			System.out.println("We are not able to perform refund ");
		}

	}

	public void PerformCancelLast(List<String> saleData) {
		try {

			String returnRequest = Return.cancelLastRequest(saleData.get(2), saleData.get(3));
			sendRequestToAESDK(returnRequest);
			String returnResponse = receiveResponseFromAESDK();
			Response_Parameters returnRes = new Response_Parameters(returnResponse);
			List<String> returnData = returnRes.print_Response("Cancel Last", parameters);
			returnData.add(1, "Cancel Last");
			xl.writeTransactionData(returnData);
		} catch (Exception e) {
			// System.out.println(e);
		}
	}


}
