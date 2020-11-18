package com.techverito.sales.entertaintment.bmm.services.taxation;

public class KrishiKalyanTaxCalculator extends AbstractTaxCalculator {


    public KrishiKalyanTaxCalculator() {
        super("Krishi Kalyan Tax","KkT", 0.5);
    }

    @Override
    public Double calculateTax(Double mrp) {
        return mrp*getTaxRate()/100;
    }
}
