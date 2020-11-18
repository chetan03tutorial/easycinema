package com.techverito.sales.entertaintment.bmm.services;

import com.techverito.sales.entertaintment.bmm.domain.Show;

import java.util.List;

public interface ShowService {
    void organizeShow(Show showModel);
    Show getShow(Long showId);
    public List<Show> getShows();
}
