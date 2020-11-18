package com.techverito.sales.entertaintment.bmm.storage.Storage;

import java.util.List;
import java.util.function.Predicate;

public interface BaseStorage<T,ID> {
    void save(T t,ID id);
    List<T> list();
    List<T> get(Predicate p);
}
