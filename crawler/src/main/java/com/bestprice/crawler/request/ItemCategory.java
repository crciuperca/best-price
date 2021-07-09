package com.bestprice.crawler.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ItemCategory {

    private UUID id;
    private String name;
    private String URL;
    private ItemCategory parentCategory;

}
