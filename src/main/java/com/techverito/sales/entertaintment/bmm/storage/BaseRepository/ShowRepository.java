package com.techverito.sales.entertaintment.bmm.storage.BaseRepository;

import com.techverito.sales.entertaintment.bmm.domain.Show;
import com.techverito.sales.entertaintment.bmm.storage.Storage.ListBaseStorage;

public class ShowRepository extends BaseRepository<Show,Long>{

    private static final ListBaseStorage<Show> showStorage = new ListBaseStorage<>();

    public ShowRepository() {
        super(showStorage);
    }

}
