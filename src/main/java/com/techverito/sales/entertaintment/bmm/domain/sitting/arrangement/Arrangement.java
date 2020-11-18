package com.techverito.sales.entertaintment.bmm.domain.sitting.arrangement;

import com.techverito.sales.entertaintment.bmm.domain.Seat;
import com.techverito.sales.entertaintment.bmm.constants.SeatType;
import lombok.Data;

import java.util.List;
import static java.util.stream.Collectors.*;
import java.util.stream.IntStream;

@Data
public class Arrangement {

    private SeatType category;
    private List<Seat> seats;

    public Arrangement(){
    }

    public Arrangement(int capacity,SeatType category){
        this.category = category;
        seats = getSeat(capacity, category);
    }

    public SeatType getSeatType(){
        return this.category;
    }


    private List<Seat> getSeat(int capacity, SeatType seatType){
        return IntStream.range(1,capacity).
                mapToObj(num -> new Seat(num,seatType)).
                collect(toList());
    }

    @Override
    public String toString(){
        return this.category.name() + ":" + seats;
    }
}
