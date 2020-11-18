package com.techverito.sales.entertaintment.services;

import com.techverito.sales.entertaintment.BaseBmmTest;
import com.techverito.sales.entertaintment.bmm.services.taxation.KrishiKalyanTaxCalculator;
import com.techverito.sales.entertaintment.bmm.services.taxation.SwachBharatTaxCalculator;
import com.techverito.sales.entertaintment.bmm.services.taxation.TaxService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

public class TaxServiceTest extends BaseBmmTest {

    private TaxService taxService;

    @Before
    public void setup(){
        KrishiKalyanTaxCalculator krishiKalyanTaxCalculator = new KrishiKalyanTaxCalculator();
        SwachBharatTaxCalculator swachBharatTaxCalculator = new SwachBharatTaxCalculator();
        taxService = new TaxService(Arrays.asList(krishiKalyanTaxCalculator,swachBharatTaxCalculator));
    }

    @Test
    public void test_tax_calculation(){
        Double amount = taxService.calculateTax(100d);
        assertEquals(1d,amount);
    }
}
