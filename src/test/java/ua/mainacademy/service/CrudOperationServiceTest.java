package ua.mainacademy.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.mainacademy.dao.ItemDAO;
import ua.mainacademy.model.Item;

import java.util.Collections;
import java.util.List;

import static ua.mainacademy.prototype.ItemPrototype.aNewItem;

class CrudOperationServiceTest {

    @BeforeEach
    void setUp() {
        ItemDAO.deleteAllItems();
    }

    @AfterEach
    void tearDown() {
        ItemDAO.deleteAllItems();
    }

    @Test
    void getItemTest() {
        Item item = aNewItem();
        Item createdItem = CrudOperationService.create(item);

        Item expectedItem = CrudOperationService.getItem(createdItem.getId());

        Assertions.assertEquals(createdItem.getId(), expectedItem.getId());
        Assertions.assertEquals(createdItem.getUrl(), expectedItem.getUrl());
        Assertions.assertEquals(createdItem.getName(), expectedItem.getName());
    }

    @Test
    void getItemsFromEmptyDB() {
        Assertions.assertEquals(CrudOperationService.getItems(), Collections.EMPTY_LIST);
    }

    @Test
    void getItemsTest() {
        Item first = aNewItem();
        Item second = aNewItem().toBuilder().name("New name").build();
        Item createdFirstItem = CrudOperationService.create(first);
        Item createdSecondItem = CrudOperationService.create(second);

        List<Item> items = CrudOperationService.getItems();

        Assertions.assertEquals(items.size(), 2);
        Assertions.assertEquals(items.get(0).getId(), createdFirstItem.getId());
        Assertions.assertEquals(items.get(0).getName(), first.getName());
        Assertions.assertEquals(items.get(1).getId(), createdSecondItem.getId());
        Assertions.assertEquals(items.get(1).getName(), second.getName());
    }

    @Test
    void createTest() {
        Item item = aNewItem();

        Item createdItem = CrudOperationService.create(item);

        Assertions.assertNotNull(createdItem.getId());
    }

    @Test
    void update() {
        Item item = aNewItem();

        Item createdItem = CrudOperationService.create(item);
        Item updatedItem = CrudOperationService.update(aNewItem().toBuilder()
                .id(createdItem.getId()).name("New name").build());
        Item expectedItem = CrudOperationService.getItem(updatedItem.getId());

        Assertions.assertEquals(createdItem.getId(), expectedItem.getId());
        Assertions.assertEquals(expectedItem.getName(), "New name");
        Assertions.assertNotEquals(createdItem.getName(), expectedItem.getName());
    }

    @Test
    void deleteTest() {
        Item first = aNewItem();
        Item second = aNewItem().toBuilder().name("New name").build();
        Item createdFirstItem = CrudOperationService.create(first);
        Item createdSecondItem = CrudOperationService.create(second);

        List<Item> items = CrudOperationService.getItems();
        Assertions.assertEquals(items.size(), 2);

        CrudOperationService.delete(List.of(createdFirstItem.getId()));

        List<Item> itemsAfter = CrudOperationService.getItems();
        Assertions.assertEquals(itemsAfter.size(), 1);
        Assertions.assertEquals(itemsAfter.get(0).getId(), createdSecondItem.getId());
        Assertions.assertEquals(itemsAfter.get(0).getName(), createdSecondItem.getName());
    }

    @Test
    void shouldTrowAnExeptionForDeleteNullIdsTest() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            CrudOperationService.delete(null);
        });
    }

    @Test
    void deleteAllItemsTest() {
        CrudOperationService.create(aNewItem());
        CrudOperationService.create(aNewItem().toBuilder().name("New name").build());

        Assertions.assertEquals(CrudOperationService.getItems().size(), 2);

        CrudOperationService.deleteAllItems();
        Assertions.assertEquals(CrudOperationService.getItems(), Collections.EMPTY_LIST);
    }
}