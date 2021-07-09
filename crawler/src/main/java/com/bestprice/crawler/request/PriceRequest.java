package com.bestprice.crawler.request;

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

    private float minPrice;
    private float maxPrice;
    private float latestPrice;

}
