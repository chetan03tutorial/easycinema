package com.techverito.sales.entertaintment.bmm.services;

import com.techverito.sales.entertaintment.bmm.domain.Show;
import com.techverito.sales.entertaintment.bmm.exception.NotFoundException;
import com.techverito.sales.entertaintment.bmm.storage.BaseRepository.ShowRepository;

import java.util.List;

/**
 * This service is used to create the show by providing details such as
 * event, venue , costing etc.
 */
public class ShowServiceImpl implements ShowService {

    private final String SHOW_NOT_FOUND = "Show Not Found %d";
    /*private final EventManager eventManager;*/
    private final ShowRepository showRepository;
    //private final PricingServiceImpl pricingService;

    public ShowServiceImpl(ShowRepository showRepository){
        /*this.eventManager = movieEventManager;*/
        this.showRepository = showRepository;
        //this.pricingService = pricingService;
    }

    public void organizeShow(Show show){
        showRepository.save(show);
    }

    public List<Show> getShows(){
        return showRepository.list();
    }

    public Show getShow(Long showId){
        return showRepository.get(sd -> sd.getShowId().equals(showId))
                .orElseThrow(() -> new NotFoundException(String.format(SHOW_NOT_FOUND, showId)));
    }




   /* public ShowDetails organizeShow(File file){
        ShowModel request = JsonReader.JSON_READER.readJson(file, ShowModel.class);
        *//*ShowDetails show = movieEventManager.organizeEvent(request);*//*
        ShowDetails show = organizeShow(request);
        showRepository.saveShow(show);
        pricingService.assignCost(show.getShowId(), request.getPricing());
        return show;
    }*/

    /*public List<ShowDetails> organizeShow(String requestPath){
        List<ShowDetails> shows = FileReader.readFile(requestPath).stream()
                .map(this::organizeShow).collect(toList());
        return shows;
    }*/

}
