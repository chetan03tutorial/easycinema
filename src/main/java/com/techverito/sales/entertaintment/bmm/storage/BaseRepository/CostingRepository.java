package com.techverito.sales.entertaintment.bmm.storage.BaseRepository;

import com.techverito.sales.entertaintment.bmm.domain.Costing;
import com.techverito.sales.entertaintment.bmm.exception.NotFoundException;
import com.techverito.sales.entertaintment.bmm.storage.Storage.ListBaseStorage;

public class CostingRepository extends BaseRepository<Costing,Long> {
    private static final ListBaseStorage<Costing> costingStorage = new ListBaseStorage<>();
    private static final String SHOW_NOT_FOUND = "Show Not found %d";

    public CostingRepository() {
        super(costingStorage);
    }

    public Costing getCostByShowId(Long showId){
        return costingStorage.list().stream().filter(c -> c.getShowId().equals(showId))
                .findFirst().orElseThrow(() -> new NotFoundException(String.format(SHOW_NOT_FOUND, showId)));
    }
}
