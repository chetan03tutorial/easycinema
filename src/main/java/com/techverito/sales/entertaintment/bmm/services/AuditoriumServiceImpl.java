package com.techverito.sales.entertaintment.bmm.services;

import com.techverito.sales.entertaintment.bmm.domain.Auditorium;
import com.techverito.sales.entertaintment.bmm.exception.NotFoundException;
import com.techverito.sales.entertaintment.bmm.model.AuditoriumModel;
import com.techverito.sales.entertaintment.bmm.storage.BaseRepository.AuditoriumRepository;

import java.util.Optional;

public class AuditoriumServiceImpl implements AuditoriumService {

    private static final String AUDITORIUM_NOT_FOUND = "Auditorium not Found %d";
    private final AuditoriumRepository auditoriumRepository;

    public AuditoriumServiceImpl(AuditoriumRepository repository){
        this.auditoriumRepository = repository;
    }

    public Auditorium getAuditorium(Long auditoriumId){
        return auditoriumRepository.get(an -> auditoriumId.equals(an.getId()))
                .stream()
                .findFirst()
                .orElseThrow(() -> new NotFoundException(String.format(AUDITORIUM_NOT_FOUND , auditoriumId)));
    }

    public Auditorium createAuditorium(AuditoriumModel model) {
        Auditorium.AuditoriumBuilder builder = new Auditorium.AuditoriumBuilder();
        Auditorium auditorium = builder.name(model.getName()).screen(model.getNumber()).capacity(model.getMaxCapacity()).build();
        auditoriumRepository.save(auditorium);
        return auditorium;
    }
}
