package com.bestprice.crawler.repository;

import com.bestprice.crawler.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

}
