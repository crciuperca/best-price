package com.bestprice.storage.controller;

import com.bestprice.storage.entity.ItemPrice;
import com.bestprice.storage.request.PriceRequest;
import com.bestprice.storage.service.ItemPriceService;
import com.bestprice.storage.service.ItemPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/prices")
public class ItemPriceController {

    @Autowired
    private ItemPriceService itemPriceService;

    @PostMapping("/")
    public ItemPrice saveItemPrice(@RequestBody ItemPrice itemPrice) {
        return itemPriceService.saveItemPrice(itemPrice);
    }

    @GetMapping("/{id}")
    public ItemPrice getItemPriceById(@PathVariable UUID id) {
        return itemPriceService.getItemPriceById(id).orElseGet(null);
    }

    @GetMapping("/request/{itemId}")
    public PriceRequest getItemPriceRequestByItemId(@PathVariable UUID itemId) {
        return itemPriceService.getItemPriceRequestByItemId(itemId);
    }

}
