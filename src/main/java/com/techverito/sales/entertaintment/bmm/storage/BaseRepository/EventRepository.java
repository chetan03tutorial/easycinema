package com.techverito.sales.entertaintment.bmm.storage.BaseRepository;

import com.techverito.sales.entertaintment.bmm.domain.Event;
import com.techverito.sales.entertaintment.bmm.storage.Storage.ListBaseStorage;

public class EventRepository extends BaseRepository<Event,Long> {


    private static final ListBaseStorage<Event> eventStorage = new ListBaseStorage<>();

    public EventRepository() {
        super(eventStorage);
    }
}
