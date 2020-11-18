package com.techverito.sales.entertaintment.bmm.services.pricing;

import com.techverito.sales.entertaintment.bmm.constants.SeatType;

import java.util.Map;

public interface PricingService {
    //Double calculateTaxAmount(Double mrp);
    Double calculateSittingCost(Long showId, Map<SeatType,Long> selection);
    void assignCost(Long showId, Map<SeatType,Double> pricing);
}
