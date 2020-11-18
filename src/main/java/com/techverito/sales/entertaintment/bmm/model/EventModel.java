package com.techverito.sales.entertaintment.bmm.model;

import com.techverito.sales.entertaintment.bmm.domain.Event;
import com.techverito.sales.entertaintment.bmm.domain.EventSchedule;
import lombok.Data;

@Data
public class EventModel {
    private String name;
    private String description;
    private String type;
}
