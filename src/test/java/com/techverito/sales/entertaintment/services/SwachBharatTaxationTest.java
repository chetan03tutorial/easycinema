package com.techverito.sales.entertaintment.services;

import com.techverito.sales.entertaintment.BaseBmmTest;
import com.techverito.sales.entertaintment.bmm.services.taxation.KrishiKalyanTaxCalculator;
import com.techverito.sales.entertaintment.bmm.services.taxation.SwachBharatTaxCalculator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SwachBharatTaxationTest extends BaseBmmTest {

    private SwachBharatTaxCalculator swbTaxCalculator;

    @Before
    public void setup(){
        swbTaxCalculator = new SwachBharatTaxCalculator();
    }

    @Test
    public void test_swb_tax(){
        assertEquals(0.5d,swbTaxCalculator.calculateTax(100.0));
    }
}
