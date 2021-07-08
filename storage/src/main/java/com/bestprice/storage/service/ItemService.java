package com.bestprice.storage.service;

import com.bestprice.storage.entity.Item;
import com.bestprice.storage.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    public Optional<Item> getItemById(UUID id) {
        return itemRepository.findById(id);
    }
}
