package com.techverito.sales.entertaintment.bmm.util;

/**
 * In real life, we would be using a proper logging mechanism
 */
public class Logger {

    public void log(Exception ex){
        System.out.println("***************** LOG *****************************");
        System.out.println(ex.getMessage());
        System.out.println("***************** LOG *****************************");
    }
}
