package com.techverito.sales.entertaintment.bmm.storage.BaseRepository;

import com.techverito.sales.entertaintment.bmm.storage.Storage.BaseStorage;
import com.techverito.sales.entertaintment.bmm.storage.Storage.ListBaseStorage;

import java.util.List;
import java.util.function.Predicate;

public class ListBasedRepository<T,ID> implements Repository<T,ID> {

    private final BaseStorage<T,ID> storage;

    public ListBasedRepository(){
        this.storage = new ListBaseStorage<>();
    }

    public List<T> list(){
        return storage.list();
    }

    public List<T> get(Predicate<T> predicate){
        return storage.get(predicate);
        /*.list().stream().filter(predicate).collect(toList());*/
    }

    public void save(T t){
        storage.save(t,null);
    }
}
