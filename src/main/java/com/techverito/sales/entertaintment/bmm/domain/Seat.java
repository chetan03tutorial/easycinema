package com.techverito.sales.entertaintment.bmm.domain;

import com.techverito.sales.entertaintment.bmm.constants.SeatState;
import com.techverito.sales.entertaintment.bmm.constants.SeatType;
import com.techverito.sales.entertaintment.bmm.sequences.LongSequenceGenerator;
import lombok.Data;

@Data
public class Seat {
    private static final LongSequenceGenerator seatIdGenerator =
            new LongSequenceGenerator(79100L);
    private final Long identifier;
    private volatile SeatState state;
    private SeatType seatType;
    private String mnemonic;

    public Seat(){
        identifier = seatIdGenerator.getNext();
    }

    public Seat setState(SeatState state){
        this.state = state;
        return this;
    }

    public Seat(int seatNumber, SeatType type){
        state = SeatState.UNALLOCATED;
        identifier = seatIdGenerator.getNext();
        seatType = type;
        mnemonic = type.getCode().concat(String.valueOf(seatNumber));
    }

    @Override
    public String toString(){
        return mnemonic;
    }
}
