package com.techverito.sales.entertaintment.bmm.services.booking;


import com.techverito.sales.entertaintment.bmm.constants.AvailabilityStatus;
import com.techverito.sales.entertaintment.bmm.domain.Seat;

import java.util.List;

public interface BookingService {
    AvailabilityStatus reserveSitting(List<Seat> seats);

}
