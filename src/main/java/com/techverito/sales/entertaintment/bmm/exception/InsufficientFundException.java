package com.techverito.sales.entertaintment.bmm.exception;

public class InsufficientFundException extends BaseException{

    public InsufficientFundException(){

    }
    public InsufficientFundException(String message, Throwable th){
        super(message,th);
    }
    public InsufficientFundException(String message){
        super(message);
    }
    public InsufficientFundException(Throwable th){
        super(th);
    }

}
