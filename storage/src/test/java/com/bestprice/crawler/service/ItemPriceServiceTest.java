package com.bestprice.crawler.service;

import com.bestprice.storage.entity.ItemPrice;
import com.bestprice.storage.repository.ItemPriceRepository;
import com.bestprice.storage.request.PriceRequest;
import com.bestprice.storage.service.ItemPriceService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static com.bestprice.crawler.utils.GenerationUtils.generateItemPrice;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ItemPriceServiceTest {

    @InjectMocks
    private ItemPriceService itemPriceService;

    @Mock
    private ItemPriceRepository itemPriceRepository;

    @Before
    public void setUp() {
        itemPriceService = new ItemPriceService(itemPriceRepository);
    }

    @Test
    public void testSaveItemPrice() {
        ItemPrice savedPrice = generateItemPrice();

        itemPriceService.saveItemPrice(savedPrice);

        ArgumentCaptor<ItemPrice> itemPriceArgumentCaptor = ArgumentCaptor.forClass(ItemPrice.class);
        verify(itemPriceRepository).save(itemPriceArgumentCaptor.capture());
        assertEquals(savedPrice, itemPriceArgumentCaptor.getValue());
    }

    @Test
    public void testGetItemPriceById() {
        UUID itemPriceId = UUID.randomUUID();

        itemPriceService.getItemPriceById(itemPriceId);

        ArgumentCaptor<UUID> itemPriceIdArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
        verify(itemPriceRepository).findById(itemPriceIdArgumentCaptor.capture());
        assertEquals(itemPriceId, itemPriceIdArgumentCaptor.getValue());
    }

    @Test
    public void testGetItemPriceByItemId() {
        Random randomGenerator = new Random();
        UUID itemPriceId = UUID.randomUUID();
        PriceRequest expectedPriceRequest = new PriceRequest(randomGenerator.nextFloat(),
                randomGenerator.nextFloat(), randomGenerator.nextFloat());

        when(itemPriceRepository.findMinPriceByItemId(itemPriceId))
                .thenReturn(Optional.of(expectedPriceRequest.getMinPrice()));
        when(itemPriceRepository.findMaxPriceByItemId(itemPriceId))
                .thenReturn(Optional.of(expectedPriceRequest.getMaxPrice()));
        when(itemPriceRepository.findLatestPriceByItemId(itemPriceId))
                .thenReturn(Optional.of(expectedPriceRequest.getLatestPrice()));

        PriceRequest resultPriceRequest = itemPriceService.getItemPriceRequestByItemId(itemPriceId);

        assertEquals(expectedPriceRequest, resultPriceRequest);
    }

    @Test
    public void testGetAllItemPrice() {
        List<ItemPrice> expectedItemPriceList = Arrays.asList(generateItemPrice());

        when(itemPriceRepository.findAll()).thenReturn(expectedItemPriceList);

        List<ItemPrice> resultItemPriceList = itemPriceService.getAllItemPrice();

        verify(itemPriceRepository, times(1)).findAll();
        assertEquals(expectedItemPriceList, expectedItemPriceList);
    }
}
