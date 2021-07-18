package com.bestprice.crawler.service;

import com.bestprice.storage.entity.ItemCategory;
import com.bestprice.storage.repository.ItemCategoryRepository;
import com.bestprice.storage.service.ItemCategoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.bestprice.crawler.utils.GenerationUtils.generateItemCategory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ItemCategoryServiceTest {

    @InjectMocks
    private ItemCategoryService itemCategoryService;

    @Mock
    private ItemCategoryRepository itemCategoryRepository;

    @Before
    public void setUp() {
        itemCategoryService = new ItemCategoryService(itemCategoryRepository);
    }

    @Test
    public void testSaveItemCategory() {
        ItemCategory savedItemCategory = generateItemCategory();
        ItemCategory expectedItemCategory = ItemCategory.builder()
                .id(UUID.randomUUID())
                .name(savedItemCategory.getName())
                .URL(savedItemCategory.getURL())
                .parentCategory(savedItemCategory.getParentCategory())
                .build();

        when(itemCategoryRepository.save(savedItemCategory)).thenReturn(expectedItemCategory);

        ItemCategory resultItemCategory = itemCategoryService.saveItemCategory(savedItemCategory);

        assertEquals(expectedItemCategory, resultItemCategory);
    }

    @Test
    public void testGetItemCategoryById() {
        ItemCategory expectedItemCategory = generateItemCategory();

        when(itemCategoryRepository.findById(expectedItemCategory.getId())).thenReturn(Optional.of(expectedItemCategory));

        Optional<ItemCategory> resultItemCategory = itemCategoryService.getItemCategoryById(expectedItemCategory.getId());

        assertEquals(Optional.of(expectedItemCategory), resultItemCategory);
    }

    @Test
    public void testGetRandomItemCategory() {
        List<ItemCategory> itemCategoryList = Arrays.asList(generateItemCategory(), generateItemCategory());

        when(itemCategoryRepository.findAllChildItemCategories()).thenReturn(itemCategoryList);

        Optional<ItemCategory> resultItemCategory = itemCategoryService.getRandomItemCategory();

        assertTrue(itemCategoryList.contains(resultItemCategory.get()));
    }

    @Test
    public void testGetAllItemCategoryById() {
        List<ItemCategory> expectedItemCategoryList = Arrays.asList(generateItemCategory(), generateItemCategory());

        when(itemCategoryRepository.findAll()).thenReturn(expectedItemCategoryList);

        List<ItemCategory> resultItemCategoryList = itemCategoryService.getAllItemCategory();

        assertEquals(expectedItemCategoryList, resultItemCategoryList);
    }
}
