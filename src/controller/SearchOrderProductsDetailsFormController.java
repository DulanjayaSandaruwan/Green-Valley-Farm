package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.OrderProducts;
import view.tm.OrderProductsTM;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-24
 **/
public class SearchOrderProductsDetailsFormController {

    public TableView <OrderProductsTM> tblSearchProductDetails;
    public TableColumn colProductId;
    public TableColumn colQuantity;
    public TableColumn colTotalCost;
    public Label lblOrderId;
    public AnchorPane root10;

    public void initialize(){

        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotalCost.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
    }

    public void loadAllData(String id){

        lblOrderId.setText(id);

        try {
            ObservableList<OrderProductsTM> tmList = FXCollections.observableArrayList();
            for (OrderProducts tempOrder :  getAllOrderProducts(id)
                 ) {
                tmList.add(new OrderProductsTM(tempOrder.getProductId(), tempOrder.getQty(), tempOrder.getTotalCost()));
            }

            tblSearchProductDetails.setItems(tmList);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ArrayList <OrderProducts> getAllOrderProducts(String orderId) throws SQLException {

        ArrayList <OrderProducts> orderProducts = new ArrayList();

        ResultSet resultSet = DBConnection.getInstance().getConnection().
                prepareStatement("select finalProductId, orderQty, itemTotal from orderDetails where orderId = " +
                        "'" + orderId + "'").executeQuery();

        while (resultSet.next()){
            orderProducts.add(
              new OrderProducts(
                      resultSet.getString(1),
                      resultSet.getInt(2),
                      resultSet.getDouble(3)
              )
            );
        }
        return orderProducts;
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/OrderDetailsForm.fxml");
        Parent load = FXMLLoader.load(resource);
        root10.getChildren().clear();
        root10.getChildren().add(load);
    }
}
