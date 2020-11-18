package com.techverito.sales.entertaintment.services;

import com.techverito.sales.entertaintment.BaseBmmTest;
import com.techverito.sales.entertaintment.bmm.domain.Auditorium;
import com.techverito.sales.entertaintment.bmm.domain.Event;
import com.techverito.sales.entertaintment.bmm.domain.Show;
import com.techverito.sales.entertaintment.bmm.model.ShowModel;
import com.techverito.sales.entertaintment.bmm.services.*;
import com.techverito.sales.entertaintment.bmm.services.pricing.PricingService;
import com.techverito.sales.entertaintment.bmm.services.pricing.PricingServiceImpl;
import com.techverito.sales.entertaintment.helper.BookingUnitTestHelper;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.mockito.Mockito.*;


import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EventManagerTest extends BaseBmmTest {

    private EventManager eventManager;
    private EventService eventService;
    private AuditoriumService auditoriumService;
    private PricingService pricingService;
    private ShowService showService;
    //private BookingUnitTestHelper helper;

    @Before
    public void setup(){
        eventService = mock(EventServiceImpl.class);
        showService = mock(ShowServiceImpl.class);
        pricingService = mock(PricingServiceImpl.class);
        auditoriumService = mock(AuditoriumServiceImpl.class);
        unitTestHelper = new BookingUnitTestHelper();
        eventManager = new EventManagerImpl(eventService,auditoriumService,showService,pricingService);
    }

    @Test
    public void organize_show(){

        ShowModel show = unitTestHelper.getShowModels().get(0);
        Event event = unitTestHelper.getEvent(show.getEventId());
        when(eventService.getEventDetails(any(Long.class))).thenReturn(event);
        doNothing().when(showService).organizeShow(any(Show.class));
        Auditorium auditorium = unitTestHelper.getAuditorium(show.getAuditoriumId());
        when(auditoriumService.getAuditorium(any(Long.class))).thenReturn(auditorium);
        doNothing().when(pricingService).assignCost(any(Long.class),any(Map.class));
        Show showDetails = eventManager.organizeShow(show);
        assertEquals(show.getEventId(),showDetails.getEvent().getId());
        assertEquals(show.getAuditoriumId(),showDetails.getAuditorium().getId());

    }
}
