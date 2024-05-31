package utilities;
import org.apache.logging.log4j.LogManager;

public class Logger {
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(Logger.class);

    public static void logData(String Request) {
    	  String modifiedRequest = Request.replaceAll("\\s+", "");
        logger.info(modifiedRequest);
        
    }
}