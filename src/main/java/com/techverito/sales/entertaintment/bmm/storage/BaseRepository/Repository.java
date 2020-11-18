package com.techverito.sales.entertaintment.bmm.storage.BaseRepository;

import com.techverito.sales.entertaintment.bmm.exception.NotFoundException;
import com.techverito.sales.entertaintment.bmm.storage.Storage.BaseStorage;


import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Predicate;

public interface Repository<T,ID> {

    List<T> list();

    List<T> get(Predicate<T> id);

    void save(T t);
}

