package com.techverito.sales.entertaintment.bmm.services.taxation;

abstract public class AbstractTaxCalculator implements  TaxCalculator{
    private final String name;
    private final String code;
    private final Double rate;

    public Double getTaxRate(){
        return rate;
    }

    public String getName(){
        return this.name;
    }

    public String getCode(){
        return this.code;
    }

    public AbstractTaxCalculator(String name,String code, Double taxRate){
        this.name = name;
        this.code = code;
        this.rate = taxRate;
    }
}
