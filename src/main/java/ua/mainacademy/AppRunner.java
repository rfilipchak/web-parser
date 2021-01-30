package ua.mainacademy;

import org.jsoup.nodes.Document;
import ua.mainacademy.model.Item;
import ua.mainacademy.parser.PageParser;
import ua.mainacademy.service.DocumentExtractorService;
import ua.mainacademy.service.RouterService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class AppRunner {
    public static void main(String[] args) {

        String url = "https://estore.ua/smartfony/iphone-12-pro-max/";
//        String url1 = "https://estore.ua/iphone-12-pro-max-128gb-pacific-blue/";
//
//        Document document = DocumentExtractorService.getDocument(url);
//        Document document1 = DocumentExtractorService.getDocument(url1);
//
//        PageParser pageParser = new PageParser(url, document1);
//
//        System.out.println(new NavigationPageParser(url, document).extractItemsLinks());
//
//        System.out.println(pageParser.getItemFromPage());


        List<Item> items = Collections.synchronizedList(new ArrayList<>());
        List<Thread> threads = Collections.synchronizedList(new ArrayList<>());

        RouterService routerService = new RouterService(items, threads, url);
        threads.add(routerService);
        routerService.start();

        do {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (!threadsAreNotActive(threads));

        System.out.println("Items were extracted. Amount=" + items.size());
        for (Item i:items) {
            System.out.println(i.getCode() + " : " + i.getName());
        }

    }

    private static boolean threadsAreNotActive(List<Thread> threads) {
        for (Thread thread : threads) {
            if (thread.isAlive() || thread.getState().equals(Thread.State.NEW)) {
                return false;
            }
        }
        return true;
    }
}
