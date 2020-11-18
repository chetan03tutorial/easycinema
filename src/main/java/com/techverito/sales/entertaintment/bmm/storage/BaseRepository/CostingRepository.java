package com.techverito.sales.entertaintment.bmm.storage.BaseRepository;

import com.techverito.sales.entertaintment.bmm.domain.Costing;
import com.techverito.sales.entertaintment.bmm.exception.NotFoundException;
import com.techverito.sales.entertaintment.bmm.storage.Storage.ListBaseStorage;

import java.util.List;

public class CostingRepository extends ListBasedRepository<Costing,Long> {

    public Costing getCostByShowId(Long showId){
        return this.list().stream()
                .filter(c -> showId.equals(c.getShowId()))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }
}
