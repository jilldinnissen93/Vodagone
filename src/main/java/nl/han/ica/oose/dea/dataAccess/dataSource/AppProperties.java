package nl.han.ica.oose.dea.dataAccess.dataSource;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

class AppProperties {

    private static final Logger logger = Logger.getLogger(AppProperties.class.getName());
    private final Properties properties;

    public AppProperties() {
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("properties.properties"));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Unable to access application properties file.", e);
        }

    }

    public Properties getProperties() {
        return properties;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

}