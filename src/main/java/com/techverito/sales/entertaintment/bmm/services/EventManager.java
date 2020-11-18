package com.techverito.sales.entertaintment.bmm.services;

import com.techverito.sales.entertaintment.bmm.domain.Show;
import com.techverito.sales.entertaintment.bmm.model.ShowModel;

public interface EventManager {

    Show organizeShow(ShowModel model);
}
