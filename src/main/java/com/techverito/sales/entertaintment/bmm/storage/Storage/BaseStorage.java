package com.techverito.sales.entertaintment.bmm.storage.Storage;

import java.util.List;

public interface BaseStorage<T> {
    public void save(T t);
    public List<T> list();
}
