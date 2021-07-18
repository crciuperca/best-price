package com.bestprice.storage.service;

import com.bestprice.storage.entity.ItemCategory;
import com.bestprice.storage.repository.ItemCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ItemCategoryService {

    @Autowired
    private ItemCategoryRepository itemCategoryRepository;

    public ItemCategoryService(ItemCategoryRepository itemCategoryRepository) {
        this.itemCategoryRepository = itemCategoryRepository;
    }

    public ItemCategory saveItemCategory(ItemCategory itemCategory) {
        return itemCategoryRepository.save(itemCategory);
    }

    public Optional<ItemCategory> getItemCategoryById(UUID id) {
        return itemCategoryRepository.findById(id);
    }

    public Optional<ItemCategory> getRandomItemCategory() {
        List<ItemCategory> childItemCategoryList = itemCategoryRepository.findAllChildItemCategories().stream().collect(Collectors.toList());
        if (childItemCategoryList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(childItemCategoryList.get((new Random()).nextInt(childItemCategoryList.size())));
    }

    public List<ItemCategory> getAllItemCategory() {
        return itemCategoryRepository.findAll();
    }
}
