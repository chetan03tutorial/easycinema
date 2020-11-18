package com.techverito.sales.entertaintment.services;

import com.techverito.sales.entertaintment.ApplicationConfiguration;
import com.techverito.sales.entertaintment.BaseBmmTest;
import com.techverito.sales.entertaintment.bmm.exception.PaymentGatewayException;
import com.techverito.sales.entertaintment.bmm.domain.Payment;
import com.techverito.sales.entertaintment.bmm.services.payment.IBPaymentGateway;
import com.techverito.sales.entertaintment.bmm.services.payment.PaymentGateway;
import com.techverito.sales.entertaintment.bmm.services.payment.PaymentService;
import com.techverito.sales.entertaintment.bmm.services.payment.PaymentServiceImpl;
import com.techverito.sales.entertaintment.bmm.util.UserInputReader;
import com.techverito.sales.entertaintment.helper.TestResourceReader;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class  PaymentServiceTest extends BaseBmmTest {

    private PaymentService paymentService;
    private PaymentGateway paymentGateway;
    private UserInputReader userInputReader;
    //private BookingUnitTestHelper unitTestHelper;

    @Before
    public void setup(){

        paymentGateway = mock(IBPaymentGateway.class);
        userInputReader = mock(UserInputReader.class);
        paymentService = new PaymentServiceImpl(paymentGateway,userInputReader);
    }

    @Test
    public void test_successful_payment(){
        String amount = "500";
        InputStream in = new ByteArrayInputStream(amount.getBytes());
        System.setIn(in);
        when(paymentGateway.pay(anyDouble())).thenReturn(true);
        when(userInputReader.capturePayment()).thenReturn("500");
        Payment payment = paymentService.makePayment(500d);
        assertNotNull(payment.getPaidAmount());
    }

    @Test(expected = PaymentGatewayException.class)
    public void test_payment_failure_when_gateway_is_down(){
        String amount = "500";
        InputStream in = new ByteArrayInputStream(amount.getBytes());
        System.setIn(in);
        when(paymentGateway.pay(anyDouble())).thenReturn(false);
        when(userInputReader.capturePayment()).thenReturn("500");
        Payment payment = paymentService.makePayment(500d);
        assertNotNull(payment.getPaidAmount());
    }
}
