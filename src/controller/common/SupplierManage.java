package controller.common;

import model.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-06
 **/
public interface SupplierManage {
    boolean saveSupplier(Supplier supplier) throws SQLException;

    Supplier searchSupplier(String id) throws SQLException;

    boolean updateSupplier(Supplier supplier) throws SQLException;

    boolean deleteSupplier(String id) throws SQLException;

    ArrayList<Supplier> selectAllSuppliers() throws SQLException;
}
