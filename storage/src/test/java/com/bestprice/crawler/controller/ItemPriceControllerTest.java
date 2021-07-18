package com.bestprice.crawler.controller;

import com.bestprice.storage.controller.ItemPriceController;
import com.bestprice.storage.entity.ItemPrice;
import com.bestprice.storage.request.PriceRequest;
import com.bestprice.storage.service.ItemPriceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static com.bestprice.crawler.utils.GenerationUtils.generateItemPrice;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ItemPriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemPriceService itemPriceService;

    ItemPriceController itemPriceController;

    @Before
    public void setUp() {
        itemPriceService = mock(ItemPriceService.class);
        itemPriceController = new ItemPriceController(itemPriceService);
        mockMvc = MockMvcBuilders.standaloneSetup(itemPriceController).build();
    }

    @Test
    public void testSaveItemPrice() throws Exception {
        ItemPrice itemPriceReceived = generateItemPrice();
        String itemPriceReceivedJSON = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(itemPriceReceived);

        when(itemPriceService.saveItemPrice(itemPriceReceived)).thenReturn(itemPriceReceived);

        this.mockMvc.perform(
                post("/prices/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(itemPriceReceivedJSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price", equalTo(Double.parseDouble(itemPriceReceived.getPrice().toString()))))
                .andExpect(jsonPath("$.url", comparesEqualTo(itemPriceReceived.getURL())));
    }

    @Test
    public void testGetItemPriceById() throws Exception {
        ItemPrice expectedItemPrice = generateItemPrice();
        String itemPriceReceivedJSON = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(expectedItemPrice);

        when(itemPriceService.getItemPriceById(expectedItemPrice.getId())).thenReturn(Optional.of(expectedItemPrice));

        this.mockMvc.perform(
                get("/prices/{id}", expectedItemPrice.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(itemPriceReceivedJSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", comparesEqualTo(expectedItemPrice.getId().toString())))
                .andExpect(jsonPath("$.url", is(expectedItemPrice.getURL())));
    }

    @Test
    public void testGetAllItemPrice() throws Exception {
        List<ItemPrice> expectedItemPriceList = Arrays.asList(generateItemPrice(), generateItemPrice());

        when(itemPriceService.getAllItemPrice()).thenReturn(expectedItemPriceList);

        this.mockMvc.perform(
                get("/prices/all").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", comparesEqualTo(expectedItemPriceList.get(0).getId().toString())))
                .andExpect(jsonPath("$[0].url", comparesEqualTo(expectedItemPriceList.get(0).getURL())))
                .andExpect(jsonPath("$[1].id", comparesEqualTo(expectedItemPriceList.get(1).getId().toString())))
                .andExpect(jsonPath("$[1].url", comparesEqualTo(expectedItemPriceList.get(1).getURL())));
    }

    @Test
    public void testGetItemPriceRequestByItemId() throws Exception {
        Random randomGenerator = new Random();
        PriceRequest expectedPriceRequest = new PriceRequest(randomGenerator.nextFloat(),
                randomGenerator.nextFloat(), randomGenerator.nextFloat());
        UUID itemId = UUID.randomUUID();

        when(itemPriceService.getItemPriceRequestByItemId(itemId)).thenReturn(expectedPriceRequest);

        this.mockMvc.perform(
                get("/prices/request/{itemId}", itemId).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.minPrice", equalTo(Double.parseDouble(expectedPriceRequest.getMinPrice().toString()))))
                .andExpect(jsonPath("$.maxPrice", equalTo(Double.parseDouble(expectedPriceRequest.getMaxPrice().toString()))))
                .andExpect(jsonPath("$.latestPrice", equalTo(Double.parseDouble(expectedPriceRequest.getLatestPrice().toString()))));
    }

}