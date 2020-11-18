package com.techverito.sales.entertaintment.services;

import com.techverito.sales.entertaintment.BaseBmmTest;
import com.techverito.sales.entertaintment.bmm.constants.SeatType;
import com.techverito.sales.entertaintment.bmm.domain.Costing;
import com.techverito.sales.entertaintment.bmm.services.pricing.PricingService;
import com.techverito.sales.entertaintment.bmm.services.pricing.PricingServiceImpl;
import com.techverito.sales.entertaintment.bmm.services.taxation.TaxService;
import com.techverito.sales.entertaintment.bmm.storage.BaseRepository.CostingRepository;
import com.techverito.sales.entertaintment.helper.BookingUnitTestHelper;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;


import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PricingServiceTest extends BaseBmmTest {

    private PricingService pricingService;
    private TaxService taxService;
    private CostingRepository costingRepository;
    private Costing costing;

    @Before
    public void setup(){

        /*Map<SeatType,Double> priceMap = new HashMap<>();
        priceMap.put(SeatType.PLATINUM,500.0);
        priceMap.put(SeatType.GOLD,400.0);
        priceMap.put(SeatType.SILVER,200.0);*/

        costingRepository = mock(CostingRepository.class);
        taxService = mock(TaxService.class);
        pricingService = new PricingServiceImpl(costingRepository);
    }

    @Test
    public void  test_costing_calculation(){
        Costing costing  = new Costing();
        costing.setShowId(41002l);
        costing.setPricing(unitTestHelper.getPriceMap());

        doNothing().when(costingRepository).save(costing);
        when(costingRepository.getCostByShowId(41002l)).thenReturn(costing);
        Map<SeatType,Long> seatMap = new HashMap<>();
        seatMap.put(SeatType.GOLD,2l);
        pricingService.assignCost(41002l,unitTestHelper.getPriceMap());
        Double cost = pricingService.calculateSittingCost(41002l,seatMap);
        verify(costingRepository,times(1)).save(any(Costing.class));
        assertEquals(cost,Double.valueOf(800));

    }



}
