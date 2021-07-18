package com.bestprice.storage.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PriceRequest {

    private Float minPrice;
    private Float maxPrice;
    private Float latestPrice;

    @Override
    public boolean equals(Object o) {
        return this.minPrice == ((PriceRequest)o).getMinPrice() &&
                this.maxPrice == ((PriceRequest)o).getMaxPrice() &&
                this.latestPrice == ((PriceRequest)o).getLatestPrice();
    }

}
