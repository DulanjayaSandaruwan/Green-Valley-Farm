package controller.common;

import model.Item;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-09
 **/
public interface ItemManage {
    boolean saveItem(Item item) throws SQLException;

    Item searchItem(String code) throws SQLException;

    boolean updateItem(Item item) throws SQLException;

    boolean deleteItem(String code) throws SQLException;

    ArrayList<Item> selectAllItems() throws SQLException;
}
