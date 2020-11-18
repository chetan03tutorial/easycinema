package com.techverito.sales.entertaintment.bmm.services.booking;

import com.techverito.sales.entertaintment.ApplicationConfiguration;
import com.techverito.sales.entertaintment.bmm.constants.AvailabilityStatus;
import com.techverito.sales.entertaintment.bmm.constants.SeatState;
import com.techverito.sales.entertaintment.bmm.domain.Seat;
import lombok.Data;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.techverito.sales.entertaintment.bmm.constants.BmmConstants.DEFAULT_TIME_TO_EXPIRE;
import static java.util.stream.Collectors.toList;

//import java.util.concurrent.BlockingQueue;


public class BookingServiceImpl implements  BookingService {

    /**
     * A thread would run in the background which constantly poll the payment status of the
     * BookingBox and clear out the booking if status is not payment completed
     */
    private static final Lock seatLock = new ReentrantLock(true);
    private final BookingQueue bookingQueue;
    // In real life, we would have a separate thread pool for running multiple task for cleanup
    private final CleanupService cleanupService;

    private static final long ttl = Long.valueOf(ApplicationConfiguration.getValue(DEFAULT_TIME_TO_EXPIRE));

    /*private static final Long ttl = Long.valueOf(System.getenv("tte"));*/
    private static final long tte = Long.valueOf(ttl)+2l;

    public BookingServiceImpl(){

        this.bookingQueue = new BookingQueue();
        cleanupService = new CleanupService(bookingQueue);
        cleanupService.runCleanupService();
    }

    @Override
    public AvailabilityStatus reserveSitting(List<Seat> selectedSeats) {
        AvailabilityStatus availabilityStatus = AvailabilityStatus.AVAILABLE;
        boolean isAcquired = seatLock.tryLock();
        try{
            if(isAcquired){
                List<Seat> unavailable = unavailableSeats(selectedSeats);
                if(unavailable.size() != 0){
                    availabilityStatus = AvailabilityStatus.UNAVAILABLE;
                    //status.setStatus(ReservationState.UNAVAILABLE);
                    //status.setUnavailable(unavailable);
                }else{
                    selectedSeats.forEach(s ->  s.setState(SeatState.PAYMENT_PENDING));
                    bookingQueue.queueSeats(selectedSeats);
                    availabilityStatus = AvailabilityStatus.AVAILABLE;
                    //status.setAvailable(selectedSeats);
                    //status.setStatus(ReservationState.AVAILABLE);
                }
            }
        }/*catch (Exception ex){
            //TODO think of error handling strategy here
            status.setStatus(ReservationState.UNAVAILABLE);
        }*/finally {
            seatLock.unlock();
        }
        return availabilityStatus;
    }



    private List<Seat> unavailableSeats(List<Seat> selection){
        return selection.stream()
                .filter((Seat seat) -> ! seat.getState().equals(SeatState.UNALLOCATED))
                .collect(toList());
    }


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
            seats.addAll(selection);
            updatedAt = LocalTime.now();
        }
    }

    private class BookingQueue {

        //private final long tte;
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

        // If there are multiple threads running clean up task then
        // we would need to implement this method under locking
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
                    if (isExpired > 0) {

                        List<Seat> pendingPayment = seatStream
                                .filter(s -> s.getState().equals(SeatState.PAYMENT_PENDING))
                                .collect(Collectors.toList());
                        // Some or all of the seats in the ticket have pending payment state
                        if(pendingPayment.size() != 0){
                            seats.forEach(s -> s.setState(SeatState.UNALLOCATED));
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


    private class CleanupService implements BookingCleanupService{

        private final BookingQueue bookingQueue;

        public CleanupService(BookingQueue bookingQueue){
            this.bookingQueue = bookingQueue;
        }

        public void runCleanupService() {
            Thread cleanupThread = new Thread(bookingQueue::release);
            cleanupThread.start();
        }
    }
}
