package com.bestprice.crawler.controller;

import com.bestprice.storage.controller.ItemController;
import com.bestprice.storage.entity.Item;
import com.bestprice.storage.receive.ItemReceived;
import com.bestprice.storage.service.ItemService;
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

import static com.bestprice.crawler.utils.GenerationUtils.generateItem;
import static com.bestprice.crawler.utils.GenerationUtils.generateItemReceived;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    ItemController itemController;

    @Before
    public void setUp() {
        itemService = mock(ItemService.class);
        itemController = new ItemController(itemService);
        mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();
    }

    @Test
    public void testSaveItem() throws Exception {
        ItemReceived itemReceived = generateItemReceived();
        String itemReceivedJSON = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(itemReceived);
        Item expectedItem = generateItem();

        when(itemService.saveItem(itemReceived)).thenReturn(expectedItem);

        this.mockMvc.perform(
                post("/items/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(itemReceivedJSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", comparesEqualTo(expectedItem.getId().toString())))
                .andExpect(jsonPath("$.name", comparesEqualTo(expectedItem.getName())))
                .andExpect(jsonPath("$.createdAt", comparesEqualTo(expectedItem.getCreatedAt().toInstant().toEpochMilli())));
    }

    @Test
    public void testGetItemById() throws Exception {
        Item expectedItem = generateItem();

        when(itemService.getItemById(expectedItem.getId())).thenReturn(Optional.of(expectedItem));

        this.mockMvc.perform(
                get("/items/{id}", expectedItem.getId()).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", comparesEqualTo(expectedItem.getId().toString())))
                .andExpect(jsonPath("$.name", comparesEqualTo(expectedItem.getName())))
                .andExpect(jsonPath("$.createdAt", comparesEqualTo(expectedItem.getCreatedAt().toInstant().toEpochMilli())));
    }

    @Test
    public void testGetAllItems() throws Exception {
        List<Item> expectedItemList = Arrays.asList(generateItem(), generateItem());

        when(itemService.getAllItems()).thenReturn(expectedItemList);

        this.mockMvc.perform(
                get("/items/all").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", comparesEqualTo(expectedItemList.get(0).getId().toString())))
                .andExpect(jsonPath("$[0].name", comparesEqualTo(expectedItemList.get(0).getName())))
                .andExpect(jsonPath("$[0].createdAt", comparesEqualTo(expectedItemList.get(0).getCreatedAt().toInstant().toEpochMilli())))
                .andExpect(jsonPath("$[1].id", comparesEqualTo(expectedItemList.get(1).getId().toString())))
                .andExpect(jsonPath("$[1].name", comparesEqualTo(expectedItemList.get(1).getName())))
                .andExpect(jsonPath("$[1].createdAt", comparesEqualTo(expectedItemList.get(1).getCreatedAt().toInstant().toEpochMilli())));
    }
}
