package com.techverito.sales.entertaintment.services;

import com.techverito.sales.entertaintment.BaseBmmTest;
import com.techverito.sales.entertaintment.bmm.services.taxation.KrishiKalyanTaxCalculator;
import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class KrishiKalyanTaxationTest extends BaseBmmTest {

    private KrishiKalyanTaxCalculator kklTaxCalculator;

    @Before
    public void setup(){
        kklTaxCalculator = new KrishiKalyanTaxCalculator();
    }

    @Test
    public void test_kkl_tax(){
        assertEquals(0.5d,kklTaxCalculator.calculateTax(100.0));
    }
}
