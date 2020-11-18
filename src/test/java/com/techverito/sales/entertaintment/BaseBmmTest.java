package com.techverito.sales.entertaintment;

import com.techverito.sales.entertaintment.helper.BookingUnitTestHelper;
import com.techverito.sales.entertaintment.helper.TestResourceReader;

public class BaseBmmTest {
    protected BookingUnitTestHelper unitTestHelper = new BookingUnitTestHelper();

    static{
        TestResourceReader.readFile("conf/","properties").stream()
                .forEach(ApplicationConfiguration::loadProperties);
    }

}
