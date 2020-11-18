package com.techverito.sales.entertaintment.repository;

import com.techverito.sales.entertaintment.bmm.storage.BaseRepository.ListBasedRepository;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListBasedRepositoryTest {

    private ListBasedRepository<TestObject,Long> listBasedRepository;

    private class TestObject {
        Long id;
        String name;

        TestObject(Long id,String name){
            this.id = id;
            this.name = name;
        }
    }

    @Before
    public void setup(){
        listBasedRepository = new ListBasedRepository<>();
        listBasedRepository.save(new TestObject(1L,"Tapasya"));
        listBasedRepository.save(new TestObject(2L,"Tapasana"));
    }

    @Test
    public void test_save_and_retrieve(){
        List<TestObject> objectList = listBasedRepository.get( (TestObject obj) -> obj.id ==1);
        assertEquals(1,objectList.size());
    }

    @Test
    public void test_save_and_retrieve_all(){
        List<TestObject> objectList = listBasedRepository.list();
        assertEquals(2,objectList.size());
    }

}
