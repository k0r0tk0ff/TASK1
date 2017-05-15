package ru.k0r0tk0ff.service;

import java.io.*;
import java.util.Properties;

public class Settings {

    private static final Settings INSTANCE = new Settings();
    private static Properties properties;
    InputStream input = null;

    private Settings() {
        properties = new Properties();
        input = this.getClass().getClassLoader().getResourceAsStream("dbConnect.properties");
        try {
                properties.load(input);
            } catch (IOException e) {
                e.printStackTrace();

                // close connection

            } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static Settings getInstance() {return INSTANCE;}

    public String getValue(String key) {
        return properties.getProperty(key);
    }
}
