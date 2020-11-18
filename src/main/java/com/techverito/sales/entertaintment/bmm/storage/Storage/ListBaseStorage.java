package com.techverito.sales.entertaintment.bmm.storage.Storage;

import com.techverito.sales.entertaintment.bmm.domain.Auditorium;
import com.techverito.sales.entertaintment.bmm.util.FileReader;
import com.techverito.sales.entertaintment.bmm.util.JsonReader;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class ListBaseStorage<T> implements BaseStorage<T> {

    private final List<T> list;

    public ListBaseStorage(){
      list  = new LinkedList<>();;
    }

    @Override
    public void save(T t) {
        list.add(t);
    }


    @Override
    public List<T> list() {
        return list;
    }
}
