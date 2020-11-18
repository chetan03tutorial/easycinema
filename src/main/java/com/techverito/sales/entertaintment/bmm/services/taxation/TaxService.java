package com.techverito.sales.entertaintment.bmm.services.taxation;

import java.util.LinkedList;
import java.util.List;

public class TaxService implements TaxCalculator {

    private final List<AbstractTaxCalculator> taxCalculators = new LinkedList<>();

    public TaxService(List<AbstractTaxCalculator> taxes){
        taxCalculators.addAll(taxes);
    }

    @Override
    public Double calculateTax(Double mrp) {
        Double totalTax = 0d;
        Double taxAmount;
        for(AbstractTaxCalculator calculator : taxCalculators){
            taxAmount = calculator.calculateTax(mrp);
            totalTax = totalTax + taxAmount;
            System.out.println(calculator.getName() + " @" + calculator.getTaxRate() + "%: " + taxAmount);
        }
        return totalTax;
    }
}
