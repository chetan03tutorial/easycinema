package com.techverito.sales.entertaintment.bmm.services.payment;

import com.techverito.sales.entertaintment.bmm.domain.Payment;

public interface PaymentService {
    public Payment makePayment(Double dueAmount);
}
