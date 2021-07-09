package com.bestprice.crawler.sent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ItemSent {

    private String name;
    private String URL;
    private Float price;
    private String categoryName;

}
