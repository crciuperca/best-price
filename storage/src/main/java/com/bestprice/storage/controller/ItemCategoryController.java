package com.bestprice.storage.controller;

import com.bestprice.storage.entity.ItemCategory;
import com.bestprice.storage.service.ItemCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/categories")
public class ItemCategoryController {

    @Autowired
    private ItemCategoryService itemCategoryService;

    @PostMapping("/")
    public ItemCategory saveItemCategory(@RequestBody ItemCategory itemCategory) {
        return itemCategoryService.saveItemCategory(itemCategory);
    }

    @GetMapping("/{id}")
    public ItemCategory getItemCategoryById(@PathVariable UUID id) {
        return itemCategoryService.getItemCategoryById(id).orElseGet(null);
    }

    @GetMapping("/random")
    public ItemCategory getRandomItemCategory() {
        return itemCategoryService.getRandomItemCategory().orElseGet(null);
    }
}
