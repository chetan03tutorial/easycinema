package com.techverito.sales.entertaintment.bmm.services.booking;

public class BookingCleanupServiceImpl implements BookingCleanupService{

    private final BookingQueue bookingQueue;

    public BookingCleanupServiceImpl(BookingQueue bookingQueue){
        this.bookingQueue = bookingQueue;
    }

    public void runCleanupService() {
        Thread cleanupThread = new Thread(bookingQueue::release);
        cleanupThread.start();
    }

}
