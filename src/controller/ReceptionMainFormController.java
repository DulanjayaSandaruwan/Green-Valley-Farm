package controller;

import db.DBConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.Optional;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-02
 **/
public class ReceptionMainFormController {

    public AnchorPane root4;
    public Label lblTitle;
    public Label lblDate;
    public Label lblTime;
    public AnchorPane root5;
    public PieChart pieChrtProductDetails;
    public int plants = 0;
    public int animal = 0;
    public Label lblProductCount;
    public Label lblFarmerCount;
    public Label lblMostMovableItem;
    public Label lblCustomerCount;
    public Label lblOrderCount;
    public Label lblUserNameText;
    public Label lblUserEmail;
    public Label lblUserRole;
    public Label lblUserId;
    public Label lblNotificationCount;

    public void initialize() {
        loadDateAndTime();

        new NotificationFormController().showNotifications();

        try {
            productsCount();
            productCountLabel();
            farmerCount();
            mostMovebleItem();
            customerCount();
            orderCount();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        pieChart();

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
        URL resource = getClass().getResource("../view/ReceptionMainForm.fxml");
        Parent load = FXMLLoader.load(resource);
        root4.getChildren().clear();
        root4.getChildren().add(load);
    }

    public void btnFarmerOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManageFarmerDetailsForm.fxml");
        Parent load = FXMLLoader.load(resource);
        root5.getChildren().clear();
        root5.getChildren().add(load);
    }

    public void btnProductsOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManageProductsForm.fxml");
        Parent load = FXMLLoader.load(resource);
        root5.getChildren().clear();
        root5.getChildren().add(load);
    }

    public void btnCollectProductsOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/CollectProductsForm.fxml");
        Parent load = FXMLLoader.load(resource);
        root5.getChildren().clear();
        root5.getChildren().add(load);
    }

    public void btnCustomerOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManageCustomerDetailsForm.fxml");
        Parent load = FXMLLoader.load(resource);
        root5.getChildren().clear();
        root5.getChildren().add(load);
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/PlaceOrderForm.fxml");
        Parent load = FXMLLoader.load(resource);
        root5.getChildren().clear();
        root5.getChildren().add(load);
    }

    public void btnGardenOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManageGardenDetailsForm.fxml");
        Parent load = FXMLLoader.load(resource);
        root5.getChildren().clear();
        root5.getChildren().add(load);
    }

    public void imgExitOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to log out", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();

        if(buttonType.get().equals(ButtonType.YES)) {
            Parent parent = FXMLLoader.load(this.getClass().getResource("../view/LoginForm.fxml"));
            Scene scene = new Scene(parent);
            Stage primaryStage = (Stage) this.root5.getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.setTitle("Login Form");
            primaryStage.centerOnScreen();
        }
    }

    public void imgNotification(MouseEvent mouseEvent) throws IOException {
        URL resource = getClass().getResource("../view/NotificationForm.fxml");
        Parent load = FXMLLoader.load(resource);
        root5.getChildren().clear();
        root5.getChildren().add(load);
    }

    public void productCountLabel() throws SQLException, ClassNotFoundException {
        PreparedStatement stm= DBConnection.getInstance().getConnection().prepareStatement("select * from finalProduct");
        ResultSet rst=stm.executeQuery();
        int a=0;
        while (rst.next()){
            a++;
            lblProductCount.setText(String.valueOf(a));
        }
    }

    public void farmerCount() throws SQLException, ClassNotFoundException {
        PreparedStatement stm= DBConnection.getInstance().getConnection().prepareStatement("select * from farmer");
        ResultSet rst=stm.executeQuery();
        int a=0;
        while (rst.next()){
            a++;
            lblFarmerCount.setText(String.valueOf(a));
        }
    }

    public void mostMovebleItem() throws SQLException, ClassNotFoundException {
        PreparedStatement stm= DBConnection.getInstance().getConnection().
                prepareStatement("select finalProductId,sum(orderQty) as orderQty from orderDetails group by finalProductId order by orderQty desc limit 1;");
        ResultSet rst=stm.executeQuery();
        String productId = null;
        while (rst.next()){
            productId = rst.getString(1);
        }

        PreparedStatement preparedStatement= DBConnection.getInstance().getConnection().
                prepareStatement("select finalProductName from finalProduct where finalProductId = '"+productId+"'");
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            lblMostMovableItem.setText(resultSet.getString(1));
        }

    }

    public void orderCount() throws SQLException, ClassNotFoundException {
        PreparedStatement stm= DBConnection.getInstance().getConnection().prepareStatement("select * from `order`");
        ResultSet rst=stm.executeQuery();
        int a=0;
        while (rst.next()){
            a++;
            lblOrderCount.setText(String.valueOf(a));
        }
    }

    public void customerCount() throws SQLException, ClassNotFoundException {
        PreparedStatement stm= DBConnection.getInstance().getConnection().prepareStatement("select * from customer");
        ResultSet rst=stm.executeQuery();
        int a=0;
        while (rst.next()){
            a++;
            lblCustomerCount.setText(String.valueOf(a));
        }
    }

    public void productsCount() throws SQLException {
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection()
                .prepareStatement("select * from finalProduct");
        ResultSet resultSet = preparedStatement.executeQuery();
        int count = 0;
        while (resultSet.next()){
            count++;

            if (resultSet.getString(3).equals("Plants")){
                plants++;
            }else {
                animal++;
            }
        }
    }

    public void pieChart(){
        ObservableList<PieChart.Data> pieChartData= FXCollections.observableArrayList(
                new PieChart.Data("Plants",plants),
                new PieChart.Data("Animal",animal)
        );

        pieChartData.forEach(data -> {data.nameProperty().bind(Bindings.concat(data.getName()," Amount ",
                data.pieValueProperty()));});
        pieChrtProductDetails.getData().addAll(pieChartData);
        pieChrtProductDetails.setStyle("-fx-font-weight:bolder");
    }

}
