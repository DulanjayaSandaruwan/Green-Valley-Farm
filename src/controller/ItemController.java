package controller;

import controller.common.ItemManage;
import db.DBConnection;
import model.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-09
 **/
public class ItemController implements ItemManage {

    public List<String> getItemCode() throws SQLException {
        ResultSet rst = DBConnection.getInstance().
                getConnection().prepareStatement("select * from supplyItem").executeQuery();
        List<String> itemCode = new ArrayList<>();
        while (rst.next()) {
            itemCode.add(
                    rst.getString(1)
            );
        }
        return itemCode;
    }

    @Override
    public boolean saveItem(Item item) throws SQLException {
        PreparedStatement stm;
        Connection con = DBConnection.getInstance().getConnection();
        String findDuplicate ="select 1 from supplyItem where supItemCode = ? ";
        stm = con.prepareStatement(findDuplicate);
        stm.setString(1,item.getItemCode());
        ResultSet rs = stm.executeQuery();
        if(!rs.next()) {
            String query = "insert into supplyItem values(?,?,?,?)";
            stm = con.prepareStatement(query);
            stm.setObject(1, item.getItemCode());
            stm.setObject(2, item.getItemName());
            stm.setObject(3, item.getItemType());
            stm.setObject(4, item.getUnitPrice());

            return stm.executeUpdate() > 0;
        }else {
            return false;
        }
    }

    @Override
    public Item searchItem(String code) throws SQLException {
        PreparedStatement stm = DBConnection.getInstance()
                .getConnection().prepareStatement("select * from supplyItem where supItemCode=?");
        stm.setObject(1, code);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Item(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4)
            );

        } else {
            return null;
        }
    }

    @Override
    public boolean updateItem(Item item) throws SQLException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().
                prepareStatement("update supplyItem set supItemName=?, supItemType=?, unitPrice=? where supItemCode=?");
        stm.setObject(1, item.getItemName());
        stm.setObject(2, item.getItemType());
        stm.setObject(3, item.getUnitPrice());
        stm.setObject(4, item.getItemCode());

        return stm.executeUpdate() > 0;
    }

    @Override
    public boolean deleteItem(String code) throws SQLException {
        return DBConnection.getInstance().getConnection().
                prepareStatement("delete from supplyItem where supItemCode='" + code + "'").executeUpdate() > 0;
    }

    @Override
    public ArrayList<Item> selectAllItems() throws SQLException {
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().
                prepareStatement("select * from supplyItem");
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<Item> items = new ArrayList<>();
        while (resultSet.next()) {
            items.add(new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4)
            ));
        }
        return items;
    }
}
