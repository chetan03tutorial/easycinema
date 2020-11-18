package com.techverito.sales.entertaintment.bmm.domain;

import com.techverito.sales.entertaintment.bmm.constants.BmmConstants;
import com.techverito.sales.entertaintment.bmm.constants.SeatType;
import com.techverito.sales.entertaintment.bmm.domain.Auditorium;
import com.techverito.sales.entertaintment.bmm.domain.Event;
import com.techverito.sales.entertaintment.bmm.domain.EventSchedule;
import com.techverito.sales.entertaintment.bmm.domain.sitting.arrangement.Arrangement;
import com.techverito.sales.entertaintment.bmm.sequences.LongSequenceGenerator;
import com.techverito.sales.entertaintment.bmm.services.EventManager;
import lombok.Data;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Data
public class Show {
    private static final LongSequenceGenerator showIdGenerator = new LongSequenceGenerator(41000L);

    @Getter
    private Long showId;
    private Event event;
    private Auditorium auditorium;
    private EventSchedule eventSchedule;
    private List<Arrangement> arrangements;

    public Show(){
        this.showId = showIdGenerator.getNext();
        arrangements = new LinkedList<>();
    }

    public void arrangement(Arrangement an){
        arrangements.add(an);
    }

}
