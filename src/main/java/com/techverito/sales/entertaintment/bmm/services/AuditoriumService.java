package com.techverito.sales.entertaintment.bmm.services;

import com.techverito.sales.entertaintment.bmm.domain.Auditorium;
import com.techverito.sales.entertaintment.bmm.model.AuditoriumModel;

public interface AuditoriumService {
    Auditorium getAuditorium(Long auditoriumId);
    Auditorium createAuditorium(AuditoriumModel model);
}
