package com.bestprice.crawler.utils;

import com.bestprice.storage.entity.Item;
import com.bestprice.storage.entity.ItemCategory;
import com.bestprice.storage.entity.ItemPrice;
import com.bestprice.storage.receive.ItemReceived;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class GenerationUtils {

    public static ItemReceived generateItemReceived(UUID categoryId) {
        UUID randomId = UUID.randomUUID();
        return ItemReceived.builder()
                .name("itemReceived-" + randomId)
                .URL("URL-" + randomId)
                .price(new Random().nextFloat())
                .categoryId(categoryId != null ? categoryId : UUID.randomUUID())
                .build();
    }

    public static ItemReceived generateItemReceived() {
        return generateItemReceived(null);
    }

    public static ItemCategory generateItemCategory(String URL) {
        UUID randomId = UUID.randomUUID();
        return ItemCategory.builder()
                .id(randomId)
                .name("itemReceived-" + randomId)
                .URL(URL != null ? URL : "URL-" + randomId)
                .parentCategory(null)
                .build();
    }

    public static ItemCategory generateItemCategory() {
        return generateItemCategory(null);
    }

    public static ItemPrice generateItemPrice(Item item) {
        UUID randomId = UUID.randomUUID();
        return ItemPrice.builder()
                .id(randomId)
                .price(new Random().nextFloat())
                .URL("URL-" + randomId)
                .item(item)
                .createdAt(item != null ? new Date() : null)
                .build();
    }

    public static ItemPrice generateItemPrice() {
        return generateItemPrice(null);
    }

    public static Item generateItem() {
        UUID randomId = UUID.randomUUID();
        return Item.builder()
                .id(randomId)
                .name("item-" + randomId)
                .category(generateItemCategory())
                .URL("URL-" + randomId)
                .createdAt(new Date())
                .build();
    }
}
