package ua.mainacademy.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import ua.mainacademy.model.Item;

import java.util.Collections;
import java.util.Optional;

import static ua.mainacademy.prototype.ItemPrototype.aNewItem;

class ItemDAOTest {

    @Test
    void shouldThrowAnExptionOnUpdate() {
        Item item = aNewItem();

        Assertions.assertThrows(RuntimeException.class, ()-> { ItemDAO.update(null, item.getCode(), item.getName(),
                item.getPrice(),
                item.getInitPrice(),
                item.getUrl(),
                item.getImageUrl(),
                item.getGroup(),
                item.getSeller());
        });
    }

    @Test
    void getItemByItemId() {
        Assertions.assertEquals(ItemDAO.getItemByItemId(10500), Optional.empty());
    }
}