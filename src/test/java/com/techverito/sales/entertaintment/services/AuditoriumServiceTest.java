package com.techverito.sales.entertaintment.services;
import com.techverito.sales.entertaintment.BaseBmmTest;
import com.techverito.sales.entertaintment.bmm.domain.Auditorium;
import com.techverito.sales.entertaintment.bmm.model.AuditoriumModel;
import com.techverito.sales.entertaintment.bmm.services.AuditoriumService;
import com.techverito.sales.entertaintment.bmm.services.AuditoriumServiceImpl;
import com.techverito.sales.entertaintment.bmm.storage.BaseRepository.AuditoriumRepository;
import com.techverito.sales.entertaintment.bmm.util.FileReader;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static java.util.stream.Collectors.*;

import static com.techverito.sales.entertaintment.bmm.util.JsonReader.JSON_READER;
import static org.mockito.Mockito.*;


import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AuditoriumServiceTest extends BaseBmmTest {

    private AuditoriumService auditoriumService;

    private AuditoriumRepository auditoriumRepository;

    @Before
    public void setup(){
        this.auditoriumRepository = mock(AuditoriumRepository.class);
        this.auditoriumService = new AuditoriumServiceImpl(auditoriumRepository);
    }

    @Test
    public void create_auditorium(){
        List<Auditorium> auditoriumList = FileReader.readFile("json/auditoriums/","json").stream()
                .map(f -> JSON_READER.readJson(f, AuditoriumModel.class))
                .map(auditoriumService::createAuditorium)
                .collect(toList());
        verify(auditoriumRepository, times(auditoriumList.size())).save(any(Auditorium.class));
    }

    @Test
    public void get_auditorium_details(){
        Auditorium.AuditoriumBuilder builder  = new Auditorium.AuditoriumBuilder();
        Auditorium expected = builder.name("AUDITORIUM-1").build();
        when(auditoriumRepository.get(any(Predicate.class))).thenReturn(Optional.of(expected));
        Auditorium actual = auditoriumService.getAuditorium((long)1000);
        assertEquals(actual.getName(),expected.getName());
    }
}
