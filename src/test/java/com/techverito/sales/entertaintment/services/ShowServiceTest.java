package com.techverito.sales.entertaintment.services;


import com.techverito.sales.entertaintment.BaseBmmTest;
import com.techverito.sales.entertaintment.bmm.domain.Show;
import com.techverito.sales.entertaintment.bmm.services.ShowService;
import com.techverito.sales.entertaintment.bmm.services.ShowServiceImpl;
import com.techverito.sales.entertaintment.bmm.storage.BaseRepository.ShowRepository;
import org.junit.Before;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static org.mockito.Mockito.*;


import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ShowServiceTest extends BaseBmmTest {

    private ShowService showService;
    private ShowRepository showRepository;

    @Before
    public void setup(){
        this.showRepository  = mock(ShowRepository.class);
        this.showService = new ShowServiceImpl(showRepository);
    }

    @Test
    public void save_and_retrieve_show(){
        Show show = new Show();
        showService.organizeShow(show);
        verify(showRepository,times(1)).save(show);
        when(showRepository.get(any(Predicate.class))).thenReturn(Arrays.asList(show));
        Show actual = showService.getShow(show.getShowId());
        assertEquals(show.getShowId(),actual.getShowId());
    }

    @Test
    public void get_all_show(){
        Show show = new Show();
        when(showRepository.list()).thenReturn(Arrays.asList(show));
        List<Show> shows = showService.getShows();
        assertEquals(shows.get(0).getShowId(),show.getShowId());
    }


    

}
