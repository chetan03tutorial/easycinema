package com.techverito.sales.entertaintment.bmm.exception;

public class InputOutputException extends BaseException{

    public InputOutputException(){
    }

    public InputOutputException(String message, Throwable th){
        super(message,th);
    }

    public InputOutputException(String message){
        super(message);
    }

    public InputOutputException(Throwable th){
        super(th);
    }

}
