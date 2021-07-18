package com.bestprice.storage.service;

import com.bestprice.storage.entity.Item;
import com.bestprice.storage.entity.ItemCategory;
import com.bestprice.storage.entity.ItemPrice;
import com.bestprice.storage.receive.ItemReceived;
import com.bestprice.storage.repository.ItemCategoryRepository;
import com.bestprice.storage.repository.ItemPriceRepository;
import com.bestprice.storage.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemCategoryRepository itemCategoryRepository;
    @Autowired
    private ItemPriceRepository itemPriceRepository;

    public ItemService(ItemRepository itemRepository, ItemCategoryRepository itemCategoryRepository, ItemPriceRepository itemPriceRepository) {
        this.itemRepository = itemRepository;
        this.itemCategoryRepository = itemCategoryRepository;
        this.itemPriceRepository = itemPriceRepository;
    }

    public Item saveItem(ItemReceived itemReceived) {
        ItemCategory itemCategory = itemCategoryRepository.findById(itemReceived.getCategoryId()).get(); //TODO behaviour for new Category, althought this won't fail in the current implementation
        Optional<Item> existingItem = itemRepository.findByURL(itemReceived.getURL());
        Optional<Item> newItem = Optional.empty();
        if (!existingItem.isPresent()) {
            newItem = Optional.of(itemRepository.save(Item.builder()
                    .name(itemReceived.getName())
                    .category(itemCategory)
                    .URL(itemReceived.getURL())
                    .build()));
        } else {
            Optional<ItemPrice> latestItemPrice = itemPriceRepository.findLatestItemPriceByItemId(existingItem.get().getId());
            if (latestItemPrice.isPresent() && (getDaysBetween(latestItemPrice.get().getCreatedAt(), new Date()) == 0 && latestItemPrice.get().getPrice() != itemReceived.getPrice())) {
                ItemPrice newItemPrice = latestItemPrice.get();
                newItemPrice.setPrice(itemReceived.getPrice());
                itemPriceRepository.save(newItemPrice);
            } else {
                itemPriceRepository.save(ItemPrice.builder()
                        .price(itemReceived.getPrice())
                        .URL(itemReceived.getURL())
                        .item(existingItem.get())
                        .build());
            }
        }
        return newItem.isPresent() ? newItem.get() : existingItem.get();
    }

    public Optional<Item> getItemById(UUID id) {
        return itemRepository.findById(id);
    }

    private long getDaysBetween(Date start, Date end) {
        long millisInADay = 24 * 60 * 60 * 1000;
        return (end.toInstant().toEpochMilli() - start.toInstant().toEpochMilli()) / millisInADay;
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }
}
