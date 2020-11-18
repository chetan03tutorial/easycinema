package com.techverito.sales.entertaintment.helper;

import com.techverito.sales.entertaintment.bmm.constants.PaymentState;
import com.techverito.sales.entertaintment.bmm.constants.SeatType;
import com.techverito.sales.entertaintment.bmm.domain.Auditorium;
import com.techverito.sales.entertaintment.bmm.domain.Event;
import com.techverito.sales.entertaintment.bmm.domain.Show;
import com.techverito.sales.entertaintment.bmm.model.AuditoriumModel;
import com.techverito.sales.entertaintment.bmm.model.EventModel;
import com.techverito.sales.entertaintment.bmm.domain.Payment;
import com.techverito.sales.entertaintment.bmm.model.ShowModel;
import com.techverito.sales.entertaintment.bmm.util.FileReader;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.*;

import static com.techverito.sales.entertaintment.bmm.util.JsonReader.JSON_READER;

@Data
public class BookingUnitTestHelper {


    private static List<ShowModel> showModels;
    private static List<EventModel> eventModels;
    private static List<AuditoriumModel> auditoriumModels;
    private static List<Auditorium> auditoriums;
    private static List<Event> events;
    private static List<Show> shows;

    public BookingUnitTestHelper(){

        auditoriumModels = TestResourceReader.readFile("json/auditoriums/","json").stream()
                .map(f -> JSON_READER.readJson(f, AuditoriumModel.class))
                .collect(toList());

        auditoriums = TestResourceReader.readFile("test-data/json/auditoriums/","json").stream()
                .map(f -> JSON_READER.readJson(f, Auditorium.class))
                .collect(toList());

        //auditoriumModels.stream().map(this::createAuditorium).collect(toList());

        eventModels = TestResourceReader.readFile("json/events/","json").stream()
                .map(f -> JSON_READER.readJson(f, EventModel.class))
                .collect(toList());

        events = TestResourceReader.readFile("test-data/json/events/","json").stream()
                .map(f -> JSON_READER.readJson(f, Event.class))
                .collect(toList());

        /*events = eventModels.stream().map(this::createEvent).collect(toList());*/

        showModels = TestResourceReader.readFile("json/shows","json").stream()
                .map(f -> JSON_READER.readJson(f, ShowModel.class))
                .collect(toList());

        shows = TestResourceReader.readFile("test-data/json/shows","json").stream()
                .map(f -> JSON_READER.readJson(f, Show.class))
                .collect(toList());

        //shows = showModels.stream().map(this::createShow).collect(toList());
    }

    private Show createShow(ShowModel showModel) {
        return new Show();
    }

    private Auditorium createAuditorium(AuditoriumModel auditoriumModel) {
        return new Auditorium.AuditoriumBuilder()
                .name(auditoriumModel.getName())
                .capacity(auditoriumModel.getMaxCapacity())
                .screen(auditoriumModel.getNumber())
                .build();
    }

    private Event createEvent(EventModel eventModel) {
        return new Event.EventBuilder(eventModel.getName(),eventModel.getType()).build();
    }

    public List<AuditoriumModel> getAuditoriumModels(){
        return FileReader.readFile("json/auditoriums/","json").stream()
                .map(f -> JSON_READER.readJson(f, AuditoriumModel.class))
                .collect(toList());

    }

    public List<ShowModel> getShowModels(){
        return FileReader.readFile("json/shows","json").stream()
                .map(f -> JSON_READER.readJson(f, ShowModel.class))
                .collect(toList());

    }

    public List<EventModel> getEventModels(){
        return FileReader.readFile("json/events/","json").stream()
                .map(f -> JSON_READER.readJson(f, EventModel.class))
                .collect(toList());
    }

    public Event getEvent(Long eventId) {
        System.out.println(eventId);
        Optional<Event> event = events.stream().filter(e -> e.getId().equals(eventId)).findFirst();
        return event.get();
    }

    public Auditorium getAuditorium(Long audId){
        Optional<Auditorium> auditorium = auditoriums.stream().filter(a -> a.getId().equals(audId)).findFirst();
        return auditorium.get();

    }

    public Show getShow(Long showId){
        Optional<Show> show = shows.stream().filter(s -> s.getShowId().equals(showId)).findFirst();
        return show.get();
    }

    public List<Show> getShows(){
        return shows;
    }

    public Map<SeatType,Double> getPriceMap(){
        Map<SeatType,Double> priceMap = new HashMap<>();
        priceMap.put(SeatType.PLATINUM,500.0);
        priceMap.put(SeatType.GOLD,400.0);
        priceMap.put(SeatType.SILVER,200.0);
        return priceMap;
    }

    public Payment getPaymentStatus(PaymentState state){
        Payment payment = new Payment();
        payment.setPaidAmount(900d);
        payment.setDueAmount(500d);
        payment.setState(state);
        return payment;
    }

}
