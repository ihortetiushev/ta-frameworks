package com.epam.seleniumwebdriver.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class ConfigReader {

    private static final Properties properties = new Properties();

    static {
        String env = System.getProperty("environment", "dev");
        String propFileName = "testdata-" + env + ".properties";
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream(propFileName)) {
            if (input == null) {
                throw new RuntimeException(propFileName + " not found in classpath");
            }
            try (InputStreamReader reader = new InputStreamReader(input, StandardCharsets.UTF_8)) {
                properties.load(reader);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load " + propFileName, e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
