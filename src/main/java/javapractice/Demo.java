package javapractice;

import java.util.List;

import org.testng.annotations.Test;

public class Demo {
	  public static String convertListToString(List<String> list, String delimiter) {
	        StringBuilder sb = new StringBuilder();
	        sb.append("["); // Start with a bracket

	        for (String str : list) {
	            sb.append(str).append(delimiter); // Append each element with the specified delimiter
	        }

	        // Remove the last delimiter and add closing bracket
	        if (!list.isEmpty()) {
	            sb.setLength(sb.length() - delimiter.length()); // Adjust length to remove last delimiter
	        }

	        sb.append("]"); // End with a bracket

	        return sb.toString();
	    }

	  
	  @Test
	  public void demor() {
		  
		  List<String> list = List.of("apple", "banana", "orange");
		System.out.println( convertListToString(list, ",")); 
	  }
}
