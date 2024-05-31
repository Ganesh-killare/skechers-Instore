package requestbuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.github.javafaker.Faker;

import responsevalidator.Response_Parameters;

public class TicketDisplay {
	

	public static List<String>  getTransactionAmount(String ticketDisplyRequest) throws Exception {
		Properties properties = new Properties();
		try (FileInputStream input = new FileInputStream("config.properties")) {
			properties.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String gcbAmount = properties.getProperty("amount");
		
		
		
		List<String> amounts = new ArrayList<String>();

		Response_Parameters rs = new Response_Parameters(ticketDisplyRequest);
		String TransTotalAmount = rs.getParameterValue("TransTotalAmount");
		String TransTaxAmount = rs.getParameterValue("TaxAmount");
		double ProductTotalAmount =   Double.valueOf(TransTotalAmount) - Double.valueOf(TransTaxAmount) ;
		String FProductTotalAmount = String.valueOf(ProductTotalAmount);
		Double FtransTotalAmount = ProductTotalAmount + Double.valueOf(TransTaxAmount);
		
		
	
		if(gcbAmount.equalsIgnoreCase("0.00")) {
			amounts.add(FProductTotalAmount+"0");
			amounts.add(TransTaxAmount);
			amounts.add(String.valueOf(FtransTotalAmount)+"0");
		}
		else {
			amounts.add(FProductTotalAmount);
			amounts.add(TransTaxAmount);
			amounts.add(String.valueOf(FtransTotalAmount));
		}
	//	System.out.println(amounts);

		return amounts;
	}


    public static String request() {
    	String FTaxdAmount ;
    	String FDiscountedAmount ;
    	String amount ;
    	  Faker faker = new Faker();
    	
    	
    	Properties properties = new Properties();
		try (FileInputStream input = new FileInputStream("config.properties")) {
			properties.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String gcbAmount = properties.getProperty("amount");
		if(gcbAmount.equalsIgnoreCase("0.00")) {
     
           int totalAmount = faker.random().nextInt(30, 39);
        
        
		int discountAmount = (totalAmount * 20) / 100;
		int discountedAmount = totalAmount - discountAmount;
		int taxAmount = (discountedAmount * 20) / 100;
		int totalTransAmount = discountedAmount + taxAmount;


	     FTaxdAmount = String.valueOf(taxAmount);
		 FDiscountedAmount = String.valueOf(discountAmount);
		 amount = String.valueOf(totalTransAmount)+".00";
		}
		else
		{
			FTaxdAmount = "0";	
			FDiscountedAmount = "0";
			amount = gcbAmount ;
		}
		
		
   
   
        Element cctTicketDisplayRequest = new Element("CCTTicketDisplayRequest");
        cctTicketDisplayRequest.addContent(new Element("POSID").setText("01"));
        cctTicketDisplayRequest.addContent(new Element("ADSDKSpecVer").setText("6.13.0"));
        cctTicketDisplayRequest.addContent(new Element("DisplayFlag").setText("07"));
        cctTicketDisplayRequest.addContent(new Element("AllowCardReader").setText("N"));
        cctTicketDisplayRequest.addContent(new Element("TransTotalDiscountAmt").setText(FDiscountedAmount+".00"));
        cctTicketDisplayRequest.addContent(new Element("TransTotalQuantity").setText("0"));
        cctTicketDisplayRequest.addContent(new Element("TaxAmount").setText(FTaxdAmount+".00"));
        cctTicketDisplayRequest.addContent(new Element("ServicesTotalAmount").setAttribute("nil", "true"));
        cctTicketDisplayRequest.addContent(new Element("TransTotalAmount").setText(amount));
        cctTicketDisplayRequest.addContent(createTicketProductData(faker));

        Document doc = new Document(cctTicketDisplayRequest);
        Format format = Format.getPrettyFormat();
        format.setOmitDeclaration(true);
        XMLOutputter xmlOutput = new XMLOutputter(format);
        return xmlOutput.outputString(doc);
    }

    public static Element createTicketProductData(Faker faker) {
        Element ticketProductData = new Element("TicketProductData");
        ticketProductData.addContent(new Element("TicketCount").setText("1"));
        ticketProductData.addContent(createTickets(faker));

        return ticketProductData;
    }

    public static Element createTickets(Faker faker) {
    	
    	Date now = new Date();
		SimpleDateFormat invoiceDateFormatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	

		String formattedDateTime = invoiceDateFormatter.format(now);
	
		
		String TicketNumber = "00001"+formattedDateTime ;
		
		//00000 20220 22 11 23 19 0 117 
    	
        Element tickets = new Element("Tickets");
        Element ticket = new Element("Ticket");
        ticket.addContent(new Element("TicketNumber").setText(TicketNumber));
        ticket.addContent(new Element("ProductCount").setText("9"));
        ticket.addContent(createProducts(faker));

        tickets.addContent(ticket);
        return tickets;
    }

    public static Element createProducts(Faker faker) {
        Element products = new Element("Products");

        for (int i = 1; i <= 9; i++) {
            Element product = new Element("Product");
            product.addContent(new Element("ProductID").setText(String.valueOf(i)));
            product.addContent(new Element("ProductName").setText("SKECHERS KA JUTTA"));
            product.addContent(new Element("ProductTypeIndicator").setText("0"));
            product.addContent(new Element("ServiceLevel").setText("F"));
            product.addContent(new Element("ProductStatementDescriptor").setText(faker.lorem().sentence()));

            product.addContent(new Element("UnitOfMeasure").setText("O"));
            product.addContent(new Element("ProductDiscountFlag").setText("0"));
            product.addContent(new Element("ProductDiscount").setAttribute("true", "20%"));
            product.addContent(new Element("Quantity").setText("1"));
            product.addContent(new Element("Price").setAttribute("nil", ""));
            product.addContent(new Element("ProductTypeFlag"));

            products.addContent(product);
        }

        return products;
    }
}
