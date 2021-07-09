package com.bestprice.crawler;

import com.bestprice.crawler.netutils.NetUtils;
import com.bestprice.crawler.request.ItemCategory;
import com.bestprice.crawler.sent.ItemSent;
import com.bestprice.crawler.webparser.WebParser;

import java.util.List;
import java.util.Random;

public class CrawlerApplication {

	public static void main(String[] args) {
		ItemCategory itemCategory = getItemCategory();
		while (true) {
			int pageNo = (new Random()).nextInt(2) + 1;
			System.out.println("Processing page " + pageNo + " of category\n" + itemCategory);
			List<ItemSent> itemSentList = WebParser.processCategoryPage(itemCategory, pageNo);
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			itemSentList.forEach(itemSent -> System.out.println(itemSent));
		}
	}

	private static void sendItem(ItemSent itemSent) {

	}

	private static ItemCategory getItemCategory() {
		String urlString = "http://localhost:8091/categories/random";
		return (new NetUtils<>(urlString, ItemCategory.class)).getObject();
	}

}
