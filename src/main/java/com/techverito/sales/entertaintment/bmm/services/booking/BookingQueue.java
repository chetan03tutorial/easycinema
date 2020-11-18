package com.techverito.sales.entertaintment.bmm.services.booking;

import com.techverito.sales.entertaintment.ApplicationConfiguration;
import com.techverito.sales.entertaintment.bmm.constants.BmmConstants;
import com.techverito.sales.entertaintment.bmm.domain.Seat;
import com.techverito.sales.entertaintment.bmm.constants.SeatState;
import lombok.Data;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BookingQueue {

    @Data
    private class TicketBox{
        final List<Seat> seats;
        final LocalTime createdAt;
        volatile LocalTime updatedAt;
        volatile SeatState bookingStatus;

        private TicketBox(){
            this.seats = new LinkedList<>();
            this.createdAt = LocalTime.now();
        }

        private void addSeats(List<Seat> selection){
            selection.forEach(seats::add);
            updatedAt = LocalTime.now();
        }
    }

    private static final long tte = Long.valueOf(ApplicationConfiguration.getValue(BmmConstants.DEFAULT_TIME_TO_EXPIRE));
    private final BlockingQueue<TicketBox> ticketQueue;

    public BookingQueue(/*long tte*/){
        //this.tte = tte;
        ticketQueue = new LinkedBlockingDeque<>();
    }

    public void queueSeats(List<Seat> selectedSeats){
        TicketBox ticketBox = new TicketBox();
        ticketBox.addSeats(selectedSeats);
        ticketQueue.add(ticketBox);
    }

    public void release(){
        TicketBox ticketBox;
        List<Seat> seats;
        Stream<Seat> seatStream ;
        while(true) {
            if (!ticketQueue.isEmpty()) {
                ticketBox = ticketQueue.peek();
                seats = ticketBox.getSeats();
                seatStream = seats.stream();
                LocalTime now = LocalTime.now();
                int isExpired = now.minusSeconds(tte).compareTo(ticketBox.getUpdatedAt());
                if (isExpired == 1) {
                    List<Seat> pendingPayment = seatStream
                            .filter(s -> s.getState().equals(SeatState.PAYMENT_PENDING))
                            .collect(Collectors.toList());

                    // Some or all of the seats in the ticket were not booked
                    if(pendingPayment.size() != 0){
                        seats.stream().forEach(s -> s.setState(SeatState.UNALLOCATED));
                    }
                    ticketQueue.remove(ticketBox);
                } else {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
