package com.techverito.sales.entertaintment.storage;

import com.techverito.sales.entertaintment.bmm.storage.Storage.ListBaseStorage;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;
import java.util.function.Predicate;

import static java.util.stream.Collectors.*;

import static com.techverito.sales.entertaintment.bmm.util.JsonReader.JSON_READER;
import static org.mockito.Mockito.*;


import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class ListBasedStorageTest {

    private ListBaseStorage listBaseStorage;

    @Before
    public void setup(){
        listBaseStorage = new ListBaseStorage();
    }

    @Test
    public void test_save_and_get(){
        listBaseStorage.save(new Object(),null);
        listBaseStorage.save(new Object(),null);
        assertEquals(2,listBaseStorage.list().size());
    }
}
