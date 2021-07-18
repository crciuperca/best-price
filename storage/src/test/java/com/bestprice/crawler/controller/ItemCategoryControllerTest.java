package com.bestprice.crawler.controller;

import com.bestprice.storage.controller.ItemCategoryController;
import com.bestprice.storage.entity.ItemCategory;
import com.bestprice.storage.service.ItemCategoryService;
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

import static com.bestprice.crawler.utils.GenerationUtils.generateItemCategory;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ItemCategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemCategoryService itemCategoryService;

    ItemCategoryController itemCategoryController;

    @Before
    public void setUp() {
        itemCategoryService = mock(ItemCategoryService.class);
        itemCategoryController = new ItemCategoryController(itemCategoryService);
        mockMvc = MockMvcBuilders.standaloneSetup(itemCategoryController).build();
    }

    @Test
    public void testSaveItemCategory() throws Exception {
        ItemCategory expectedItemCategory = generateItemCategory();
        String itemReceivedJSON = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(expectedItemCategory);

        when(itemCategoryService.saveItemCategory(expectedItemCategory)).thenReturn(expectedItemCategory);

        this.mockMvc.perform(
                post("/categories/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(itemReceivedJSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", comparesEqualTo(expectedItemCategory.getId().toString())))
                .andExpect(jsonPath("$.name", comparesEqualTo(expectedItemCategory.getName())))
                .andExpect(jsonPath("$.url", comparesEqualTo(expectedItemCategory.getURL())));
    }

    @Test
    public void testGetItemCategoryById() throws Exception {
        ItemCategory expectedItemCategory = generateItemCategory();

        when(itemCategoryService.getItemCategoryById(expectedItemCategory.getId())).thenReturn(Optional.of(expectedItemCategory));

        this.mockMvc.perform(
                get("/categories/{id}", expectedItemCategory.getId()).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", comparesEqualTo(expectedItemCategory.getId().toString())))
                .andExpect(jsonPath("$.name", comparesEqualTo(expectedItemCategory.getName())))
                .andExpect(jsonPath("$.url", comparesEqualTo(expectedItemCategory.getURL())));
    }

    @Test
    public void testGetAllItems() throws Exception {
        List<ItemCategory> expectedItemCategoryList = Arrays.asList(generateItemCategory(), generateItemCategory());

        when(itemCategoryService.getAllItemCategory()).thenReturn(expectedItemCategoryList);

        this.mockMvc.perform(
                get("/categories/all").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", comparesEqualTo(expectedItemCategoryList.get(0).getId().toString())))
                .andExpect(jsonPath("$[0].name", comparesEqualTo(expectedItemCategoryList.get(0).getName())))
                .andExpect(jsonPath("$[0].url", comparesEqualTo(expectedItemCategoryList.get(0).getURL())))
                .andExpect(jsonPath("$[1].id", comparesEqualTo(expectedItemCategoryList.get(1).getId().toString())))
                .andExpect(jsonPath("$[1].name", comparesEqualTo(expectedItemCategoryList.get(1).getName())))
                .andExpect(jsonPath("$[1].url", comparesEqualTo(expectedItemCategoryList.get(1).getURL())));
    }

    @Test
    public void testGetRandomItemCategory() throws Exception {
        ItemCategory expectedItemCategory = generateItemCategory();

        when(itemCategoryService.getRandomItemCategory()).thenReturn(Optional.ofNullable(expectedItemCategory));

        this.mockMvc.perform(
                get("/categories/random", expectedItemCategory.getId()).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", comparesEqualTo(expectedItemCategory.getId().toString())))
                .andExpect(jsonPath("$.name", comparesEqualTo(expectedItemCategory.getName())))
                .andExpect(jsonPath("$.url", comparesEqualTo(expectedItemCategory.getURL())));
    }
}
