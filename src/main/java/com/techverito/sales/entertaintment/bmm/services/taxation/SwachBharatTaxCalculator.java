package com.techverito.sales.entertaintment.bmm.services.taxation;

public class SwachBharatTaxCalculator extends AbstractTaxCalculator {


    public SwachBharatTaxCalculator() {
        super("Swach Bharat","SwB", 0.5);
    }

    @Override
    public Double calculateTax(Double mrp) {
        return mrp*getTaxRate()/100;
    }
}
