package utilities;
import org.apache.logging.log4j.LogManager;

public class Logger {
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(Logger.class);

    public static void logRequestandRespons(String Request,  String  Response) {
        // Log the TransRequest object
        logger.info(Request);
        logger.info(Response );
    }
}