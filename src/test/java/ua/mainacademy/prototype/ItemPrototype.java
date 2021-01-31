package ua.mainacademy.prototype;

import ua.mainacademy.model.Item;

public class ItemPrototype {
    public static Item aNewItem(){
        return Item.builder()
                .id(1)
                .code("220957")
                .name("iPhone 12 Pro Max 256GB Pacific Blue (MGDF3)")
                .price(44399)
                .initPrice(47999)
                .url("https://estore.ua/iphone-12-pro-max-256gb-pacific-blue/")
                .imageUrl("https://estore.ua/media/catalog/product/cache/8/image/600x600/9df78eab33525d08d6e5fb8d27136e95/i/p/iphone-12-pro-max-pacific-blue_1__1.jpeg")
                .group("Каталог товаров>Смартфоны>iPhone>iPhone 12 Pro Max")
                .seller("eStore")
                .build();
    }
}
