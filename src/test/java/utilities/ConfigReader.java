package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties = new Properties();

    static {
        readConfig();
    }

    private static void readConfig() {
        try (FileInputStream input = new FileInputStream("config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isReportingEnabled() {
        String enableReporting = properties.getProperty("enableReporting");
        return Boolean.parseBoolean(enableReporting);
    }
}
