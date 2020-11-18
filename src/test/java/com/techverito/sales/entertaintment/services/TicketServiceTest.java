package com.techverito.sales.entertaintment.services;

import com.techverito.sales.entertaintment.BaseBmmTest;
import com.techverito.sales.entertaintment.bmm.constants.AvailabilityStatus;
import com.techverito.sales.entertaintment.bmm.constants.BookingState;
import com.techverito.sales.entertaintment.bmm.constants.PaymentState;
import com.techverito.sales.entertaintment.bmm.domain.Booking;
import com.techverito.sales.entertaintment.bmm.services.ShowService;
import com.techverito.sales.entertaintment.bmm.services.TicketService;
import com.techverito.sales.entertaintment.bmm.services.TicketServiceImpl;
import com.techverito.sales.entertaintment.bmm.services.booking.BookingService;
import com.techverito.sales.entertaintment.bmm.services.payment.PaymentService;
import com.techverito.sales.entertaintment.bmm.services.pricing.PricingService;
import com.techverito.sales.entertaintment.bmm.services.taxation.TaxService;
import org.junit.Before;

import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;


public class TicketServiceTest extends BaseBmmTest {
    private TicketService ticketService;
    private ShowService showService;
    private BookingService bookingService;
    private PricingService pricingService;
    private PaymentService paymentService;
    private TaxService taxService;
    //private BookingUnitTestHelper unitTestHelper;
    private Set<String> seatMnemonics;

    @Before
    public void setup(){


        seatMnemonics = new HashSet<>();
        seatMnemonics.add("G1");
        seatMnemonics.add("G2");

        showService = mock(ShowService.class);
        bookingService = mock(BookingService.class);
        pricingService = mock(PricingService.class);
        paymentService = mock(PaymentService.class);
        taxService = mock(TaxService.class);

        ticketService = new TicketServiceImpl(showService,bookingService,pricingService,paymentService,taxService);
    }

    @Test
    public void test_successful_booking(){
        when(showService.getShow(41002l)).thenReturn(unitTestHelper.getShow(41002l));
        when(pricingService.calculateSittingCost(anyLong(),anyMap())).thenReturn(Double.valueOf(400));
        when(taxService.calculateTax(anyDouble())).thenReturn(Double.valueOf(40));
        when(bookingService.reserveSitting(any(List.class))).thenReturn(AvailabilityStatus.AVAILABLE);
        when(paymentService.makePayment(anyDouble())).thenReturn(unitTestHelper.getPaymentStatus(PaymentState.COMPLETED));
        Booking booking = ticketService.bookTicket(41002l, seatMnemonics);
        assertEquals(BookingState.CONFIRMED, booking.getState());
    }

    @Test
    public void test_failure_due_to_unavailability(){
        when(showService.getShow(41002l)).thenReturn(unitTestHelper.getShow(41002l));
        when(bookingService.reserveSitting(any(List.class))).thenReturn(AvailabilityStatus.UNAVAILABLE);
        Booking booking = ticketService.bookTicket(41002l, seatMnemonics);
        assertEquals(BookingState.FAILED, booking.getState());
    }

    @Test
    public void test_booking_status_when_payment_failed(){
        when(showService.getShow(41002l)).thenReturn(unitTestHelper.getShow(41002l));
        when(pricingService.calculateSittingCost(anyLong(),anyMap())).thenReturn(Double.valueOf(400));
        when(taxService.calculateTax(anyDouble())).thenReturn(Double.valueOf(40));
        when(bookingService.reserveSitting(any(List.class))).thenReturn(AvailabilityStatus.AVAILABLE);
        when(paymentService.makePayment(anyDouble())).thenReturn(unitTestHelper.getPaymentStatus(PaymentState.FAILED));
        Booking booking = ticketService.bookTicket(41002l, seatMnemonics);
        assertEquals(BookingState.FAILED, booking.getState());
    }

}
