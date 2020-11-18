package com.techverito.sales.entertaintment.bmm.services;

import com.techverito.sales.entertaintment.bmm.constants.SeatType;
import com.techverito.sales.entertaintment.bmm.domain.Show;
import com.techverito.sales.entertaintment.bmm.domain.sitting.arrangement.Arrangement;
import com.techverito.sales.entertaintment.bmm.domain.sitting.arrangement.AuditoriumArrangementBuilder;
import com.techverito.sales.entertaintment.bmm.model.Capacity;
import com.techverito.sales.entertaintment.bmm.model.ShowModel;
import com.techverito.sales.entertaintment.bmm.services.pricing.PricingService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class EventManagerImpl implements EventManager {

    // use the showOrganizer ( Abstract Factory ) to create the component of the show

    private final EventService eventService;
    private final AuditoriumService auditoriumService;
    private final PricingService pricingService;
    private final ShowService showService;

    public EventManagerImpl(EventService eventService,
                            AuditoriumService auditoriumService,
                            ShowService showService,
                            PricingService pricingService){
        this.eventService = eventService;
        this.auditoriumService = auditoriumService;
        this.showService = showService;
        this.pricingService = pricingService;
    }

    public Show organizeShow(ShowModel model) {

        Show show = new Show();
        show.setEvent(eventService.getEventDetails(model.getEventId()));
        show.setAuditorium(auditoriumService.getAuditorium(model.getAuditoriumId()));
        Stream<Capacity> stream = model.getCapacities().stream();
        List<Arrangement> arrangements = stream
                .map(c -> AuditoriumArrangementBuilder.buildArrangement(c.getCode(),c.getCapacity()))
                .collect(toList());
        show.setArrangements(arrangements);
        showService.organizeShow(show);
        Map<SeatType,Double> priceTable = new HashMap<>();
        model.getPricing().forEach((k,v) ->
                priceTable.put(SeatType.valueOf(k.toUpperCase()),v));
        pricingService.assignCost(show.getShowId(), priceTable);
        return show;
    }


}
