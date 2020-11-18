package com.techverito.sales.entertaintment.bmm.constants;

public enum SeatType {
    GOLD("G"), SILVER("S"), PLATINUM("P");

    private String code;

    SeatType(String code){
        this.code = code;
    }

    public String getCode(){
        return this.code;
    }
}
