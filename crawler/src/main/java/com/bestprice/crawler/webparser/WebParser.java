package com.bestprice.crawler.webparser;

import com.bestprice.crawler.request.ItemCategory;
import com.bestprice.crawler.sent.ItemSent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

public class WebParser {

    static int sleepTimeMs = 1;

    public static List<ItemSent> processCategoryPage(ItemCategory itemCategory, int pageNo) {
        String urlWithPage = getCategoryPageWithPageNo(itemCategory.getURL(), pageNo);
        Document document = null;
        try {
            document = Jsoup.connect(urlWithPage).get();
        } catch (IOException ioException) {
            return new ArrayList<>();
        }
        Elements cards = document.getElementsByClass("card-heading");
        List<String> itemURLs = cards.stream()
                .map(element ->
                        element.getElementsByClass("thumbnail-wrapper js-product-url").attr("href"))
                .collect(Collectors.toList());

        List<ItemSent> processedItemList = itemURLs.stream().map(itemURL -> {
            Optional<ItemSent> itemSent = processItemPage(itemURL, itemCategory.getName());

            try {
                Thread.sleep((new Random()).nextInt(sleepTimeMs) + 2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return itemSent;
        })
                .filter(itemSent -> itemSent.isPresent())
                .map(itemSent -> itemSent.get())
                .collect(Collectors.toList());

        return processedItemList;
    }

    private static Optional<ItemSent> processItemPage(String itemPageURL, String category) {
        Document document = null;
        try {
            document = Jsoup.connect(itemPageURL).get();
        } catch (IOException ioException) {
            return Optional.empty();
        }
        String title = document.getElementsByClass("product-title").get(0).text();
        String priceString = document.getElementsByClass("product-new-price").get(0).text().split(" ")[0].replace(".", "");
        Float price = Float.parseFloat(priceString.substring(0, priceString.length() - 2) + "." + priceString.substring(priceString.length() - 2));
        return Optional.of(new ItemSent(title, itemPageURL, price, category));
    }


    private static String getCategoryPageWithPageNo(String categoryPageURL, int pageNo) {
        return categoryPageURL.substring(0, categoryPageURL.lastIndexOf("/")) + "/p" + pageNo +
                categoryPageURL.substring(categoryPageURL.lastIndexOf("/"));
    }
}
