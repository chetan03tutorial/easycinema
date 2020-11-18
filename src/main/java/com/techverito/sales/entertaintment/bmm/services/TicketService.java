package com.techverito.sales.entertaintment.bmm.services;

import com.techverito.sales.entertaintment.bmm.domain.Seat;
import com.techverito.sales.entertaintment.bmm.domain.Booking;

import java.util.List;
import java.util.Set;

public interface TicketService {
    Booking bookTicket(Long showId, Set<String> mnemonics);
    void confirmSitting(List<Seat> seats);
    void updatePaymentStatus(List<Seat> seats);
}
