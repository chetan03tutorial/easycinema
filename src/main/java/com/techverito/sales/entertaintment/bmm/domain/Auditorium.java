package com.techverito.sales.entertaintment.bmm.domain;

import com.techverito.sales.entertaintment.bmm.constants.BmmConstants;
import com.techverito.sales.entertaintment.bmm.sequences.LongSequenceGenerator;
import lombok.Getter;

public class Auditorium {
    private static final LongSequenceGenerator auditoriumIdGenerator = new LongSequenceGenerator(21000L);
    // TODO change the field to final once we have the storage layer
    @Getter private Long id;
    // TODO change the field to final once we have the storage layer
    @Getter private String name;
    // TODO change the field to final once we have the storage layer
    @Getter private Short number;
    // TODO change the field to final once we have the storage layer
    @Getter private Integer maxCapacity;


    // TODO
    //  Make it private or remove it, once we start reading data from datastore.
    //  As of now, due to jackson library, we are keeping it public
    public Auditorium(){

    }

    private Auditorium(AuditoriumBuilder auditoriumBuilder) {
        this.id = auditoriumIdGenerator.getNext();
        this.name = auditoriumBuilder.name;
        this.maxCapacity = auditoriumBuilder.maxCapacity;
        this.number = auditoriumBuilder.number;
    }


    public static class AuditoriumBuilder {
        private String name;
        private Short number;
        private Integer maxCapacity;


        public AuditoriumBuilder(){
        }

        public AuditoriumBuilder name(String name){
            this.name = name;
            return this;
        }

        public AuditoriumBuilder screen(short number){
            this.number = number;
            return this;
        }

        public AuditoriumBuilder capacity(int maxCapacity){
            this.maxCapacity = maxCapacity;
            return this;
        }

        public Auditorium build(){
            return new Auditorium(this);
        }

    }

    public String toString() {
        return this.getId() + BmmConstants.BLANK_SPACE + this.name;
    }
}
