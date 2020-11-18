package com.techverito.sales.entertaintment.bmm.services.payment;

import com.techverito.sales.entertaintment.bmm.services.payment.PaymentGateway;

import java.util.Random;

public class IBPaymentGateway implements PaymentGateway {

    private static Random random = new Random();

    public boolean pay(Double amount){
        int randomInteger = random.nextInt(5);
        return randomInteger == 1 ? false : true;
    }
}
