package com.techverito.sales.entertaintment;

import com.techverito.sales.entertaintment.bmm.domain.Booking;
import com.techverito.sales.entertaintment.bmm.exception.InsufficientFundException;
import com.techverito.sales.entertaintment.bmm.exception.NotFoundException;
import com.techverito.sales.entertaintment.bmm.exception.PaymentGatewayException;
import com.techverito.sales.entertaintment.bmm.model.AuditoriumModel;
import com.techverito.sales.entertaintment.bmm.model.EventModel;
import com.techverito.sales.entertaintment.bmm.model.ShowModel;
import com.techverito.sales.entertaintment.bmm.services.*;
import com.techverito.sales.entertaintment.bmm.services.booking.*;
import com.techverito.sales.entertaintment.bmm.services.payment.IBPaymentGateway;
import com.techverito.sales.entertaintment.bmm.services.payment.PaymentGateway;
import com.techverito.sales.entertaintment.bmm.services.payment.PaymentServiceImpl;
import com.techverito.sales.entertaintment.bmm.services.pricing.PricingService;
import com.techverito.sales.entertaintment.bmm.services.pricing.PricingServiceImpl;
import com.techverito.sales.entertaintment.bmm.services.taxation.KrishiKalyanTaxCalculator;
import com.techverito.sales.entertaintment.bmm.services.taxation.ServiceTaxCalculator;
import com.techverito.sales.entertaintment.bmm.services.taxation.SwachBharatTaxCalculator;
import com.techverito.sales.entertaintment.bmm.services.taxation.TaxService;
import com.techverito.sales.entertaintment.bmm.storage.BaseRepository.AuditoriumRepository;
import com.techverito.sales.entertaintment.bmm.storage.BaseRepository.CostingRepository;
import com.techverito.sales.entertaintment.bmm.storage.BaseRepository.EventRepository;
import com.techverito.sales.entertaintment.bmm.storage.BaseRepository.ShowRepository;
import com.techverito.sales.entertaintment.bmm.util.FileReader;
import com.techverito.sales.entertaintment.bmm.util.Logger;
import com.techverito.sales.entertaintment.bmm.util.UserInputReader;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.techverito.sales.entertaintment.bmm.util.JsonReader.JSON_READER;

public class BookingConsoleRunner {

    private static final AuditoriumRepository auditoriumRepository ;
    private static final EventRepository eventRepository ;
    private static final ShowRepository showRepository;
    private static final CostingRepository pricingRepository;
    private static final TaxService taxService;
    private static final ShowServiceImpl showService ;
    private static final AuditoriumService auditoriumService;
    private static final TicketServiceImpl ticketService;
    private static final EventServiceImpl eventService;
    private static final BookingService bookingService;
    private static final PricingService pricingService;
    private static final EventManager eventManager;
    private static final PaymentServiceImpl paymentService;
    private static final BookingQueue bookingQueue;
    private static final UserInputReader userInputReader;
    private static final PaymentGateway paymentGateway;
    private static final BookingCleanupService cleanupThread;
    private static final Logger logger;

    static {
        // Load application Configuration
        FileReader.readFile("conf/","properties").stream()
                .forEach(ApplicationConfiguration::loadProperties);

        userInputReader = new UserInputReader();
        eventRepository = new EventRepository();
        auditoriumRepository = new AuditoriumRepository();
        showRepository = new ShowRepository();
        pricingRepository = new CostingRepository();
        // Create the booking queue which holds the Bookings for a customer
        bookingQueue = new BookingQueue();

        //Initialize the services


        paymentGateway = new IBPaymentGateway();
        paymentService = new PaymentServiceImpl(paymentGateway,userInputReader);
        taxService = new TaxService(Arrays.asList(new ServiceTaxCalculator(), new KrishiKalyanTaxCalculator(), new SwachBharatTaxCalculator()));
        pricingService = new PricingServiceImpl(pricingRepository, taxService);
        eventService = new EventServiceImpl(eventRepository);
        auditoriumService = new AuditoriumServiceImpl(auditoriumRepository);
        showService = new ShowServiceImpl(showRepository);
        eventManager = new EventManagerImpl(eventService, auditoriumService, showService, pricingService);
        bookingService = new BookingServiceImpl();
        ticketService = new TicketServiceImpl(showService, bookingService, pricingService, paymentService, taxService);
        cleanupThread = new BookingCleanupServiceImpl(bookingQueue);
        logger = new Logger();
        // For real life
        //bookingService =  new BookingServiceImpl(bookingQueue);


        FileReader.readFile("json/auditoriums/","json").stream()
                .map(f -> JSON_READER.readJson(f, AuditoriumModel.class))
                .forEach(auditoriumService::createAuditorium);


        FileReader.readFile("json/events/","json").stream()
                .map(f -> JSON_READER.readJson(f, EventModel.class))
                .forEach(eventService::createEvent);


        FileReader.readFile("json/shows","json").stream()
                .map(f -> JSON_READER.readJson(f, ShowModel.class))
                .forEach(eventManager::organizeShow);
    }

    public static void main(String[] args) {
        BookingConsole bookingConsole = new BookingConsole(showService,ticketService,paymentService,userInputReader, logger);
        while(true){
            String action = userInputReader.captureAction();
            switch (action){
                case "B":
                    Booking booking = bookingConsole.runConsole();
                    if( booking != null){
                        bookingConsole.displayBookingStatus(booking);
                    }
                    break;
                case "E":
                    Runtime.getRuntime().exit(-1);
            }
        }
    }
}
