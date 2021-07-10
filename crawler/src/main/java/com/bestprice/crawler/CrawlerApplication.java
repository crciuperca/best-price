package com.bestprice.crawler;

import com.bestprice.crawler.netutils.NetUtils;
import com.bestprice.crawler.request.ItemCategory;
import com.bestprice.crawler.sent.ItemSent;
import com.bestprice.crawler.webparser.WebParser;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Random;

//TODO Refactor

@Slf4j
public class CrawlerApplication {
	static int MAX_PAGE = 99;

	public static void main(String[] args) {
		while (true) {
			ItemCategory itemCategory = getItemCategory();
			int pageNo = (new Random()).nextInt(2) + 1;
			System.out.println("Processing page " + pageNo + " of category\n" + itemCategory);
			List<ItemSent> itemSentList = WebParser.processCategoryPage(itemCategory, pageNo);
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			itemSentList.forEach(itemSent -> {
				System.out.println("Sending" + itemSent);
				(new NetUtils<>("http://localhost:8091/items/", ItemSent.class)).sendObject(itemSent);
			});
		}
	}

	private static void sendItem(ItemSent itemSent) {

	}

	private static ItemCategory getItemCategory() {
		String urlString = "http://localhost:8091/categories/random";
		return (new NetUtils<>(urlString, ItemCategory.class)).getObject();
	}

}
