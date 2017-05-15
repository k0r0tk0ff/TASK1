package ru.k0r0tk0ff.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by user on 3/26/2017.
 */

public class test {

    public static void main(String[] argv) throws Exception {

        //Settings settings = Settings.getInstance();
        //System.out.println(settings.getValue("jdbc.url"));



/*    InputStream input = null;
    Properties properties = new Properties();
    try {
        input = Settings.class.getClassLoader().getResourceAsStream("dbConnect.properties");

        // load a properties file
        properties.load(input);

        // get the property value and print it out
        System.out.println(properties.getProperty("jdbc.url"));
        System.out.println(properties.getProperty("jdbc.driver_class"));
        System.out.println(properties.getProperty("jdbc.username"));

    } catch (IOException ex) {
        ex.printStackTrace();
    } finally {
        if (input != null) {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/

}


}