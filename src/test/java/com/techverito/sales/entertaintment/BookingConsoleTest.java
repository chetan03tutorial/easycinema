package com.techverito.sales.entertaintment;

import com.techverito.sales.entertaintment.bmm.constants.BookingState;
import com.techverito.sales.entertaintment.bmm.constants.SeatType;
import com.techverito.sales.entertaintment.bmm.domain.Seat;
import com.techverito.sales.entertaintment.bmm.exception.InsufficientFundException;
import com.techverito.sales.entertaintment.bmm.exception.NotFoundException;
import com.techverito.sales.entertaintment.bmm.domain.Booking;
import com.techverito.sales.entertaintment.bmm.services.ShowService;
import com.techverito.sales.entertaintment.bmm.services.ShowServiceImpl;
import com.techverito.sales.entertaintment.bmm.services.TicketService;
import com.techverito.sales.entertaintment.bmm.services.TicketServiceImpl;
import com.techverito.sales.entertaintment.bmm.services.payment.PaymentService;
import com.techverito.sales.entertaintment.bmm.services.payment.PaymentServiceImpl;
import com.techverito.sales.entertaintment.bmm.util.Logger;
import com.techverito.sales.entertaintment.bmm.util.UserInputReader;
import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

public class BookingConsoleTest extends BaseBmmTest {

    private BookingConsole bookingConsole;
    private ShowService showService;
    private TicketService ticketService;
    private UserInputReader userInputReader;
    private PaymentService paymentService;
    private Logger logger;


    @Before
    public void setup(){

        userInputReader = mock(UserInputReader.class);
        showService = mock(ShowServiceImpl.class);
        ticketService = mock(TicketServiceImpl.class);
        paymentService = mock(PaymentServiceImpl.class);
        logger = mock(Logger.class);
        bookingConsole = new BookingConsole(showService,ticketService,paymentService,userInputReader,logger);
    }

    @Test
    public void test_console_with_valid_booking_details(){

        List<Seat> allAvailableSeats = unitTestHelper.getShow(41002l).
                getArrangements().stream()
                .filter(a->a.getSeatType().equals(SeatType.GOLD))
                .map(a -> a.getSeats()).findFirst().get();
        Set<String> mnemonics = allAvailableSeats.stream().map(Seat::getMnemonic).collect(Collectors.toSet());

        Booking booking = Booking.confirmBooking(mnemonics,120d,800d);
        when(userInputReader.captureAction()).thenReturn("B");
        when(userInputReader.captureShow()).thenReturn("41002");
        when(userInputReader.captureSeats()).thenReturn("G1,G2");
        when(userInputReader.capturePayment()).thenReturn("920");
        when(showService.getShows()).thenReturn(unitTestHelper.getShows());
        when(ticketService.bookTicket(anyLong(),anySetOf(String.class))).thenReturn(booking);
        Booking actualBooking = bookingConsole.runConsole();
        bookingConsole.displayBookingStatus(actualBooking);
        assertEquals(actualBooking.getBookingId(),booking.getBookingId());
    }


    @Test
    public void test_console_with_invalid_formatted_show_id(){
        when(userInputReader.captureAction()).thenReturn("B");
        when(userInputReader.captureShow()).thenReturn("4B001");
        bookingConsole.runConsole();
        verify(logger,times(1)).log(any(NumberFormatException.class));
    }

    @Test
    public void test_console_when_invalid_show_id_is_punched(){
        when(userInputReader.captureAction()).thenReturn("B");
        when(userInputReader.captureShow()).thenReturn("41005");
        when(userInputReader.captureSeats()).thenReturn("G1,G2");
        when(userInputReader.capturePayment()).thenReturn("920");
        when(ticketService.bookTicket(anyLong(),anySetOf(String.class))).thenThrow(NotFoundException.class);
        bookingConsole.runConsole();
        verify(logger,times(1)).log(any(NotFoundException.class));
    }

    @Test
    public void test_console_when_invalid_seats_are_punched(){
        when(userInputReader.captureAction()).thenReturn("B");
        when(userInputReader.captureShow()).thenReturn("41005");
        when(userInputReader.captureSeats()).thenReturn("GB11,GB12");
        when(userInputReader.capturePayment()).thenReturn("920");
        when(ticketService.bookTicket(anyLong(),anySetOf(String.class))).thenThrow(NotFoundException.class);
        bookingConsole.runConsole();
        verify(logger,times(1)).log(any(NotFoundException.class));
    }

    @Test
    public void test_console_when_paidAmount_isLessThan_dueAmount(){
        when(userInputReader.captureAction()).thenReturn("B");
        when(userInputReader.captureShow()).thenReturn("41005");
        when(userInputReader.captureSeats()).thenReturn("GB11,GB12");
        when(userInputReader.capturePayment()).thenReturn("920");
        when(ticketService.bookTicket(anyLong(),anySetOf(String.class))).thenThrow(new InsufficientFundException("Pay Full Amount"));
        bookingConsole.runConsole();
        verify(logger,times(1)).log(any(InsufficientFundException.class));
    }

}
