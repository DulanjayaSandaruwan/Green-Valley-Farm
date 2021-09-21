package controller;

import db.DBConnection;
import javafx.scene.control.Button;
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

    public static ArrayList<Products> products = new ArrayList<>();
    public AnchorPane root7;
    public VBox vBoxNotification;

    public void initialize() {
        showNotifications();
        loadNotification();
    }

    public void loadNotification() {
        int index = 0;
        int row = 0;

        for (Products productsDetail : products) {

            Label label = new Label();
            Button button = new Button("Clear");
            label.setStyle("-fx-font-size: 18");
            label.setText("The production" + " volume of " + productsDetail.getProductId() +
                    " is declining in" + "the warehouses." + "\n" + "Be aware of that.");
            button.setStyle("-fx-background-color: red; -fx-translate-x: 850; -fx-max-height: 100; -fx-min-height: 50 ");

            button.setId("" + ++index);
            button.setOnAction(event -> {
                Object node = event.getSource();
                Button b = (Button) node;
                int remove = Integer.parseInt(b.getId());
                products.remove(--remove);
                vBoxNotification.getChildren().clear();
                loadNotification();
            });

            AnchorPane anchorPane = new AnchorPane(label, button);
            AnchorPane anchorPane2 = new AnchorPane(new javafx.scene.control.Label("    "));
            if ((++row % 2 == 0)) {
                anchorPane.setStyle("-fx-background-color: #1fdedb");
            } else {
                anchorPane.setStyle("-fx-background-color: #a5ecd7");
            }

            vBoxNotification.getChildren().addAll(anchorPane);
            vBoxNotification.getChildren().addAll(anchorPane2);
        }
    }

    private void showNotifications() {
        try {
            products.clear();

            PreparedStatement preparedStatement
                    = DBConnection.getInstance().getConnection()
                    .prepareStatement("select * from finalProduct where qtyOnHand <= 100");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Products productsDetail = new Products(resultSet.getString("finalProductId"),
                        resultSet.getString("finalProductName"),
                        resultSet.getString("finalProductType"),
                        resultSet.getInt("qtyOnHand"),
                        resultSet.getDouble("unitPrice"));

                products.add(productsDetail);
                notificationCount();
//                System.out.println(products.size());

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public int notificationCount(){
        return products.size();
    }
}
