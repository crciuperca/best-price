package com.bestprice.storage.repository;

import com.bestprice.storage.entity.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Repository
public interface ItemCategoryRepository extends JpaRepository<ItemCategory, UUID> {

    @Query("SELECT ic FROM ItemCategory ic WHERE ic.parentCategory IS NOT NULL")
    Collection<ItemCategory> findAllChildItemCategories();
}
