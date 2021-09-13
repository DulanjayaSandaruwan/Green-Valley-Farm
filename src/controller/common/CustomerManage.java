package controller.common;

import model.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-13
 **/
public interface CustomerManage {
    boolean saveCustomer(Customer customer) throws SQLException;

    Customer searchCustomer(String id) throws SQLException;

    boolean updateCustomer(Customer customer) throws SQLException;

    boolean deleteCustomer(String id) throws SQLException;

    ArrayList<Customer> selectAllCustomers() throws SQLException;
}
