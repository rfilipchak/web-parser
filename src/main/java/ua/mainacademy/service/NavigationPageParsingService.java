package ua.mainacademy.service;

import lombok.AllArgsConstructor;
import org.jsoup.nodes.Document;
import ua.mainacademy.model.Item;
import ua.mainacademy.parser.NavigationPageParser;

import java.util.List;

@AllArgsConstructor
public class NavigationPageParsingService extends Thread {

    private List<Item> items;
    private List<Thread> threads;
    private Document document;
    private String url;

    @Override
    public void run() {
        parsePage(url);
    }

    private void parsePage(String url) {

        List<String> itemLinks = new NavigationPageParser(url, document).extractItemsLinks();

        for (String itemLink : itemLinks) {
//            if (threads.size() > 5) {
//                continue;
//            }
            RouterService routerService = new RouterService(items, threads, itemLink);
            threads.add(routerService);
            routerService.start();
        }

        // pagination
        if (!url.contains("page=")) {
            int lastPage = new  NavigationPageParser(url, document).getPagesCount();
//
//            if (threads.size() > 2) {
//                return;
//            }
            for (int i = 2; i <= lastPage; i++) {
                String nextPageUrl = url + "page=" + i;
                RouterService routerService = new RouterService(items, threads, nextPageUrl);
                threads.add(routerService);
                routerService.start();
            }
        }
    }

    public static boolean isNavigationPage(String url) {
        return url.contains("/smartfony/");
    }
}