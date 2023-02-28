package util;

import java.io.BufferedReader;
import java.util.Properties;

public class Config {
    private static Properties properties;
    public static int parallelCount = Integer.parseInt(getProperty("parallelCount", "2"));
    public static String browser = getProperty("browser", "chrome");
    public static String remote = getProperty("remote", null);
    public static long pageLoadTimeout = Long.parseLong(getProperty("pageLoadTimeout", "120"));

    private static String getProperty(String key, String def) {
        if (properties == null) properties = loadSystemProperties();

        return properties.getProperty(key, def);
    }

    private static Properties loadSystemProperties() {
        Properties properties1 = new Properties();
        properties1.putAll(System.getProperties());
        return properties1;
    }
}
