package com.techverito.sales.entertaintment.services;

import com.techverito.sales.entertaintment.ApplicationConfiguration;
import com.techverito.sales.entertaintment.BaseBmmTest;
import com.techverito.sales.entertaintment.bmm.constants.SeatState;
import com.techverito.sales.entertaintment.bmm.constants.SeatType;
import com.techverito.sales.entertaintment.bmm.domain.Seat;
import com.techverito.sales.entertaintment.bmm.services.booking.BookingCleanupService;
import com.techverito.sales.entertaintment.bmm.services.booking.BookingCleanupServiceImpl;
import com.techverito.sales.entertaintment.bmm.services.booking.BookingQueue;
import com.techverito.sales.entertaintment.helper.BookingUnitTestHelper;
import com.techverito.sales.entertaintment.helper.TestResourceReader;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class BookingCleanupServiceTest extends BaseBmmTest {

    private BookingQueue bookingQueue;
    private BookingCleanupService bookingCleanupService;
    //private BookingUnitTestHelper unitTestHelper = new BookingUnitTestHelper();
    private List<Seat> seats;

    @Before
    public void setup(){
        /*TestResourceReader.readFile("conf/","properties").stream()
                .forEach(ApplicationConfiguration::loadProperties);*/
        bookingQueue = new BookingQueue();
        unitTestHelper = new BookingUnitTestHelper();
        bookingCleanupService = new BookingCleanupServiceImpl(bookingQueue);
        List<Seat> allAvailableSeats = unitTestHelper.getShow(41002l)
                .getArrangements().stream()
                .filter(a->a.getSeatType().equals(SeatType.GOLD))
                .map(a -> a.getSeats()).findFirst().get();
        seats = allAvailableSeats.subList(0,5);
        bookingQueue.queueSeats(seats);
    }

    @Test
    public void test_release_holding_tickets() throws InterruptedException {
        seats.forEach(s -> s.setState(SeatState.PAYMENT_PENDING));
        bookingCleanupService.runCleanupService();
        Thread.sleep(3000);
    }
}
