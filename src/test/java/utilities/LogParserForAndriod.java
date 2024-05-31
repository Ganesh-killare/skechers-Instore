package utilities;


import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



// This class for calculate transaction time 
public class LogParserForAndriod {

	 Workbook workbook;
	 Sheet sheet;
		static long totalDurationInMillis = 0;

	public  void setupWorkBook() {
		workbook = new XSSFWorkbook();  
		sheet = workbook.createSheet("Transactions");
	}

	public static void main(String[] args) {
		LogParserForAndriod lp = new LogParserForAndriod();

		String logFilePath = "./src\\test\\resources\\AESDK.log";

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss,SSS");

		try (BufferedReader br = new BufferedReader(new FileReader(logFilePath))) {
			String line;
			Pattern timestampPattern = Pattern.compile("(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2},\\d{3})");

			LocalDateTime requestReceivedTimestamp = null;
			String requestMessage = null;

			while ((line = br.readLine()) != null) {
				Matcher matcher = timestampPattern.matcher(line);
				if (matcher.find()) {
					LocalDateTime timestamp = LocalDateTime.parse(matcher.group().replace("T", " "), formatter);
				//	LocalDateTime timestamp = LocalDateTime.parse(matcher.group(), formatter);
					String messageType = getMessageType(line);
					if (messageType != null) {
						if (messageType.equals("Request Received From POS")
								|| messageType.equals("Request sent to CCT")) {
							requestReceivedTimestamp = timestamp;
							requestMessage = line;
						} else if (messageType.equals("Response sent to POS")
								|| messageType.equals("Got Response From CCT")) {
							if (requestReceivedTimestamp != null) {
								LocalDateTime responseSentTimestamp = timestamp;
								Duration duration = Duration.between(requestReceivedTimestamp, responseSentTimestamp);
								totalDurationInMillis += duration.toMillis();
								System.out.println("Request:          " + requestMessage);
								System.out.println("Response:         " + line);

								System.out.println(
										"\n" + requestReceivedTimestamp + "          " + responseSentTimestamp);
								System.out.println("Time difference:   " + duration.toMillis() + " milliseconds");
								System.out.println();
								String totalTime = String.valueOf(totalDurationInMillis)+" milliseconds";
								String diffTime = String.valueOf(duration.toMillis())+" milliseconds";
								System.out.println(diffTime);  

								
								  List<String> data = Arrays.asList(requestReceivedTimestamp.toString().replace('T', ' '), responseSentTimestamp.toString().replace('T', ' '), diffTime, totalTime);
								  
								  lp.writeTransactionTimeData(data); 
								  lp.saveExcelFile();
								 

								// Reset variables for the next request-response pair
								requestReceivedTimestamp = null;
								requestMessage = null;
							}
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String getMessageType(String line) {
		if (line.contains(" POS Request :::")) {
			return "Request Received From POS";
		} else if (line.contains(" POS Response :::")) {    
			return "Response sent to POS";
    
		}
		
		/*
		 * private static String getMessageType(String line) { if
		 * (line.contains(" POS Request :::")) { return
		 * "Request Received From POS"; } else if
		 * (line.contains(" POS Response :::")) { return "Response sent to POS";
		 * 
		 * }
		 */
		// This is for the CCT Request If we need POS then comment below lines   

		
		  else if (line.contains("Request sent to CCT:")) { return
		  "Request sent to CCT";
		  
		  } else if (line.contains(" Got Response From CCT:")) { return
		  "Got Response From CCT"; }
		  
		 
		return null;
	}

	///////////////// Excel Releted operations

	public  void writeTransactionTimeData(List<String> data) {
		if (this.sheet == null) {
			setupWorkBook();
		}

		int currentRow = sheet.getLastRowNum() + 1;

		if (currentRow == 0) {
			Row headerRow = sheet.createRow(0);
			// Add headers here based on your requirement
			List<String> headers = Arrays.asList("Request", "Response", "Taken Time" , "Total time");
			for (int i = 0; i < headers.size(); i++) {
				Cell headerCell = headerRow.createCell(i);
				headerCell.setCellValue(headers.get(i));
			}
			currentRow++;
		}

		Row row = sheet.createRow(currentRow);
		for (int i = 0; i < data.size(); i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(data.get(i));
		}
	}

	public  void saveExcelFile() {
		try (FileOutputStream outputStream = new FileOutputStream("./transactionsTime.xlsx")) {
			workbook.write(outputStream);

		} catch (IOException e) {
			System.out.println("We are facing Issue to store Void Data");
			e.printStackTrace();
		}
	}
}
