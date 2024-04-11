package responsevalidator;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

public class Response_Parameters {

    private Document document;

    public Response_Parameters(String xml) throws Exception {
        SAXBuilder saxBuilder = new SAXBuilder();
        document = saxBuilder.build(new StringReader(xml));
    }

    public String getParameterValue(String elementName) {
        try { 
            return getElementValue(document.getRootElement(), elementName);
        } catch (Exception e) {
            return null;
        }
    }

    private static String getElementValue(Element element, String elementName) {
        if (element == null) {
            return null;
        }

        if (element.getName().equals(elementName)) {  
            return element.getText();
        }

        List<Element> children = element.getChildren();
        for (Element child : children) {
            String result = getElementValue(child, elementName);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    public List<String> print_Response(String trans, String[] parameters) {
        System.out.print(trans + " : ");
        List<String> GCB_Response = new ArrayList<>();
        for (int i = 0; i <= parameters.length - 1; i++) {
            String GCBParameters = getParameterValue(parameters[i]);
            if(i==15 && trans.contains("Sale")) {
            	GCBParameters= 	getAIDContent();
            	 GCB_Response.add(GCBParameters);
            	
            }
            GCB_Response.add(GCBParameters);
        }
        System.out.println(GCB_Response);
        return GCB_Response;
    }
    
    
    public List<String> cardData(){
    	
		return null;
    	
    }
    
    // Method to extract content within <Line>AID:A0000000041010</Line>
    public String getAIDContent() {
        try {
            Element root = document.getRootElement();
            Element additionalReceiptInfo = root.getChild("TransDetailsData")
                                                .getChild("TransDetailData")
                                                .getChild("AdditionalReceiptInfo");
            List<Element> lines = additionalReceiptInfo.getChildren("Line");
            for (Element line : lines) {
                String content = line.getTextNormalize();
                if (content.startsWith("AID:")) {
                    return content;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "AID Not Receive ";
    }

}

