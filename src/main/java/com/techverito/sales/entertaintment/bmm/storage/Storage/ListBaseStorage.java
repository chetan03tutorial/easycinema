package com.techverito.sales.entertaintment.bmm.storage.Storage;

import com.techverito.sales.entertaintment.bmm.domain.Auditorium;
import com.techverito.sales.entertaintment.bmm.util.FileReader;
import com.techverito.sales.entertaintment.bmm.util.JsonReader;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class ListBaseStorage<T,ID> implements BaseStorage<T,ID> {

    private final List<T> list;

    public ListBaseStorage(){
      list  = new LinkedList<>();;
    }

    @Override
    public void save(T t, ID id) {
        list.add(t);
    }

    @Override
    public List<T> list() {
        return list;
    }

    public List<T> get(Predicate p){
        return list.stream().filter(e -> p.test(e)).collect(Collectors.toList());
    }
}
