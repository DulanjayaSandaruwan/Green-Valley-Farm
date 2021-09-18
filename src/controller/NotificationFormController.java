package controller;

import db.DBConnection;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.Products;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-18
 **/
public class NotificationFormController {

    public AnchorPane root7;
    public VBox vBoxNotification;
    public static ArrayList<Products> products = new ArrayList<>();

    public void initialize() {
        showNotifications();


        for (Products productsDetail : products) {
            Label label = new Label();
            label.setStyle("-fx-font-size: 50");
            label.setText(productsDetail.getProductId());
            AnchorPane anchorPane = new AnchorPane(label);
            AnchorPane anchorPane2 = new AnchorPane(new javafx.scene.control.Label("    "));
            anchorPane.setStyle("-fx-background-color: #2D75E8");
            anchorPane.setStyle("-fx-max-height: 5420");

            products.size();

            vBoxNotification.getChildren().addAll(anchorPane);
            vBoxNotification.getChildren().addAll(anchorPane2);
        }

    }

    private void showNotifications() {
        try {
            products.clear();
            PreparedStatement preparedStatement
                    = DBConnection.getInstance().getConnection().prepareStatement("select * from finalProduct where qtyOnHand < 5000");
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                Products productsDetail = new Products(resultSet.getString("finalProductId"),
                        resultSet.getString("finalProductName"),
                        resultSet.getString("finalProductType"),
                        resultSet.getInt("qtyOnHand"),
                        resultSet.getDouble("unitPrice"));

                products.add(productsDetail);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
