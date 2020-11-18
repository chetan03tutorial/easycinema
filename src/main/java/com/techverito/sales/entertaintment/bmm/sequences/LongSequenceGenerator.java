package com.techverito.sales.entertaintment.bmm.sequences;

import java.util.concurrent.atomic.AtomicLong;

public class LongSequenceGenerator implements SequenceGenerator<Long> {
    private volatile AtomicLong sequence;
    private static final Long counter = Long.valueOf(1);

    public LongSequenceGenerator(Long initialValue){
        sequence = new AtomicLong(initialValue);
    }

    public Long getNext(){
        return sequence.getAndAdd(counter);
    }
}
