package com.example.playwrightdemo.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class Config {

    public static final String API_BASE_URL_KEY = "api.base.url";
    public static final String BROWSER_KEY = "browser";
    public static final String HEADLESS_KEY = "headless";
    public static final String SLOWMO_KEY = "slowmo";

    private static final Properties properties = new Properties();

    static {
        try (InputStream input =
                     Config.class.getClassLoader().getResourceAsStream("config.properties")) {

            if (input == null) {
                throw new RuntimeException("config.properties not found");
            }

            properties.load(input);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String get(String key) {
        return System.getProperty(key, properties.getProperty(key));
    }

    private Config() {
    }

    public static int getInt(String key) {
        return Integer.parseInt(System.getProperty(key, properties.getProperty(key)));
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(System.getProperty(key, properties.getProperty(key)));
    }
}
