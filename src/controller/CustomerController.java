package controller;

import controller.common.CustomerManage;
import db.DBConnection;
import model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-13
 **/
public class CustomerController implements CustomerManage {

    public List<String> getCustomerID() throws SQLException {
        ResultSet rst = DBConnection.getInstance().
                getConnection().prepareStatement("select * from customer").executeQuery();
        List<String> customerId = new ArrayList<>();
        while (rst.next()) {
            customerId.add(
                    rst.getString(1)
            );
        }
        return customerId;
    }

    @Override
    public boolean saveCustomer(Customer customer) throws SQLException {
        PreparedStatement stm;
        Connection con = DBConnection.getInstance().getConnection();

            String findDuplicate = "select 1 from customer where customerId = ?";
            stm = con.prepareStatement(findDuplicate);
            stm.setObject(1, customer.getCustomerId());
            ResultSet rs = stm.executeQuery();
            if (!rs.next()) {
                String query = "insert into customer values(?,?,?,?)";
                stm = con.prepareStatement(query);
                stm.setObject(1, customer.getCustomerId());
                stm.setObject(2, customer.getCustomerName());
                stm.setObject(3, customer.getCustomerAddress());
                stm.setObject(4, customer.getCustomerContact());


                return stm.executeUpdate() > 0;

            }
                return false;
    }

    @Override
    public Customer searchCustomer(String id) throws SQLException {
        PreparedStatement stm = DBConnection.getInstance()
                .getConnection().prepareStatement("select * from customer where customerId=?");
        stm.setObject(1, id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Customer(
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
    public boolean updateCustomer(Customer customer) throws SQLException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().
                prepareStatement("update customer set customerName=?, customerAddress=?, customerContact=? where customerId=?");
            stm.setObject(1, customer.getCustomerName());
            stm.setObject(2, customer.getCustomerAddress());
            stm.setObject(3, customer.getCustomerContact());
            stm.setObject(4, customer.getCustomerId());

            return stm.executeUpdate() > 0;
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException {
        return DBConnection.getInstance().getConnection().
                prepareStatement("delete from customer where customerId='" + id + "'").executeUpdate() > 0;
    }

    @Override
    public ArrayList<Customer> selectAllCustomers() throws SQLException {
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().
                prepareStatement("select * from customer");
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<Customer> customers = new ArrayList<>();
        while (resultSet.next()) {
            customers.add(new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            ));
        }
        return customers;
    }
}
