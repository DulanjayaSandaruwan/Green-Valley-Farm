package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.*;
import org.controlsfx.control.Notifications;
import view.tm.OrderDetailsTM;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-19
 **/

public class OrderDetailsFormController {

    public TextField txtOrderId;
    public TextField txtCustomerId;
    public TextField txtCustomerContact;
    public TextField txtCustomerName;
    public TextField txtCustomerAddress;
    public TextField txtOrderDate;
    public TextField txtOrderCost;
    public TableView tblOrderDetails;
    public TableColumn colProductId;
    public TableColumn colProductName;
    public TableColumn colProductType;
    public TableColumn colUnitPrice;
    public TableColumn colQuantity;
    public TableColumn colDiscount;

    ObservableList<OrderDetailsTM> observableList = FXCollections.observableArrayList();

    public void initialize() {

    }

    public OderDetailsJoinModel searchOrderDetails(String id) throws SQLException {
        ArrayList<OrderDetails> orderDetails = new ArrayList<>();

        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("\tSELECT \n" +
                "\tc.customerName,\n" +
                "\tc.customerAddress,\n" +
                "\tc.customerContact,\n" +
                "\to.orderId,\n" +
                "\to.orderDate,\n" +
                "\to.customerId,\n" +
                "\to.orderCost,\n" +
                "\tod.finalProductId,\n" +
                "\tod.orderQty,\n" +
                "\tod.discount,\n" +
                "\tf.finalProductName,\n" +
                "\tf.finalProductType,\n" +
                "\tf.unitPrice\n" +
                "\t\n" +
                "\tFROM `order` AS o\n" +
                "\tINNER JOIN  customer AS c ON o.customerId = c.customerId\n" +
                "\tINNER JOIN  orderDetails AS od ON od.orderId = o.orderId\n" +
                "\tINNER JOIN  finalProduct AS f ON f.finalProductId = od.finalProductId\n" +
                "\tWHERE o.orderId = ? ;\n" +
                "\t");

        preparedStatement.setString(1, "O-005");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            orderDetails.add(new OrderDetails(
                    resultSet.getString("od.finalProductId"),
                    resultSet.getString("o.orderId"),
                    resultSet.getInt("od.orderQty"),
                    resultSet.getDouble("od.discount"),
                    resultSet.getString("f.finalProductName"),
                    resultSet.getString("f.finalProductType"),
                    resultSet.getDouble("f.unitPrice")
            ));

        }
        ResultSet resultSet2 = preparedStatement.executeQuery();
        if (resultSet2.next()) {

            OderDetailsJoinModel oderDetailJoinModel = new OderDetailsJoinModel();
            oderDetailJoinModel.setCustomer(new Customer(
                    resultSet2.getString("o.customerId"),
                    resultSet2.getString("c.customerName"),
                    resultSet2.getString("c.customerAddress"),
                    resultSet2.getString("c.customerContact")));


            oderDetailJoinModel.setOrder(
                    new Order(
                            resultSet2.getString("o.orderId"),
                            resultSet2.getString("o.orderDate"),
                            resultSet2.getString("o.customerId"),
                            resultSet2.getDouble("o.orderCost"),
                            null)
            );

            oderDetailJoinModel.setProducts(
                    new Products(
                            "",
                            resultSet2.getString("f.finalProductName"),
                            resultSet2.getString("f.finalProductType"),
                            0,
                            resultSet2.getDouble("f.unitPrice"))
            );

            oderDetailJoinModel.getOrder().setOrderDetails(orderDetails);
            return oderDetailJoinModel;
        } else {
            return null;
        }
    }

    public void txtOrderIdsOnAction(ActionEvent actionEvent) throws SQLException {

        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("select * from `order` where orderId=?");
        stm.setObject(1, txtOrderId.getText());
        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            Order order = new Order(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4)
            );
            setOrderData(order);
        } else {
            Image image = new Image("/assests/images/fail.png");
            Notifications notifications = Notifications.create();
            notifications.graphic(new ImageView(image));
            notifications.text("Something Went Wrong , Try Again !");
            notifications.title("Failed Message");
            notifications.hideAfter(Duration.seconds(5));
            notifications.position(Pos.TOP_CENTER);
            notifications.darkStyle();
            notifications.show();
        }
    }

    private void setOrderData(Order orderData) {
        txtCustomerId.setText(orderData.getCustomerId());
        txtOrderDate.setText(orderData.getOrderDate());
        txtOrderCost.setText(String.valueOf(orderData.getOrderCost()));

        try {
            Customer customer = new CustomerController().searchCustomer(txtCustomerId.getText());

            txtCustomerName.setText(customer.getCustomerName());
            txtCustomerAddress.setText(customer.getCustomerAddress());
            txtCustomerContact.setText(customer.getCustomerContact());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setOrderDetailsToTable() throws SQLException {

    }

    public void btnSearchOnAction(ActionEvent actionEvent) {

    }
}
