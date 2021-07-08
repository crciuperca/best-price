package com.bestprice.storage.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
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

    private float minPrice;
    private float maxPrice;
    private float latestPrice;

    public PriceRequest(float minPrice, float maxPrice, float latestPrice) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.latestPrice = latestPrice;
    }
}
