package ua.mainacademy.service;

import lombok.AllArgsConstructor;
import org.jsoup.nodes.Document;
import ua.mainacademy.model.Item;
import ua.mainacademy.parser.PageParser;

import java.util.List;

import static ua.mainacademy.service.CrudOperationService.create;

@AllArgsConstructor
public class ItemPageParsingService extends Thread {

    private List<Item> items;
    private Document document;
    private String url;

    public static boolean isItemPage(String url) {
        return !url.contains("/smartfony/");
    }

    @Override
    public void run() {
        items.add(create(new PageParser(url, document).getItemFromPage()));
    }
}
