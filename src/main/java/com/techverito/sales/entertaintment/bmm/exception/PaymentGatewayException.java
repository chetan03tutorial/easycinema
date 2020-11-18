package com.techverito.sales.entertaintment.bmm.exception;

public class PaymentGatewayException extends BaseException{

    public PaymentGatewayException(){

    }
    public PaymentGatewayException(String message, Throwable th){
        super(message,th);
    }
    public PaymentGatewayException(String message){
        super(message);
    }
    public PaymentGatewayException(Throwable th){
        super(th);
    }

}
