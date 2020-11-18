package com.techverito.sales.entertaintment.bmm.exception;

public class BaseException extends RuntimeException{

    public BaseException(){
    }

    public BaseException(String message, Throwable th){
        super(message,th);
    }

    public BaseException(String message){
        super(message);
    }

    public BaseException(Throwable th){
        super(th);
    }

}
