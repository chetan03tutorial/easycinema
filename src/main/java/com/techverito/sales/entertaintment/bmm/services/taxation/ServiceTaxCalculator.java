package com.techverito.sales.entertaintment.bmm.services.taxation;

public class ServiceTaxCalculator extends AbstractTaxCalculator {


    public ServiceTaxCalculator() {
        super("Service Tax","SvT", 14.0);
    }

    @Override
    public Double calculateTax(Double mrp) {
        return mrp*getTaxRate()/100;
    }
}
