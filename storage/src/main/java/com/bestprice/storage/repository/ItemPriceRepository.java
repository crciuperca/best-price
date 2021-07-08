package com.bestprice.storage.repository;

import com.bestprice.storage.entity.ItemPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ItemPriceRepository extends JpaRepository<ItemPrice, UUID> {

    @Query("SELECT MAX(ip.price) FROM ItemPrice ip WHERE ip.item.id = :itemId")
    Optional<Float> findMaxPriceByItemId(@Param("itemId") UUID itemId);

    @Query("SELECT MIN(ip.price) FROM ItemPrice ip WHERE ip.item.id = :itemId")
    Optional<Float> findMinPriceByItemId(@Param("itemId") UUID itemId);

    @Query("SELECT ip.price FROM ItemPrice ip WHERE ip.item.id = :itemId AND ip.createdAt = (select MAX(ipp.createdAt) from ItemPrice ipp where ipp.item.id = :itemId)")
    Optional<Float> findLatestPriceByItemId(@Param("itemId") UUID itemId);
}
