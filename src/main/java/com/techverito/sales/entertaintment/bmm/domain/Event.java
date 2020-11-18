package com.techverito.sales.entertaintment.bmm.domain;

import com.techverito.sales.entertaintment.bmm.sequences.LongSequenceGenerator;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class Event {
    private static final LongSequenceGenerator longSequenceGenerator = new LongSequenceGenerator(92000L);
    // TODO change the field to final once we have the storage layer
    private Long id;
    // TODO change the field to final once we have the storage layer
    private String name;
    // TODO change the field to final once we have the storage layer
    private String description;
    // TODO change the field to final once we have the storage layer
    private String type;
    // TODO change the field to final once we have the storage layer
    private EventSchedule schedule;

    // TODO
    //  Make it private or remove it, once we start reading data from datastore.
    //  As of now, due to jackson library, we are keeping it public
    public Event(){

    }

    private Event(EventBuilder builder){
        this.id = longSequenceGenerator.getNext();
        this.name = builder.eventName;
        this.description = builder.eventDesc;
        this.type = builder.eventType;
    }

    @Data
    private class EventSchedule {
        private LocalDateTime startTime;
        private LocalDateTime endTime;
    }



    public static class EventBuilder {

        private final String eventName;
        private String eventDesc;
        private final String eventType;

        public EventBuilder(String name,String type){
            this.eventName = name;
            this.eventType = type;
        }

        public EventBuilder description(String description){
            this.eventDesc = description;
            return this;
        }

        public Event build(){
            return new Event(this);
        }
    }
}
