package requestbuilder;

import java.io.StringWriter;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.github.javafaker.Faker;

import utilities.Utils;

public class Gift {
	static Faker faker = new Faker();

	public static String Request(String cardToken , List<String> amt,  String transType) {
	
		/*
		 * if(transType.equalsIgnoreCase("17")) { amt.clear(); amt.add("0.00");
		 * amt.add("0.00"); amt.add("0.00"); amt.add("0.00"); }
		 */
	  	
		String transactionAmount = amt.get(2)+"~"+amt.get(2) ; 
		
		
		/*
		 * if(transactionAmount.equalsIgnoreCase("45.00")) { transactionAmount= "10.00";
		 * }
		 */

		// Date & Time with Invoice Number


		String formattedTime =	Utils.generateDateTimeIPHostName().get(0);
		String finalDate =	Utils.generateDateTimeIPHostName().get(1);
		/*
		 * String IP = Utils.generateDateTimeIPHostName().get(2); String HostName =
		 * Utils.generateDateTimeIPHostName().get(3);
		 */
		try {
			// Load the XML file
			SAXBuilder saxBuilder = new SAXBuilder();
		
			Document doc = saxBuilder.build("./src\\test\\resources\\cctRequests\\gift.xml");

			// Get the root element (TransRequest)
			Element root = doc.getRootElement();

			// Modify the parameters
			root.getChild("TransAmountDetails").getChild("ProductTotalAmount").setText(amt.get(0));
			root.getChild("TransAmountDetails").getChild("TaxAmount").setText("0.00");
			root.getChild("TransAmountDetails").getChild("TenderAmount").setText("0.00");
			root.getChild("TransAmountDetails").getChild("TransactionTotal").setText(transactionAmount);
			root.getChild("CardToken").setText(cardToken);
			root.getChild("TransactionType").setText(transType);
			root.getChild("TransactionDate").setText(finalDate);
			root.getChild("TransactionTime").setText(formattedTime);
		//	root.getChild("POSIP").setText(IP);
		//	root.getChild("POSClientName").setText(HostName);

			Format format = Format.getPrettyFormat();
			format.setOmitDeclaration(true);

			// Convert the modified XML to a string with the custom format
			XMLOutputter xmlOutput = new XMLOutputter(format);
			StringWriter stringWriter = new StringWriter();
			xmlOutput.output(doc, stringWriter);

			return stringWriter.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
