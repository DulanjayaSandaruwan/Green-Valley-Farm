package controller;

import com.jfoenix.controls.JFXButton;
import db.DBConnection;
import javafx.animation.*;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Products;
import util.NotificationMessageUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-02
 **/
public class ManagerMainFormController {
    public Label lblDate;
    public Label lblTime;
    public Label lblTitle;
    public AnchorPane root2;
    public AnchorPane root3;
    public JFXButton btnHome;
    public AnchorPane paneSettingPanal;
    public Label lblLogOut;
    public PieChart pieChartProductDetails;
    public Label lblNotificationCount;
    public Label lblOrderCount;
    public Label lblCustomerCount;
    public Label lblProductCount;
    public Label lblMostMovableItem;
    public Label lblUserId;
    public Label lblUserNameText;
    public Label lblUserEmail;
    public Label lblUserRole;
    public int plants = 0;
    public int animal = 0;
    private boolean isSettingPanelVisible;

    public void initialize() {

        checkLowestRemainingItem();

        new NotificationFormController().showNotifications();

        loadDateAndTime();

        try {
            productsCount();
            orderCount();
            customerCount();
            productCountLabel();
            mostMovableItem();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        pieChart();

        paneSettingPanal.setVisible(false);
        isSettingPanelVisible = false;

        lblNotificationCount.setText(String.valueOf(new NotificationFormController().notificationCount()));

        String name = LoginFormController.name;
        lblTitle.setText("Hi " + name + " Welcome To The Farm !");
        lblUserId.setText(LoginFormController.userID);
        lblUserNameText.setText(LoginFormController.name);
        lblUserEmail.setText(LoginFormController.eMail);
        lblUserRole.setText(LoginFormController.role);

    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(
                    currentTime.getHour() + " : " + currentTime.getMinute() + " : " + currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );

        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    public void btnHomeOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManagerMainForm.fxml");
        Parent load = FXMLLoader.load(resource);
        root2.getChildren().clear();
        root2.getChildren().add(load);
    }

    public void ImgAddNewUserOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        URL resource = getClass().getResource("../view/AddNewUserForm.fxml");
        Parent load = FXMLLoader.load(resource);
        root3.getChildren().clear();
        root3.getChildren().add(load);
    }


