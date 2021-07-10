package com.bestprice.storage.receive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ItemReceived {

    private String name;
    private String URL;
    private Float price;
    private UUID categoryId;

}
