package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.*;
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
    public TableView <OrderDetailsTM> tblOrderDetails;
    public TableColumn colProductId;
    public TableColumn colProductName;
    public TableColumn colProductType;
    public TableColumn colUnitPrice;
    public TableColumn colQuantity;
    public TableColumn colDiscount;
    public TableColumn colTotal;
    ArrayList<OrderDetails> orderDetails = new ArrayList<>();
    ObservableList<OrderDetailsTM> observableList = FXCollections.observableArrayList();

    public void initialize() {

        colProductId.setCellValueFactory(new PropertyValueFactory<>("finalProductId"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("finalProductName"));
        colProductType.setCellValueFactory(new PropertyValueFactory<>("finalProductType"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("orderQty"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("itemTotal"));

    }

    public OderDetailsJoinModel searchOrderDetails(String concatQuery) throws SQLException {

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
                "\tod.itemTotal,\n" +
                "\tf.finalProductName,\n" +
                "\tf.finalProductType,\n" +
                "\tf.unitPrice\n" +
                "\t\n" +
                "\tFROM `order` AS o\n" +
                "\tINNER JOIN  customer AS c ON o.customerId = c.customerId\n" +
                "\tINNER JOIN  orderDetails AS od ON od.orderId = o.orderId\n" +
                "\tINNER JOIN  finalProduct AS f ON f.finalProductId = od.finalProductId\n" +
                "\t" + concatQuery + "\n" +
                "\t");

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            orderDetails.add(new OrderDetails(
                    resultSet.getString("od.finalProductId"),
                    resultSet.getString("o.orderId"),
                    resultSet.getInt("od.orderQty"),
                    resultSet.getDouble("od.discount"),
                    resultSet.getString("f.finalProductName"),
                    resultSet.getString("f.finalProductType"),
                    resultSet.getDouble("f.unitPrice"),
                    resultSet.getDouble("od.itemTotal")
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

        String ord = txtOrderId.getText();
        String concatSql = "where o.orderId = '" + ord + "'";
        OderDetailsJoinModel oderDetailsJoinModel = searchOrderDetails(concatSql);
        setOrderData(oderDetailsJoinModel.getOrder(), oderDetailsJoinModel.getCustomer());
        setDataToGrid();
    }

    private void setOrderData(Order orderData, Customer customer) {
        txtCustomerId.setText(orderData.getCustomerId());
        txtOrderId.setText(orderData.getOrderId());
        txtOrderDate.setText(orderData.getOrderDate());
        txtOrderCost.setText(String.valueOf(orderData.getOrderCost()));
        txtCustomerName.setText(customer.getCustomerName());
        txtCustomerAddress.setText(customer.getCustomerAddress());
        txtCustomerContact.setText(customer.getCustomerContact());
    }


    public void btnSearchOnAction(ActionEvent actionEvent) {
        String ord = txtOrderId.getText();
        OderDetailsJoinModel oderDetailsJoinModel = null;
        try {
            oderDetailsJoinModel = searchOrderDetails(ord);
            setOrderData(oderDetailsJoinModel.getOrder(), oderDetailsJoinModel.getCustomer());
            setDataToGrid();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void setDataToGrid() {
        observableList.clear();
        for (OrderDetails orderDetails : orderDetails) {
            if (null != orderDetails) {
                OrderDetailsTM orderDetailsTM = new OrderDetailsTM();
                orderDetailsTM.setDiscount(orderDetails.getDiscount());
                orderDetailsTM.setFinalProductId(orderDetails.getFinalProductId());
                orderDetailsTM.setFinalProductName(orderDetails.getFinalProductName());
                orderDetailsTM.setFinalProductType(orderDetails.getFinalProductType());
                orderDetailsTM.setOrderQty(orderDetails.getOrderQty());
                orderDetailsTM.setUnitPrice(orderDetails.getUnitPrice());
                orderDetailsTM.setItemTotal(orderDetails.getItemTotal());
                observableList.add(orderDetailsTM);
            }
        }
        if (null != observableList) {
            tblOrderDetails.setItems(observableList);
        }
    }

    public void txtCustomerIdOnAction(ActionEvent actionEvent) {
        String text = txtCustomerId.getText();
        String concatSql = "where c.customerId = '" + text + "'";
        OderDetailsJoinModel oderDetailsJoinModel = null;
        try {
            oderDetailsJoinModel = searchOrderDetails(concatSql);
            setOrderData(oderDetailsJoinModel.getOrder(), oderDetailsJoinModel.getCustomer());
            setDataToGrid();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void txtCustomerContactOnAction(ActionEvent actionEvent) {
        String text = txtCustomerContact.getText();
        String concatSql = "where c.customerContact = '" + text + "'";
        OderDetailsJoinModel oderDetailsJoinModel = null;
        try {
            oderDetailsJoinModel = searchOrderDetails(concatSql);
            setOrderData(oderDetailsJoinModel.getOrder(), oderDetailsJoinModel.getCustomer());
            setDataToGrid();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
