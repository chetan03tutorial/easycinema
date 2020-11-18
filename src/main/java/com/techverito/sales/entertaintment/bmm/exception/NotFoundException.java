package com.techverito.sales.entertaintment.bmm.exception;

public class NotFoundException extends BaseException{

    public NotFoundException(){

    }
    public NotFoundException(String message, Throwable th){
        super(message,th);
    }
    public NotFoundException(String message){
        super(message);
    }
    public NotFoundException(Throwable th){
        super(th);
    }

}
