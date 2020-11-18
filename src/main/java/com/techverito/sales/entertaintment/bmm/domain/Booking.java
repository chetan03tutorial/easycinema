package com.techverito.sales.entertaintment.bmm.domain;

import static com.techverito.sales.entertaintment.bmm.constants.BookingState.*;

import com.techverito.sales.entertaintment.bmm.constants.BookingState;
import com.techverito.sales.entertaintment.bmm.domain.Seat;
import com.techverito.sales.entertaintment.bmm.sequences.LongSequenceGenerator;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class Booking {
    private static final LongSequenceGenerator longSequenceGenerator = new LongSequenceGenerator(124011L);
    private Long bookingId;
    private BookingState state;
    private Set<String> available;
    private Double subtotal;
    private Double tax;
    private Double total;
    private Set<String> unavailable;
    private String failureReason;

    public static Booking confirmBooking(Set<String> mnemonics,Double seatAmount,Double taxAmount){
        return new Booking(CONFIRMED,mnemonics,seatAmount,taxAmount);
    }

    public static Booking failBooking(String failureReason){
        return new Booking(FAILED,failureReason);
    }

    private Booking(BookingState state, Set<String> available, Double subtotal, Double tax){
        bookingId = longSequenceGenerator.getNext();
        this.available = available;
        this.subtotal = subtotal;
        this.tax = tax;
        this.total = subtotal + tax;
        this.state = state;
    }

    private Booking(BookingState state,String failureReason){
        bookingId = longSequenceGenerator.getNext();
        this.state = state;
        this.failureReason = failureReason;
    }
}
