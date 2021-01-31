package ua.mainacademy;

import ua.mainacademy.model.Item;
import ua.mainacademy.service.CrudOperationService;
import ua.mainacademy.service.RouterService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class AppRunner {
    public static void main(String[] args) {

        String url = "https://estore.ua/smartfony/iphone-12-pro-max/";

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
        for (Item i : items) {
            System.out.println(i.getCode() + " : " + i.getName());
        }
//
//        CrudOperationService.update(new Item(5, "220957", "iPhone 12 Pro Max 256GB Pacific Blue (MGDF3)"
//                , 2, 2, "", "", "", ""));
//
//        CrudOperationService.delete(null);
//        System.out.println(CrudOperationService.getItem(10));
//
        System.out.println(CrudOperationService.getItems());
//
//        CrudOperationService.deleteAllItems();

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
