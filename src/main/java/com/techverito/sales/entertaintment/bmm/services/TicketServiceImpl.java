package com.techverito.sales.entertaintment.bmm.services;

import com.techverito.sales.entertaintment.bmm.constants.*;
import com.techverito.sales.entertaintment.bmm.domain.Show;
import com.techverito.sales.entertaintment.bmm.domain.Seat;
import com.techverito.sales.entertaintment.bmm.domain.sitting.arrangement.Arrangement;
import com.techverito.sales.entertaintment.bmm.exception.NotFoundException;
import com.techverito.sales.entertaintment.bmm.domain.Payment;
import com.techverito.sales.entertaintment.bmm.domain.Booking;
import com.techverito.sales.entertaintment.bmm.services.booking.BookingService;
import com.techverito.sales.entertaintment.bmm.services.payment.PaymentService;
import com.techverito.sales.entertaintment.bmm.services.pricing.PricingService;
import com.techverito.sales.entertaintment.bmm.services.taxation.TaxService;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.*;

public class TicketServiceImpl implements TicketService {

    private final BookingService bookingService;
    private final PricingService pricingService;
    private final ShowService showService;
    private final PaymentService paymentService;
    private final TaxService taxService;

    public TicketServiceImpl(ShowService showService,
                             BookingService bookingService,
                             PricingService pricingService,
                             PaymentService paymentService,
                             TaxService taxService){
        this.bookingService = bookingService;
        this.pricingService = pricingService;
        this.showService = showService;
        this.paymentService = paymentService;
        this.taxService = taxService;
    }

    @Override
    public void confirmSitting(List<Seat> seats) {
        seats.stream()
                .filter(s -> s.getState().equals(SeatState.PAYMENT_COMPLETED))
                .forEach(s -> s.setState(SeatState.ALLOCATED));
    }

    @Override
    public void updatePaymentStatus(List<Seat> seats) {
        seats.stream()
                .filter(s -> s.getState().equals(SeatState.PAYMENT_PENDING))
                .forEach(s -> s.setState(SeatState.PAYMENT_COMPLETED));
    }


    /*public Booking pay(Booking booking){
        *//*Double totalCost = booking.getTotalCost();*//*
        *//*List<Seat> selectedSeats = booking.getSeats();*//*
        *//*PaymentStatus paymentStatus = paymentService.makePayment(totalCost, booking.getAmountPaid());*//*

        if(paymentStatus.getState().equals(PaymentState.COMPLETED)){
            bookingService.updatePaymentStatus(selectedSeats);
            bookingService.confirmSitting(selectedSeats);
            booking.setBookingStatus(BookingState.CONFIRMED);
            booking.setAmountPaid(paymentStatus.getPaidAmount());
        }else{
            booking.setBookingStatus(BookingState.FAILED);
            booking.setFailureReason("Payment Failure");
            return booking;
        }
        return booking;
    }*/

    public Booking bookTicket(Long showId, Set<String> mnemonics){

        Show show = showService.getShow(showId);
        List<Seat> selectedSeats = mnemonics.stream()
                .map((mnemonic -> retrieveSeat(show,mnemonic)))
                .collect(toList());

        Map<SeatType,Long> seatCount = selectedSeats.stream()
                .collect(groupingBy(Seat::getSeatType, counting()));

        AvailabilityStatus availabilityStatus = bookingService.reserveSitting(selectedSeats);
        List<String> unavailable = selectedSeats.stream()
                .filter((Seat seat) -> ! seat.getState().equals(SeatState.UNALLOCATED))
                .map(Seat::getMnemonic)
                .collect(toList());

        if(availabilityStatus == AvailabilityStatus.AVAILABLE) {
            Double seatCost = pricingService.calculateSittingCost(showId, seatCount);
            Double taxAmount = taxService.calculateTax(seatCost);
            Double totalAmount  = seatCost + taxAmount;
            Payment payment = paymentService.makePayment(totalAmount);
            if (payment.getState().equals(PaymentState.COMPLETED)) {
                updatePaymentStatus(selectedSeats);
                confirmSitting(selectedSeats);
                return Booking.confirmBooking(mnemonics,seatCost,taxAmount);
            } else {
                return Booking.failBooking("Payment Failure");
            }
        }else{
            return Booking.failBooking("Seats Unavailable : " + unavailable);
        }
    }


    private Seat retrieveSeat(Show show , String mnemonics){

        char category = mnemonics.charAt(0);
        return show.getArrangements().stream()
                .filter(a -> a.getCategory().getCode().equalsIgnoreCase(Character.toString(category)))
                .flatMap((Arrangement alpha)  -> alpha.getSeats().stream())
                .filter(s -> s.getMnemonic().equalsIgnoreCase(mnemonics))
                .findFirst().orElseThrow(() -> new NotFoundException("Seat Not found : " + mnemonics ));
    }
}
