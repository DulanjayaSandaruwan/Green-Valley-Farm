package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.SearchOrder;
import view.tm.SearchOrderTM;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-19
 **/

public class OrderDetailsFormController {
    public AnchorPane root9;
    public TableView <SearchOrderTM> tblOrderDetails;
    public TableColumn colCustomerId;
    public TableColumn colCustomerName;
    public TableColumn colOrderId;
    public TableColumn colOrderDate;
    public TableColumn colTotalCost;

    public void initialize(){

        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colTotalCost.setCellValueFactory(new PropertyValueFactory<>("totalCost"));

        try {
            loadAllOrdersData();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        tblOrderDetails.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    try {
                        loadOrderProductDetailsUI(newValue.getOrderId());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

    }

    private void loadOrderProductDetailsUI(String orderId) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/SearchOrderProductsDetailsForm.fxml"));
        Parent load = fxmlLoader.load();
        SearchOrderProductsDetailsFormController controller = fxmlLoader.getController();
        controller.loadAllData(orderId);
        root9.getChildren().clear();
        root9.getChildren().add(load);

    }

    private void loadAllOrdersData() throws SQLException {
        ObservableList <SearchOrderTM> tmList = FXCollections.observableArrayList();
        for (SearchOrder tempOrder :  getAllOrders()
                ) {
            tmList.add(
                    new SearchOrderTM(
                            tempOrder.getCustomerId(),
                            tempOrder.getCustomerName(),
                            tempOrder.getOrderId(),
                            tempOrder.getOrderDate(),
                            tempOrder.getTotalCost()
                    )
            );
        }
        tblOrderDetails.setItems(tmList);
    }

    public ArrayList <SearchOrder> getAllOrders() throws SQLException {
        ArrayList <SearchOrder> list = new ArrayList();
        ResultSet resultSet = DBConnection.getInstance().getConnection()
                .prepareStatement(
                        "select c.customerId, c.customerName, o.orderId, o.orderDate, o.orderCost from customer c join `order` o on o.customerId = c.customerID")
                .executeQuery();

        while (resultSet.next()) {
            list.add(
                    new SearchOrder(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getDouble(5)
                    )
            );
        }
        return list;
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ReportsForm.fxml");
        Parent load = FXMLLoader.load(resource);
        root9.getChildren().clear();
        root9.getChildren().add(load);
    }
}