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

import utilities.TransactionXL;

public class BaseClass {

	
	  protected String[] parameters = { "CardToken", "CardIdentifier", "CRMToken",
	  "CardEntryMode", "TransactionTypeCode", "TransactionSequenceNumber",
	  "CardType", "SubCardType", "TotalApprovedAmount", "ResponseText",
	  "ResponseCode", "TransactionIdentifier", "AurusPayTicketNum", "ApprovalCode",
	  "ProcessorToken", "AID" };
	 
	 
	 
	// Gift Card
	
	/*
	 * protected String[] parameters = { "CardToken", "CardIdentifier", "CRMToken",
	 * "CardEntryMode", "TransactionTypeCode", "TransactionSequenceNumber",
	 * "CardType", "SubCardType", "TotalApprovedAmount", "ResponseText",
	 * "ResponseCode", "TransactionIdentifier", "AurusPayTicketNum", "ApprovalCode",
	 * "ProcessorToken", "BalanceAmount"};
	 * 
	 */

	protected List<String> GCB_Parameters = new ArrayList<>(Arrays.asList(parameters));
	protected TransactionXL xl = new TransactionXL();

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
		// Thread.sleep(500);
	}

	public String receiveResponseFromAESDK() throws IOException, InterruptedException, JDOMException {
		InputStream inputStream = socket.getInputStream();
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		String response = in.readLine();

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
			return response;
		}

	}

}
