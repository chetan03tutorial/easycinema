package com.techverito.sales.entertaintment.bmm.domain.sitting.arrangement;

import com.techverito.sales.entertaintment.bmm.constants.SeatType;

/**
 * Abstract Factory to create components of the show i.e.
 * Auditorium, Event, EventSchedule
 */
public class AuditoriumArrangementBuilder{

    public static Arrangement buildArrangement(String type, Integer capacity) {
        Arrangement arrangement = null;
        SeatType category = SeatType.valueOf(type);
        switch (category){
            case PLATINUM:
                arrangement = new PlatinumArrangement(capacity);
                break;
            case GOLD:
                arrangement = new GoldArrangement(capacity);
                break;
            case SILVER:
                arrangement = new SilverArrangement(capacity);
                break;
        }
        return arrangement;
    }
}
