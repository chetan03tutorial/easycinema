package com.techverito.sales.entertaintment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ApplicationConfiguration {

    private static final Properties properties = new Properties();

    public static void loadProperties(File file){
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            properties.load(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getValue(String key){
        return properties.getProperty(key);
    }
}
