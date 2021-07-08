package com.bestprice.storage.controller;

import com.bestprice.storage.entity.Item;
import com.bestprice.storage.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping("/")
    public Item saveItem(@RequestBody Item item) {
        return itemService.saveItem(item);
    }

    @GetMapping("/{id}")
    public Item getItemById(@PathVariable UUID id) {
        return itemService.getItemById(id).orElseGet(null);
    }
}
