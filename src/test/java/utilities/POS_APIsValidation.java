package utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class POS_APIsValidation {

    // Regular expressions for extracting time and request type
    private static final Pattern TIME_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2},\\d{3}");
    private static final Pattern REQUEST_TYPE_PATTERN = Pattern.compile("Request type (\\w+)");

    public static void main(String[] args) {
        // Path to your log file
        String logFilePath = "./logs/_aesdk_1_07-31-2024.log";

        try (BufferedReader reader = new BufferedReader(new FileReader(logFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Extract and print the time and request type
                String time = extractTime(line);
                if (time != null) {
                    System.out.println("Time: " + time);
                    String requestType = extractRequestType(line);
                    if (requestType != null) {
                        System.out.println("Request Type: " + requestType);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String extractTime(String line) {
        Matcher matcher = TIME_PATTERN.matcher(line);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    private static String extractRequestType(String line) {
        Matcher matcher = REQUEST_TYPE_PATTERN.matcher(line);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
