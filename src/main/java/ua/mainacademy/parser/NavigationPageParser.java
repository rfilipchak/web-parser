package ua.mainacademy.parser;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class NavigationPageParser {
    private String url;
    private Document document;


    public List<String> extractItemsLinks() {
        Elements items = document.getElementsByClass("category-products")
                .first()
                .getElementsByClass("item");
        List<String> extractItemsLinks = new ArrayList<>();
        for (Element element : items) {
            extractItemsLinks.add(getItem(element));
        }
        return extractItemsLinks;
    }


    public int getPagesCount(){
        return document.getElementsByClass("toolbar-bottom")
                .first().getElementsByClass("pages").first().getElementsByTag("a").size();
    }

    private String getItem(Element element) {
        return element.getElementsByTag("a").attr("href");
    }



}
