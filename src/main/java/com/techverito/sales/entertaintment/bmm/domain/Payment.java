package com.techverito.sales.entertaintment.bmm.domain;

import com.techverito.sales.entertaintment.bmm.constants.PaymentState;
import lombok.Data;

@Data
public class Payment {
    private Double dueAmount;
    private PaymentState state;
    private Double paidAmount;
}
