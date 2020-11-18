package com.techverito.sales.entertaintment.bmm.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ShowModel {
    private Long eventId;
    private Long auditoriumId;
    private List<Capacity> capacities;
    private Map<String,Double> pricing;
    private String currencyCode;
}
