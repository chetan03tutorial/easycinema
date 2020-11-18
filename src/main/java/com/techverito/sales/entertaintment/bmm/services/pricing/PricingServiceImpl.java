package com.techverito.sales.entertaintment.bmm.services.pricing;

import com.techverito.sales.entertaintment.bmm.constants.SeatType;
import com.techverito.sales.entertaintment.bmm.domain.Costing;
import com.techverito.sales.entertaintment.bmm.services.taxation.TaxService;
import com.techverito.sales.entertaintment.bmm.storage.BaseRepository.CostingRepository;

import java.util.Map;
import static java.util.stream.Collectors.toMap;

/**
 * In real life microservice architecture, this would be a separate microservice
 * which would manage the pricing of each show. A remote call to this service would
 * set the costing model of each show.
 * Have the concept of promocode etc in this service
 */
public class PricingServiceImpl implements PricingService{

    private final CostingRepository costingRepository;


    public PricingServiceImpl(CostingRepository costingRepository) {
        this.costingRepository = costingRepository;
    }

    /*public Double calculateTaxAmount(Double mrp){
        return taxService.calculateTax(mrp);
    }*/

    /**
     * In real life scenario, this method would be used to fetch the cost of the
     * show and the selected arrangement from the stored information
     * @param showId - Show Id to fetch the pricing model for
     * @param seatCount - Seat Type -> Number of seats map
     * @return Cost of the selected seats based on customer selection
     */
    public Double calculateSittingCost(Long showId, Map<SeatType,Long> seatCount){
        Costing costing = costingRepository.getCostByShowId(showId);
        Map<SeatType,Double> priceTable = costing.getPricing();
        return seatCount.entrySet()
                .stream()
                .mapToDouble(e ->  priceTable.get(e.getKey()) * e.getValue())
                .sum();
    }

    /*This is the method which need to be exposed in real life to
    * save the cost of the show
    */
    public void assignCost(Long showId, Map<SeatType,Double> pricing){
        /*Map<SeatType, Double> pricingTable = pricing.entrySet().stream()
                .collect(toMap(Map.Entry::getKey,Map.Entry::getValue));*/
        Costing costing = new Costing();
        costing.setShowId(showId);
        costing.setPricing(pricing);
        costingRepository.save(costing);
    }
}
