package com.techverito.sales.entertaintment;

import com.techverito.sales.entertaintment.bmm.constants.SeatState;
import com.techverito.sales.entertaintment.bmm.constants.SeatType;
import com.techverito.sales.entertaintment.bmm.domain.Show;
import com.techverito.sales.entertaintment.bmm.domain.Seat;
import com.techverito.sales.entertaintment.bmm.domain.sitting.arrangement.Arrangement;
import com.techverito.sales.entertaintment.bmm.exception.InsufficientFundException;
import com.techverito.sales.entertaintment.bmm.exception.NotFoundException;
import com.techverito.sales.entertaintment.bmm.exception.PaymentGatewayException;
import com.techverito.sales.entertaintment.bmm.domain.Booking;
import com.techverito.sales.entertaintment.bmm.services.ShowService;
import com.techverito.sales.entertaintment.bmm.services.TicketService;
import com.techverito.sales.entertaintment.bmm.services.payment.PaymentService;
import com.techverito.sales.entertaintment.bmm.util.Logger;
import com.techverito.sales.entertaintment.bmm.util.UserInputReader;

import java.util.*;

import static com.techverito.sales.entertaintment.bmm.constants.BmmConstants.BLANK_SPACE;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;


public class BookingConsole
{

    private final ShowService showService;
    private final TicketService ticketService;
    private final UserInputReader userInputReader;
    private final Logger logger;

    public BookingConsole(ShowService showService,TicketService ticketService, PaymentService paymentService, UserInputReader userInputReader, Logger logger){
        this.showService = showService;
        this.ticketService = ticketService;
        this.userInputReader = userInputReader;
        this.logger = logger;
    }

    public Booking runConsole(){
        Booking bookingStatus = null;
        displayShows(showService.getShows());
        try{
            System.out.println();
            Long showId = Long.valueOf(userInputReader.captureShow());
            String selection = userInputReader.captureSeats();
            List<String> seatMnemonics = Arrays.asList(selection.split(","));
            Set<String> mnemonics = new HashSet<>(seatMnemonics);
            bookingStatus = ticketService.bookTicket(showId,mnemonics);
        }catch (NumberFormatException ex){
            System.out.println("Enter Input in correct format");
            logger.log(ex);
        }
        catch (NotFoundException |InsufficientFundException | PaymentGatewayException ex){
            System.out.println(ex.getMessage());
            logger.log(ex);
        }
        return bookingStatus;
    }

    public void displayBookingStatus(Booking booking) {
        System.out.println("********************* Booking Information *****************************");
        displayBookingFields(booking.getBookingId(),"Booking Id: ");
        displayBookingFields(booking.getState(),"Booking Status: ");
        displayBookingFields(booking.getFailureReason(),"Reason: ");
        displayBookingFields(booking.getSubtotal(),"Subtotal: ");
        displayBookingFields(booking.getTax(),"Taxes: ");
        displayBookingFields(booking.getTotal(),"Total Cost: ");
        displayBookingFields(booking.getAvailable(), "Seats: ");
        System.out.println("***********************************************************************");
    }

    private void displayBookingFields(Object value,String message){
        if(Optional.ofNullable(value).isPresent()){
            System.out.println(message + value);
        }
    }

    private void displayShows(List<Show> shows){
        for(Show s : shows){
            System.out.println("\n" +
                    "Show " + s.getShowId() + " : " + s.getEvent().getName() +
                    BLANK_SPACE +
                    "running in " + s.getAuditorium().getName());

            Map<SeatType,List<Seat>> sitting = s.getArrangements().stream()
                    .collect(toMap(Arrangement::getCategory,
                            a -> a.getSeats().stream()
                                    .filter((Seat seat) -> SeatState.UNALLOCATED.equals(seat.getState()))
                                    .collect(toList())));
            sitting.forEach((seatType,seats) -> System.out.println(seatType + " : " + seats));
        }
    }

    // In real life, we would have a clean up thread to persist the bookings present in queue
    /*private static class CleanupService extends Thread{
        @Override
        public void run(){
            System.out.println("Persisting the existing bookings and closing the queue");
        }
    }*/


}
