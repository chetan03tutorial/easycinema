package com.techverito.sales.entertaintment.bmm.util;

import com.techverito.sales.entertaintment.ApplicationConfiguration;

import java.util.Scanner;

import static com.techverito.sales.entertaintment.bmm.constants.BmmConstants.DEFAULT_TIME_TO_EXPIRE;

public class UserInputReader {


    private final Scanner scanner;

    public UserInputReader(){
        scanner = new Scanner(System.in);
    }

    public String captureShow(){
        System.out.print("Enter the show Id :");
        return scanner.next();
    }

    public String captureSeats(){
        System.out.print("Enter the seats :");
        return scanner.next();
    }

    public String capturePayment(){
        return scanner.next();
    }

    public String captureAction() {
        System.out.print("Press B for Booking and E for Exit :");
        return scanner.next().trim();
    }
}
