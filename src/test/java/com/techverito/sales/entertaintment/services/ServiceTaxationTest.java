package com.techverito.sales.entertaintment.services;

import com.techverito.sales.entertaintment.BaseBmmTest;
import com.techverito.sales.entertaintment.bmm.services.taxation.KrishiKalyanTaxCalculator;
import com.techverito.sales.entertaintment.bmm.services.taxation.ServiceTaxCalculator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServiceTaxationTest extends BaseBmmTest {

    private ServiceTaxCalculator serviceTaxCalculator;

    @Before
    public void setup(){
        serviceTaxCalculator = new ServiceTaxCalculator();
    }

    @Test
    public void test_service_tax(){
        assertEquals(14d,serviceTaxCalculator.calculateTax(100.0));
    }
}
