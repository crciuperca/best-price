package com.bestprice.storage.controller;

import com.bestprice.storage.entity.Item;
import com.bestprice.storage.receive.ItemReceived;
import com.bestprice.storage.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Item saveItem(@RequestBody ItemReceived itemReceived) {
        return itemService.saveItem(itemReceived);
    }

    @GetMapping("/{id}")
    public Item getItemById(@PathVariable UUID id) {
        return itemService.getItemById(id).orElseGet(null);
    }

    @GetMapping("/all")
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }
}
