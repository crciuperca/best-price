package com.bestprice.crawler.service;

import com.bestprice.storage.entity.Item;
import com.bestprice.storage.entity.ItemCategory;
import com.bestprice.storage.entity.ItemPrice;
import com.bestprice.storage.receive.ItemReceived;
import com.bestprice.storage.repository.ItemCategoryRepository;
import com.bestprice.storage.repository.ItemPriceRepository;
import com.bestprice.storage.repository.ItemRepository;
import com.bestprice.storage.service.ItemService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.Optional;
import java.util.UUID;

import static com.bestprice.crawler.utils.GenerationUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ItemServiceTest {

    @InjectMocks
    private ItemService itemService;

    @Mock
    private ItemRepository itemRepository;
    @Mock
    private ItemCategoryRepository itemCategoryRepository;
    @Mock
    private ItemPriceRepository itemPriceRepository;

    @Before
    public void setUp() {
        itemService = new ItemService(itemRepository, itemCategoryRepository, itemPriceRepository);
    }

    @Test
    public void testSaveNewItem() {
        ItemCategory itemCategory = generateItemCategory("www.found.com/category/123");
        ItemReceived itemToSave = generateItemReceived(itemCategory.getId());
        Item savedItem = Item.builder()
                .name(itemToSave.getName())
                .id(UUID.randomUUID()).build();

        when(itemCategoryRepository.findById(itemToSave.getCategoryId())).thenReturn(Optional.of(itemCategory));
        when(itemRepository.findByURL(itemToSave.getURL())).thenReturn(Optional.empty());
        when(itemRepository.save(
                Item.builder()
                        .name(itemToSave.getName())
                        .category(itemCategory)
                        .URL(itemToSave.getURL())
                        .build()))
                .thenReturn(savedItem);

        Item returnedItem = itemService.saveItem(itemToSave);

        assertEquals(savedItem, returnedItem);
    }

    @Test
    public void testSaveUpdateItemUpdatePrice() {
        ItemCategory itemCategory = generateItemCategory("www.found.com/category/123");
        ItemReceived itemToSave = generateItemReceived(itemCategory.getId());
        Item existingItem = Item.builder()
                .id(UUID.randomUUID())
                .name(itemToSave.getName())
                .id(UUID.randomUUID()).build();
        ItemPrice itemPrice = generateItemPrice(existingItem);
        ItemPrice savedPrice = ItemPrice.builder()
                .id(itemPrice.getId())
                .price(itemToSave.getPrice())
                .URL(itemPrice.getURL())
                .item(existingItem)
                .createdAt(itemPrice.getCreatedAt())
                .build();

        when(itemCategoryRepository.findById(itemToSave.getCategoryId())).thenReturn(Optional.of(itemCategory));
        when(itemRepository.findByURL(itemToSave.getURL())).thenReturn(Optional.of(existingItem));
        when(itemPriceRepository.findLatestItemPriceByItemId(existingItem.getId())).thenReturn(Optional.of(itemPrice));

        Item returnedItem = itemService.saveItem(itemToSave);

        ArgumentCaptor<ItemPrice> itemPriceArgumentCaptor= ArgumentCaptor.forClass(ItemPrice.class);
        verify(itemPriceRepository).save(itemPriceArgumentCaptor.capture());
        assertEquals(savedPrice, itemPriceArgumentCaptor.getValue());
        assertEquals(existingItem, returnedItem);
    }

    @Test
    public void testSaveUpdateItemNewPrice() {
        ItemCategory itemCategory = generateItemCategory("www.found.com/category/123");
        ItemReceived itemToSave = generateItemReceived(itemCategory.getId());
        Item existingItem = Item.builder()
                .id(UUID.randomUUID())
                .name(itemToSave.getName())
                .id(UUID.randomUUID()).build();
        ItemPrice savedPrice = ItemPrice.builder()
                .price(itemToSave.getPrice())
                .URL(itemToSave.getURL())
                .item(existingItem)
                .build();

        when(itemCategoryRepository.findById(itemToSave.getCategoryId())).thenReturn(Optional.of(itemCategory));
        when(itemRepository.findByURL(itemToSave.getURL())).thenReturn(Optional.of(existingItem));
        when(itemPriceRepository.findLatestItemPriceByItemId(existingItem.getId())).thenReturn(Optional.empty());

        Item returnedItem = itemService.saveItem(itemToSave);

        ArgumentCaptor<ItemPrice> itemPriceArgumentCaptor= ArgumentCaptor.forClass(ItemPrice.class);
        verify(itemPriceRepository).save(itemPriceArgumentCaptor.capture());
        assertEquals(savedPrice, itemPriceArgumentCaptor.getValue());
        assertEquals(existingItem, returnedItem);
    }
}
