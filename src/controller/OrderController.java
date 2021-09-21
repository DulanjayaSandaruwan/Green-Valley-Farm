package controller;

import db.DBConnection;
import model.Order;
import model.OrderDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-17
 **/
public class OrderController {

    public String getOrderId() throws SQLException {
        ResultSet rst = DBConnection.getInstance().getConnection().
                prepareStatement("select orderId from `order` order by orderId desc limit 1").executeQuery();
        if (rst.next()) {
            int tempId = Integer.parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId <= 9) {
                return "O-00" + tempId;
            } else if (tempId < 99) {
                return "O-0" + tempId;
            } else {
                return "O-" + tempId;
            }
        } else {
            return "O-001";
        }

    }

    public boolean placeOrder(Order order) {

        Connection connection = null;

        try {
            connection = DBConnection.getInstance().getConnection();

            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into `order` values (?, ?, ?, ?)");
            preparedStatement.setObject(1, order.getOrderId());
            preparedStatement.setObject(2, order.getOrderDate());
            preparedStatement.setObject(3, order.getCustomerId());
            preparedStatement.setObject(4, order.getOrderCost());

            if (preparedStatement.executeUpdate() > 0) {
                if (saveOrderDetails(order.getOrderId(), order.getOrderDetails())) {

                    connection.commit();

                    return true;

                } else {

                    connection.rollback();

                    return false;
                }

            } else {

                connection.rollback();

                return false;
            }

        } catch (SQLException throwables) {

            throwables.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return false;
    }

    private boolean saveOrderDetails(String orderId, ArrayList<OrderDetails> orderDetails) throws SQLException {
        for (OrderDetails temp : orderDetails
        ) {
            PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(
                    "insert into orderDetails values (?, ?, ? ,?,?)");

            preparedStatement.setObject(1, temp.getFinalProductId());
            preparedStatement.setObject(2, temp.getOrderId());
            preparedStatement.setObject(3, temp.getOrderQty());
            preparedStatement.setObject(4, temp.getDiscount());
            preparedStatement.setObject(5, temp.getItemTotal());

            if (preparedStatement.executeUpdate() > 0) {
                if (updateQty(temp.getFinalProductId(), temp.getOrderQty())) {

                } else {
                    return false;
                }

            } else {
                return false;
            }
        }
        return true;
    }

    private boolean updateQty(String finalProductId, int qty) throws SQLException {
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection()
                .prepareStatement("update finalProduct set qtyOnHand = (qtyOnHand - ?) where finalProductId = ?");
        preparedStatement.setInt(1, qty);
        preparedStatement.setString(2, finalProductId);
        return preparedStatement.executeUpdate() > 0;
    }
}
