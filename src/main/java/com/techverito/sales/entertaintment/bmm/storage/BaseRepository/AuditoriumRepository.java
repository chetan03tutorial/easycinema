package com.techverito.sales.entertaintment.bmm.storage.BaseRepository;

import com.techverito.sales.entertaintment.bmm.domain.Auditorium;
import com.techverito.sales.entertaintment.bmm.storage.Storage.ListBaseStorage;

public class AuditoriumRepository extends BaseRepository<Auditorium,Long>{

    private static final ListBaseStorage<Auditorium> auditoriumStorage = new ListBaseStorage<>();
    public AuditoriumRepository() {
        super(auditoriumStorage);
    }
}
