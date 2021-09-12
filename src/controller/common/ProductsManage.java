package controller.common;

import model.Item;
import model.Products;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-09
 **/
public interface ProductsManage {
    boolean saveProduct(Products products) throws SQLException;

    Products searchProducts(String id) throws SQLException;

    boolean updateProducts(Products products) throws SQLException;

    boolean deleteProducts(String id) throws SQLException;

    ArrayList<Products> selectAllProducts() throws SQLException;
}
