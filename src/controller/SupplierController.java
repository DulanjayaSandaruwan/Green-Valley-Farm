package controller;

import controller.common.SupplierManage;
import db.DBConnection;
import model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-06
 **/

public class SupplierController implements SupplierManage {

    public List<String> getSupplierID() throws SQLException {
        ResultSet rst = DBConnection.getInstance().
                getConnection().prepareStatement("select * from supplier").executeQuery();
        List<String> supId = new ArrayList<>();
        while (rst.next()) {
            supId.add(
                    rst.getString(1)
            );
        }
        return supId;
    }

    @Override
    public boolean saveSupplier(Supplier supplier) throws SQLException {
        PreparedStatement stm;
        Connection con = DBConnection.getInstance().getConnection();
        String findDuplicate ="select 1 from supplier where supId = ? ";
        stm = con.prepareStatement(findDuplicate);
        stm.setString(1,supplier.getSupID());
        ResultSet rs = stm.executeQuery();
        if(!rs.next()) {
            String query = "insert into supplier values(?,?,?,?)";
            stm = con.prepareStatement(query);
            stm.setObject(1, supplier.getSupID());
            stm.setObject(2, supplier.getSupName());
            stm.setObject(3, supplier.getSupAddress());
            stm.setObject(4, supplier.getSupContact());

            return stm.executeUpdate() > 0;
        }else {
            return false;
        }
    }

    @Override
    public Supplier searchSupplier(String id) throws SQLException {
        PreparedStatement stm = DBConnection.getInstance()
                .getConnection().prepareStatement("select * from supplier where supId=?");
        stm.setObject(1, id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Supplier(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)
            );

        } else {
            return null;
        }
    }

    @Override
    public boolean updateSupplier(Supplier supplier) throws SQLException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().
                prepareStatement("update supplier set supName=?, supAddress=?, supContact=? where supId=?");
        stm.setObject(1, supplier.getSupName());
        stm.setObject(2, supplier.getSupAddress());
        stm.setObject(3, supplier.getSupContact());
        stm.setObject(4, supplier.getSupID());

        return stm.executeUpdate() > 0;

    }

    @Override
    public boolean deleteSupplier(String id) throws SQLException {
        return DBConnection.getInstance().getConnection().
                prepareStatement("delete from supplier where supId='" + id + "'").executeUpdate() > 0;
    }

    @Override
    public ArrayList<Supplier> selectAllSuppliers() throws SQLException {
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().
                prepareStatement("select * from supplier");
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<Supplier> suppliers = new ArrayList<>();
        while (resultSet.next()) {
            suppliers.add(new Supplier(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            ));
        }
        return suppliers;
    }
}
