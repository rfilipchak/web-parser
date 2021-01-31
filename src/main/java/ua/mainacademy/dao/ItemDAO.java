package ua.mainacademy.dao;

import ua.mainacademy.model.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static java.util.Objects.isNull;

public class ItemDAO {

    private static final Logger LOGGER = Logger.getLogger(ItemDAO.class.getName());

    public static Item save(String code, String name, int price,
                            Integer initPrice, String url, String imageUrl, String group, String seller) {
        String sql = "" +
                "INSERT INTO items(code, name, price, initprice, url, imageurl, itemgroup, seller) " +
                "VALUES(?,?,?,?,?,?,?,?)";
        String sequenceSql = "" +
                "SELECT currval('items_id_seq')";

        try (
                Connection connection = ConnectionToDB.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                PreparedStatement sequenceStatement = connection.prepareStatement(sequenceSql)
        ) {
            preparedStatement.setString(1, code);
            preparedStatement.setString(2, name);
            preparedStatement.setInt(3, price);
            preparedStatement.setInt(4, initPrice);
            preparedStatement.setString(5, url);
            preparedStatement.setString(6, imageUrl);
            preparedStatement.setString(7, group);
            preparedStatement.setString(8, seller);
            preparedStatement.executeUpdate();
            ResultSet resultSet = sequenceStatement.executeQuery();
            Integer id = null;
            while (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            LOGGER.info(String.format("Successful create Item with id: %s ", id));
            return Item.builder()
                    .id(id)
                    .code(code)
                    .name(name)
                    .price(price)
                    .initPrice(initPrice)
                    .url(url)
                    .imageUrl(imageUrl)
                    .group(group)
                    .seller(seller)
                    .build();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException(String.format("Item with code: %s and name: %s was not created", code, name));
    }

    public static Item update(Integer id, String code, String name, int price,
                              int initPrice, String url, String imageUrl, String group, String seller) {
        if (isNull(id)) {
            throw new RuntimeException("id is null, update is impossible");
        }

        String sql = "" +
                "UPDATE items " +
                "SET code=?, name=?, price=?, initprice=?, url=?, imageurl=?, itemgroup=?, seller=? " +
                "WHERE id=?";
        try (
                Connection connection = ConnectionToDB.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setString(1, code);
            preparedStatement.setString(2, name);
            preparedStatement.setInt(3, price);
            preparedStatement.setInt(4, initPrice);
            preparedStatement.setString(5, url);
            preparedStatement.setString(6, imageUrl);
            preparedStatement.setString(7, group);
            preparedStatement.setString(8, seller);
            preparedStatement.setInt(9, id);
            preparedStatement.executeUpdate();

            LOGGER.info(String.format("Successful update Item with id: %s ", id));
            return Item.builder()
                    .id(id)
                    .code(code)
                    .name(name)
                    .price(price)
                    .initPrice(initPrice)
                    .url(url)
                    .imageUrl(imageUrl)
                    .group(group)
                    .seller(seller)
                    .build();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException(String.format("Item with code: %s and name: %s was not updated", code, name));
    }

    public static Optional<Item> getItemByItemId(Integer id) {
        String sql = "" +
                "SELECT * FROM items " +
                "WHERE id=?";
        try (
                Connection connection = ConnectionToDB.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Item item = Item.builder()
                        .id(resultSet.getInt("id"))
                        .code(resultSet.getString("code"))
                        .name(resultSet.getString("name"))
                        .price(resultSet.getInt("price"))
                        .initPrice(resultSet.getInt("initprice"))
                        .url(resultSet.getString("url"))
                        .imageUrl(resultSet.getString("imageUrl"))
                        .group(resultSet.getString("itemgroup"))
                        .seller(resultSet.getString("seller"))
                        .build();
                LOGGER.info(String.format("Successful get Item with id: %s ", id));
                return Optional.of(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        LOGGER.info(String.format("No Item with id: %s ", id));
        return Optional.empty();
    }

    public static List<Item> getAllItems(){
        List<Integer> itemsIds = getItemsIds();
        if (itemsIds.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        List<Item> items = new ArrayList<>();
        for (Integer i :itemsIds) {
            items.add(getItemByItemId(i).get());
        }
        LOGGER.info("Successful get all Items");
        return items;
    }

    public static void delete(List<Integer> ids) {
        if (ids.isEmpty()) {
            throw new RuntimeException("Delete is impossible for empty collection");
        }
        for (Integer id : ids) {
            String sql = "DELETE " +
                    "FROM items " +
                    "WHERE id=?";
            try (
                    Connection connection = ConnectionToDB.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ) {
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
                LOGGER.info(String.format("Successful delete Item with id: %s ", id));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void deleteAllItems(){
        String sql = "TRUNCATE TABLE items ";
        try (
                Connection connection = ConnectionToDB.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.executeUpdate();
            LOGGER.info("Successful delete  all Items from");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static List<Integer> getItemsIds() {
        List<Integer> itemIds = new ArrayList<>();
        String sql = "" +
                "SELECT id FROM items ";
        try (
                Connection connection = ConnectionToDB.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                itemIds.add(resultSet.getInt("id"));
            }
            return itemIds;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemIds;
    }
}