package com.techverito.sales.entertaintment.bmm.services;

import com.techverito.sales.entertaintment.bmm.domain.Event;
import com.techverito.sales.entertaintment.bmm.exception.NotFoundException;
import com.techverito.sales.entertaintment.bmm.model.EventModel;
import com.techverito.sales.entertaintment.bmm.storage.BaseRepository.EventRepository;

public class EventServiceImpl implements EventService{

    private static final String EVENT_NOT_FOUND = "Event Not Found %d";
    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository){
        this.eventRepository = eventRepository;
    }

    public Event createEvent(EventModel model) {
        Event.EventBuilder builder = new Event.EventBuilder(model.getName(),model.getType());
        Event event = builder.build();
        eventRepository.save(event);
        return event;
    }

    public Event getEventDetails(Long eventId){
        return eventRepository.get(e -> eventId.equals(e.getId()))
                .orElseThrow(() -> new NotFoundException(String.format(EVENT_NOT_FOUND, eventId)));
    }

}
