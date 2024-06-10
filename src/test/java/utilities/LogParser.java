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

// This class calculates transaction time
public class LogParser {

	private String ReqOfPOS =  "Request Received From POS : <TransRequest>" ;             //" Request Received From POS :";
	private String ResOfPOS =    "Response sent to POS: <TransResponse>" ;//" POS response sent successfully.";
	
	  private String ReqOfPED = null;
	  private String ResOfPED = null;
	 

	private static final String LOG_FILE_PATH = "./src/test/resources/AESDK.log";
	private static final String EXCEL_FILE_PATH = "./transactionsTime.xlsx";
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss,SSS");
	private static final Pattern TIMESTAMP_PATTERN = Pattern
			.compile("(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2},\\d{3})");

	private Workbook workbook;
	private Sheet sheet;
	private static long totalDurationInMillis = 0;

	public static void main(String[] args) {
		LogParser lp = new LogParser();
		lp.setupWorkBook();
		lp.parseLogFile(LOG_FILE_PATH);
		lp.saveExcelFile();
	}

	private void setupWorkBook() {
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet("Transactions");
		createHeaderRow();
	}

	private void createHeaderRow() {
		Row headerRow = sheet.createRow(0);
		List<String> headers = Arrays.asList("Request Type", "Request Timestamp", "Response Timestamp", "Time Taken",
				"Total Time");
		for (int i = 0; i < headers.size(); i++) {
			Cell headerCell = headerRow.createCell(i);
			headerCell.setCellValue(headers.get(i));
		}
	}

	private void parseLogFile(String logFilePath) {
		System.out.println("Parsing log file: " + logFilePath);

		try (BufferedReader br = new BufferedReader(new FileReader(logFilePath))) {
			String line;
			LocalDateTime requestReceivedTimestamp = null;
			String requestMessage = null;
			String requestType = null;

			while ((line = br.readLine()) != null) {
				Matcher matcher = TIMESTAMP_PATTERN.matcher(line);
				if (matcher.find()) {
					LocalDateTime timestamp = LocalDateTime.parse(matcher.group(), FORMATTER);
					String messageType = getMessageType(line);

					if (messageType != null) {
						if (messageType.equals(ReqOfPOS) || messageType.equals(ReqOfPED)) {
							requestReceivedTimestamp = timestamp;
							requestMessage = line;
							//requestType = extractRequestType(line);
						} else if (messageType.equals(ResOfPOS) || messageType.equals(ResOfPED)) {
							if (requestReceivedTimestamp != null) {
								requestType = extractRequestType(line);
								processTransaction(requestReceivedTimestamp, timestamp, requestMessage, line,
										requestType);
								requestReceivedTimestamp = null;
								requestMessage = null;
								requestType = null;
							}
						}
					}
				}
			}
		} catch (IOException e) {
			System.err.println("Error reading the log file: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void processTransaction(LocalDateTime requestTimestamp, LocalDateTime responseTimestamp,
			String requestMessage, String responseMessage, String requestType) {
		Duration duration = Duration.between(requestTimestamp, responseTimestamp);
		long durationMillis = duration.toMillis();
		totalDurationInMillis += durationMillis;

		String totalTime = totalDurationInMillis + " milliseconds";
		String diffTime = durationMillis + " milliseconds";

		List<String> data = Arrays.asList(requestType, requestTimestamp.toString().replace('T', ' '),
				responseTimestamp.toString().replace('T', ' '), diffTime, totalTime);

		writeTransactionTimeData(data);

		// Console output
		System.out.println("Processed Request :");
		System.out.println("  Request Type:      " + requestType);
		System.out.println("  Request:           " + requestMessage);
		System.out.println("  Response:          " + responseMessage);
		System.out.println("  Request Timestamp: " + requestTimestamp);
		System.out.println("  Response Timestamp:" + responseTimestamp);
		System.out.println("  Time Taken:        " + diffTime);
		System.out.println("  Total Time:        " + totalTime);
		System.out.println();
	}

	private String getMessageType(String line) {

		// for the END TO END transaction time
		
		  if (line.contains(ReqOfPOS)) { return ReqOfPOS; } else if
		  (line.contains(ResOfPOS)) { return ResOfPOS; }
		 
		// For the CCT transaction Time
		/*
		 * if (line.contains(ReqOfPED)) { return ReqOfPED; } else if
		 * (line.contains(ResOfPED)) { return ResOfPED; }
		 */

		return null;
	}

	/*
	 * private String extractRequestType(String line) { int startIndex =
	 * line.indexOf(": < ") + 3; // Add 3 to include the length of ": < " int
	 * endIndex = line.indexOf('>', startIndex); // Look for '>' after ": < " if
	 * (endIndex == -1) { endIndex = line.length(); } return line.substring(131,
	 * endIndex); //return ""; }
	 */
	
	private String extractRequestType(String line) {
	    int startIndex = line.indexOf('<');
	    if (startIndex == -1) {
	        return ""; // If '<' is not found, return an empty string
	    }
	    return line.substring(startIndex); // Extract from '<' to the end of the line
	}

	private void writeTransactionTimeData(List<String> data) {  
		int currentRow = sheet.getLastRowNum() + 1;
		Row row = sheet.createRow(currentRow);

		for (int i = 0; i < data.size(); i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(data.get(i));
		}
	}

	private void saveExcelFile() {
		try (FileOutputStream outputStream = new FileOutputStream(EXCEL_FILE_PATH)) {
			workbook.write(outputStream);
			System.out.println("Excel file saved: " + EXCEL_FILE_PATH);
		} catch (IOException e) {
			System.err.println("Error saving the Excel file: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
