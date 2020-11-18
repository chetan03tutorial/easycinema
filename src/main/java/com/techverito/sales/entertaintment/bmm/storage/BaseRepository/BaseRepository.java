package com.techverito.sales.entertaintment.bmm.storage.BaseRepository;

import com.techverito.sales.entertaintment.bmm.exception.NotFoundException;
import com.techverito.sales.entertaintment.bmm.storage.Storage.BaseStorage;


import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Predicate;

abstract public class BaseRepository<T,ID> {

    private BaseStorage<T> storage;

    public BaseRepository(BaseStorage storage){
        this.storage = storage;
    }

    public List<T> list(){
        return storage.list();
    }

    public Optional<T> get(Predicate<T> predicate){
        return storage.list().stream()
                .filter(predicate)
                .findFirst();
    }

    public void save(T t){
        storage.save(t);
    }
}

