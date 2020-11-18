package com.techverito.sales.entertaintment.bmm.services.payment;

import com.techverito.sales.entertaintment.ApplicationConfiguration;
import static com.techverito.sales.entertaintment.bmm.constants.BmmConstants.*;

import com.techverito.sales.entertaintment.bmm.constants.PaymentState;
import com.techverito.sales.entertaintment.bmm.exception.InsufficientFundException;
import com.techverito.sales.entertaintment.bmm.exception.PaymentGatewayException;
import com.techverito.sales.entertaintment.bmm.domain.Payment;
import com.techverito.sales.entertaintment.bmm.util.UserInputReader;

import static java.time.LocalDateTime.*;

import java.time.LocalDateTime;

public class PaymentServiceImpl implements PaymentService {

    private final PaymentGateway paymentGateway;
    private final UserInputReader userInputReader;
    private static final long idleTimeout = Long.valueOf(ApplicationConfiguration.getValue(DEFAULT_TIME_TO_EXPIRE));
    public static final String MAKE_PAYMENT_STATEMENT = "Pay %.2f in next %d seconds to confirm the seats ";
    public static final String ENTER_PAYMENT_STATEMENT = "Enter amount to pay and press enter : ";

    public PaymentServiceImpl(PaymentGateway paymentGateway, UserInputReader userInputReader){
        this.paymentGateway = paymentGateway;
        this.userInputReader = userInputReader;
    }

    public Payment makePayment(Double dueAmount){


        Payment payment = new Payment();
        System.out.println(String.format(MAKE_PAYMENT_STATEMENT,dueAmount,Integer.valueOf(ApplicationConfiguration.getValue(DEFAULT_TIME_TO_EXPIRE))));
        System.out.print(ENTER_PAYMENT_STATEMENT);
        LocalDateTime paymentStartTime = now();
        Double paidAmount = Double.valueOf(userInputReader.capturePayment());
        if(now().minusSeconds(idleTimeout).compareTo(paymentStartTime) > 0){
            payment.setState(PaymentState.FAILED);
        }else {
            payment.setPaidAmount(paidAmount);
            validateAmount(paidAmount, dueAmount);
            // Introduce the gateway timeout here of 15 seconds
            boolean done = paymentGateway.pay(paidAmount);
            if (!done) {
                throw new PaymentGatewayException("Error at Payment Gateway");
            }
            payment.setState(done ? PaymentState.COMPLETED : PaymentState.FAILED);
        }




        /*paymentStatus.setDueAmount(dueAmount);*/

        // Intentionally asking the amount to pay to play the use case for counter booking
        // In real life, the payment gateway would only accept the due amount for the booking
        // and the timeout condition would be applicable on the payment gateway.
        /*LocalDateTime paymentStartTime = now();
        Scanner scanner = new Scanner(System.in);
        Double paidAmount = scanner.nextDouble();

        if(now().minusSeconds(idleTimeout).compareTo(paymentStartTime) > 0){
            paymentStatus.setState(PaymentState.FAILED);
        }else{*/

        //}
        return payment;
    }

    private void validateAmount(Double paidAmount, Double dueAmount) {
        if(paidAmount - dueAmount < 0){
            throw new InsufficientFundException("Paid Amount cannot be less than " + dueAmount);
        }
    }
}
