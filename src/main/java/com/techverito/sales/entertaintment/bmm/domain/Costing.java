package com.techverito.sales.entertaintment.bmm.domain;

import com.techverito.sales.entertaintment.bmm.constants.SeatType;
import com.techverito.sales.entertaintment.bmm.sequences.LongSequenceGenerator;
import lombok.Data;

import java.util.Map;

@Data
public class Costing {
    private final Long costingId;
    private Long showId;
    private Map<SeatType,Double> pricing;
    private static final LongSequenceGenerator costingIdSequence = new LongSequenceGenerator(41000L);

    public Costing(){
        costingId = costingIdSequence.getNext();
    }
}
