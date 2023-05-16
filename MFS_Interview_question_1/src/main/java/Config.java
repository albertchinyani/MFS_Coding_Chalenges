import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private static final String PROPERTY_FILE = "config.properties";
    private static Properties properties;

    static {
        try {
            // Load the property file into a properties object
            FileInputStream fileInputStream = new FileInputStream(PROPERTY_FILE);
            properties = new Properties();
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the value of the specified property.
     * @param propertyName the name of the property to retrieve
     * @return the value of the property
     */
    public static String getProperty(String propertyName) {
        return properties.getProperty(propertyName);
    }
}
