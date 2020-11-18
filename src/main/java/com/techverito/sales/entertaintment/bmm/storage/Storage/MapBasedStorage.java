package com.techverito.sales.entertaintment.bmm.storage.Storage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MapBasedStorage<T,ID> implements BaseStorage<T,ID> {

    private final Map<ID,T> storage;

    public MapBasedStorage(){
        this.storage = new HashMap<>();
    }

    @Override
    public void save(T t,ID id) {
        storage.put(id,t);
    }

    @Override
    public List<T> list() {
        return (List<T>)storage.values();
    }

    public List<T> get(Predicate p){
        return storage.entrySet().stream().filter(e -> p.test(e.getKey()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }
}
