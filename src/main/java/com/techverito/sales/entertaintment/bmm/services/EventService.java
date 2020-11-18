package com.techverito.sales.entertaintment.bmm.services;

import com.techverito.sales.entertaintment.bmm.domain.Event;
import com.techverito.sales.entertaintment.bmm.model.EventModel;

public interface EventService {
    Event createEvent(EventModel model);
    Event getEventDetails(Long eventId);
}
