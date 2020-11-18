package com.techverito.sales.entertaintment.services;

import com.techverito.sales.entertaintment.BaseBmmTest;
import com.techverito.sales.entertaintment.bmm.domain.Event;
import com.techverito.sales.entertaintment.bmm.model.EventModel;
import com.techverito.sales.entertaintment.bmm.services.EventService;
import com.techverito.sales.entertaintment.bmm.services.EventServiceImpl;
import com.techverito.sales.entertaintment.bmm.storage.BaseRepository.EventRepository;
import com.techverito.sales.entertaintment.bmm.util.FileReader;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static com.techverito.sales.entertaintment.bmm.util.JsonReader.JSON_READER;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EventServiceTest extends BaseBmmTest {

    private EventService eventService;
    private EventRepository eventRepository;

    @Before
    public void setup(){
        this.eventRepository = mock(EventRepository.class);
        this.eventService = new EventServiceImpl(eventRepository);
    }

    @Test
    public void create_event(){
        List<Event> events = FileReader.readFile("json/events/","json").stream()
                .map(f -> JSON_READER.readJson(f, EventModel.class))
                .map(eventService::createEvent)
                .collect(toList());
        verify(eventRepository, times(events.size())).save(any(Event.class));
    }

    @Test
    public void get_auditorium_details(){
        Event expected  = new Event.EventBuilder("The Technical Vertios","Movie").build();
        when(eventRepository.get(any(Predicate.class))).thenReturn(Optional.of(expected));
        Event actual = eventService.getEventDetails((long)1000);
        assertEquals(actual.getName(),expected.getName());
    }
}
