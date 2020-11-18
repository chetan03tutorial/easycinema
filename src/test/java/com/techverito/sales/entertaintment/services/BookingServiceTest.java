package com.techverito.sales.entertaintment.services;

import com.techverito.sales.entertaintment.BaseBmmTest;
import com.techverito.sales.entertaintment.bmm.constants.AvailabilityStatus;
import com.techverito.sales.entertaintment.bmm.constants.SeatState;
import com.techverito.sales.entertaintment.bmm.constants.SeatType;
import com.techverito.sales.entertaintment.bmm.domain.Seat;
import com.techverito.sales.entertaintment.bmm.domain.sitting.arrangement.Arrangement;
import com.techverito.sales.entertaintment.bmm.services.booking.BookingService;
import com.techverito.sales.entertaintment.bmm.services.booking.BookingServiceImpl;
import com.techverito.sales.entertaintment.helper.BookingUnitTestHelper;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookingServiceTest extends BaseBmmTest {

    private BookingService bookingService;
    private List<Seat> seats;

    @Before
    public void setup(){
        bookingService = new BookingServiceImpl();

        List<Seat> allAvailableSeats = unitTestHelper.getShow(41002L).
                getArrangements().stream()
                .filter(a->a.getSeatType().equals(SeatType.GOLD))
                .map(Arrangement::getSeats).findFirst().get();
        seats = allAvailableSeats.subList(0,5);
    }

    @Test
    public void test_reserving_seats(){
        AvailabilityStatus status = bookingService.reserveSitting(seats);
        assertEquals(AvailabilityStatus.AVAILABLE,status);
    }

    @Test
    public void test_reserving_booked_seats(){
        seats.forEach(s -> s.setState(SeatState.ALLOCATED));
        AvailabilityStatus status = bookingService.reserveSitting(seats);
        assertEquals(AvailabilityStatus.UNAVAILABLE,status);
    }

    @Test
    public void test_release_holding_tickets() throws InterruptedException {
        bookingService.reserveSitting(seats);
        Thread.sleep(5000);
        seats.forEach(s -> assertEquals(SeatState.UNALLOCATED,s.getState()));
    }
}