    public void btnSupplierOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManageSupplierDetails.fxml");
        Parent load = FXMLLoader.load(resource);
        root3.getChildren().clear();
        root3.getChildren().add(load);
    }

    public void btnPurchasedItemOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManagePurchasedItemDetailsForm.fxml");
        Parent load = FXMLLoader.load(resource);
        root3.getChildren().clear();
        root3.getChildren().add(load);
    }

    public void btnPurchaseOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/PurchaseItemsForm.fxml");
        Parent load = FXMLLoader.load(resource);
        root3.getChildren().clear();
        root3.getChildren().add(load);
    }

    public void btnProductsOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManageProductsForm.fxml");
        Parent load = FXMLLoader.load(resource);
        root3.getChildren().clear();
        root3.getChildren().add(load);

    }

    public void btnReportsOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ReportsForm.fxml");
        Parent load = FXMLLoader.load(resource);
        root3.getChildren().clear();
        root3.getChildren().add(load);
    }

    public void imgSettingOnMouseClicked(MouseEvent mouseEvent) {
        FadeTransition fade = new FadeTransition();
        if (!isSettingPanelVisible) {
            paneSettingPanal.setVisible(true);
            fade.setNode(paneSettingPanal);
            fade.setDuration(Duration.millis(500));
            fade.setInterpolator(Interpolator.LINEAR);
            fade.setFromValue(0);
            fade.setToValue(1);
            fade.play();
            isSettingPanelVisible = true;
        } else {
            paneSettingPanal.setVisible(false);
            isSettingPanelVisible = false;
        }

    }

    public void btnGardenOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManageGardenDetailsForm.fxml");
        Parent load = FXMLLoader.load(resource);
        root3.getChildren().clear();
        root3.getChildren().add(load);
    }

    public void btnFarmerOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManageFarmerDetailsForm.fxml");
        Parent load = FXMLLoader.load(resource);
        root3.getChildren().clear();
        root3.getChildren().add(load);
    }

    public void btnCollectProductsOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/CollectProductsForm.fxml");
        Parent load = FXMLLoader.load(resource);
        root3.getChildren().clear();
        root3.getChildren().add(load);
    }

    public void btnCustomerOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManageCustomerDetailsForm.fxml");
        Parent load = FXMLLoader.load(resource);
        root3.getChildren().clear();
        root3.getChildren().add(load);
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/PlaceOrderForm.fxml");
        Parent load = FXMLLoader.load(resource);
        root3.getChildren().clear();
        root3.getChildren().add(load);
    }

    public void lblChangePasswordOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        URL resource = getClass().getResource("../view/ChangePasswordForm.fxml");
        Parent load = FXMLLoader.load(resource);
        root3.getChildren().clear();
        root3.getChildren().add(load);
    }

    public void lblLogOutOnMouseClicked(MouseEvent mouseEvent) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to log out", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.get().equals(ButtonType.YES)) {
            Parent parent = FXMLLoader.load(this.getClass().getResource("../view/LoginForm.fxml"));
            Scene scene = new Scene(parent);
            Stage primaryStage = (Stage) this.root2.getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.setTitle("Login Form");
            primaryStage.centerOnScreen();
        }
    }

    public void imgNotification(MouseEvent mouseEvent) throws IOException {
        URL resource = getClass().getResource("../view/NotificationForm.fxml");
        Parent load = FXMLLoader.load(resource);
        root3.getChildren().clear();
        root3.getChildren().add(load);
    }

    public void productCountLabel() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("select * from finalProduct");
        ResultSet rst = stm.executeQuery();
        int count = 0;
        while (rst.next()) {
            count++;
            lblProductCount.setText(String.valueOf(count));
        }
    }

    public void mostMovableItem() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().
                prepareStatement("select finalProductId,sum(orderQty) as orderQty from orderDetails group by finalProductId order by orderQty desc limit 1;");
        ResultSet rst = stm.executeQuery();
        String productId = null;
        while (rst.next()) {
            productId = rst.getString(1);
        }

        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().
                prepareStatement("select finalProductName from finalProduct where finalProductId = '" + productId + "'");
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            lblMostMovableItem.setText(resultSet.getString(1));
        }

    }

    public void orderCount() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("select * from `order`");
        ResultSet rst = stm.executeQuery();
        int count = 0;
        while (rst.next()) {
            count++;
            lblOrderCount.setText(String.valueOf(count));
        }
    }

    public void customerCount() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("select * from customer");
        ResultSet rst = stm.executeQuery();
        int count = 0;
        while (rst.next()) {
            count++;
            lblCustomerCount.setText(String.valueOf(count));
        }
    }

    public void productsCount() throws SQLException {
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection()
                .prepareStatement("select * from finalProduct");
        ResultSet resultSet = preparedStatement.executeQuery();
        int count = 0;
        while (resultSet.next()) {
            count++;

            if (resultSet.getString(3).equals("Plants")) {
                plants++;
            } else {
                animal++;
            }
        }
    }

    public void pieChart() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Plants", plants),
                new PieChart.Data("Animal", animal)
        );

        pieChartData.forEach(data -> {
            data.nameProperty().bind(Bindings.concat(data.getName(), " Amount ",
                    data.pieValueProperty()));
        });
        pieChartProductDetails.getData().addAll(pieChartData);
        pieChartProductDetails.setStyle("-fx-font-weight:bolder");
    }

    private void checkLowestRemainingItem() {
        try {
            PreparedStatement preparedStatement
                    = DBConnection.getInstance().getConnection()
                    .prepareStatement("select * from finalProduct where qtyOnHand <= 50");
            int qtyOnHand = 0;
            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<Products> products = new ArrayList<>();

            while (resultSet.next()) {
                products.add(new Products(
                        resultSet.getString("finalProductId"),
                        resultSet.getString("finalProductName"),
                        resultSet.getString("finalProductType"),
                        resultSet.getInt("qtyOnHand"),
                        resultSet.getDouble("unitPrice")));

            }

            for (int i = 0; i < products.size(); i++) {
                new NotificationMessageUtil().infoMessage("The production" + " volume of " + products.get(i).getProductName() +
                        " is declining in the warehouses.Be aware of that.");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
