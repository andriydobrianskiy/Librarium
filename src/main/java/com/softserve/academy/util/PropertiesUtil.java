package com.softserve.academy.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesUtil {
    private static final Logger LOGGER = Logger.getLogger(PropertiesUtil.class);
    private static final String PROPERTIES_FILENAME = "/db.properties";

    public static Properties getProperties() {
        Properties properties = new Properties();
        try (InputStream inputStream = PropertiesUtil.class.getResourceAsStream(PROPERTIES_FILENAME)) {
            properties.load(inputStream);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return properties;
    }
}
