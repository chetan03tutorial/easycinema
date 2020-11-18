package com.techverito.sales.entertaintment.bmm.exception;

public class NotSupportedException extends BaseException{

    public NotSupportedException(){

    }
    public NotSupportedException(String message, Throwable th){
        super(message,th);
    }
    public NotSupportedException(String message){
        super(message);
    }
    public NotSupportedException(Throwable th){
        super(th);
    }

}
