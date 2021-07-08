package com.bestprice.storage.service;

import com.bestprice.storage.entity.ItemPrice;
import com.bestprice.storage.repository.ItemPriceRepository;
import com.bestprice.storage.request.PriceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ItemPriceService {

    @Autowired
    private ItemPriceRepository itemPriceRepository;

    public ItemPrice saveItemPrice(ItemPrice itemPrice) {
        return itemPriceRepository.save(itemPrice);
    }

    public Optional<ItemPrice> getItemPriceById(UUID id) {
        return itemPriceRepository.findById(id);
    }

    public PriceRequest getItemPriceRequestByItemId(UUID id) {
        return new PriceRequest(itemPriceRepository.findMinPriceByItemId(id).orElseGet(null),
                itemPriceRepository.findMaxPriceByItemId(id).orElseGet(null),
                itemPriceRepository.findLatestPriceByItemId(id).orElseGet(null));
    }

}
