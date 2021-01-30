package ua.mainacademy.parser;

import lombok.AllArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ua.mainacademy.model.Item;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@ToString
public class PageParser {
    private String url;
    private Document document;

    public Item getItemFromPage() {
        Element productBlock = document.getElementsByClass("main container ").first();

        String name = extractName(productBlock);
        String code = extractCode(productBlock);
        int price = extractPrice(productBlock);
        int initPrice = extractInitPrice(productBlock) == 0 ? price : extractInitPrice(productBlock);
        String imageUrl = extractImageUrl(productBlock);
        String group = extractGroupOfProduct(document);
        String seller = extractSeller(document);

        return Item.builder()
                .code(code)
                .name(name)
                .price(price)
                .initPrice(initPrice)
                .group(group)
                .url(url)
                .imageUrl(imageUrl)
                .seller(seller)
                .build();
    }

    private String extractName(Element productBlock) {
        return productBlock.getElementsByClass("product-name")
                .first()
                .getElementsByTag("h1")
                .text();
    }

    private String extractSeller(Document document) {
        return document.getElementById("top")
                .getElementsByClass("logo")
                .first()
                .getElementsByTag("strong")
                .text();
    }

    private String extractGroupOfProduct(Document document) {
        Element groupDiv = document.getElementsByClass("breadcrumbs").first();
        Elements groupElements = groupDiv.getElementsByTag("a");
        List<String> groups = new ArrayList<>();
        for (Element element : groupElements) {
            groups.add(element.text());
        }
        return StringUtils.join(groups, ">");
    }

    private String extractImageUrl(Element productBlock) {
        return productBlock.getElementById("image-main").attr("src");
    }

    private int extractInitPrice(Element productBlock) {
        return Integer.parseInt(productBlock.getElementsByClass("old-price")
                .first()
                .text()
                .replaceAll("\\D", ""));
    }

    private int extractPrice(Element productBlock) {
        return Integer.parseInt(productBlock.getElementsByClass("price")
                .first()
                .text()
                .replaceAll("\\D", ""));
    }

    private String extractCode(Element productBlock) {
        return productBlock.getElementsByClass("value")
                .first()
                .text();
    }
}
