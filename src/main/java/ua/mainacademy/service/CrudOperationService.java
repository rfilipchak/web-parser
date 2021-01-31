package ua.mainacademy.service;

import lombok.NonNull;
import ua.mainacademy.dao.ItemDAO;
import ua.mainacademy.model.Item;

import java.util.List;

public class CrudOperationService {

    public static Item getItem(Integer id){
        return ItemDAO.getItemByItemId(id).get();
    }

    public static List<Item> getItems(){
        return ItemDAO.getAllItems();
    }

    public static Item create(Item item){
        return ItemDAO.save(item.getCode(), item.getName(), item.getPrice(), item.getInitPrice(), item.getUrl(),
                item.getImageUrl(), item.getGroup(), item.getSeller());
    }

    public static Item update(Item item){
        return ItemDAO.update(item.getId(), item.getCode(), item.getName(), item.getPrice(), item.getInitPrice(), item.getUrl(),
                item.getImageUrl(), item.getGroup(), item.getSeller());
    }

    public static void delete(@NonNull List<Integer> ids){
        ItemDAO.delete(ids);
    }

    public static void deleteAllItems(){
        ItemDAO.deleteAllItems();
    }
}
