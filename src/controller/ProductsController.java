package controller;

import controller.common.ProductsManage;
import db.DBConnection;
import model.Item;
import model.Products;

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
public class ProductsController implements ProductsManage {

    public List<String> getProductID() throws SQLException {
        ResultSet rst = DBConnection.getInstance().
                getConnection().prepareStatement("select * from finalProduct").executeQuery();
        List<String> productID = new ArrayList<>();
        while (rst.next()) {
            productID.add(
                    rst.getString(1)
            );
        }
        return productID;
    }

    @Override
    public boolean saveProduct(Products products) throws SQLException {
        PreparedStatement stm;
        Connection con = DBConnection.getInstance().getConnection();
        String findDuplicate ="select 1 from finalProduct where finalProductId = ? ";
        stm = con.prepareStatement(findDuplicate);
        stm.setString(1,products.getProductId());
        ResultSet rs = stm.executeQuery();
        if(!rs.next()) {
            String query = "insert into finalProduct values(?,?,?,?,?)";
            stm = con.prepareStatement(query);
            stm.setObject(1, products.getProductId());
            stm.setObject(2, products.getProductName());
            stm.setObject(3, products.getProductType());
            stm.setObject(4, products.getQtyOnHand());
            stm.setObject(5, products.getUnitPrice());

            return stm.executeUpdate() > 0;
        }else {
            return false;
        }
    }

    @Override
    public Products searchProducts(String id) throws SQLException {
        PreparedStatement stm = DBConnection.getInstance()
                .getConnection().prepareStatement("select * from finalProduct where finalProductId=?");
        stm.setObject(1, id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Products(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getInt(4),
                    rst.getDouble(5)
            );

        } else {
            return null;
        }
    }

    @Override
    public boolean updateProducts(Products products) throws SQLException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().
                prepareStatement("update finalProduct set finalProductName=?, finalProductType=?, qtyOnHand=?, unitPrice=? where finalProductId=?");
        stm.setObject(1, products.getProductName());
        stm.setObject(2, products.getProductType());
        stm.setObject(3, products.getQtyOnHand());
        stm.setObject(4, products.getUnitPrice());
        stm.setObject(5, products.getProductId());

        return stm.executeUpdate() > 0;
    }

    @Override
    public boolean deleteProducts(String id) throws SQLException {
        return DBConnection.getInstance().getConnection().
                prepareStatement("delete from finalProduct where finalProductId='" + id + "'").executeUpdate() > 0;
    }

    @Override
    public ArrayList<Products> selectAllProducts() throws SQLException {
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().
                prepareStatement("select * from finalProduct");
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<Products> products = new ArrayList<>();
        while (resultSet.next()) {
            products.add(new Products(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4),
                    resultSet.getDouble(5)
            ));
        }
        return products;
    }
}
