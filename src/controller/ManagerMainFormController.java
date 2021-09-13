package controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
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
    private boolean isSettingPanelVisible;

    public void initialize() {
        loadDateAndTime();

        paneSettingPanal.setVisible(false);
        isSettingPanelVisible = false;

        String name = LoginFormController.name;
        lblTitle.setText("Hi " + name + " Welcome To The Farm !");
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
        if(!isSettingPanelVisible){
            paneSettingPanal.setVisible(true);
            fade.setNode(paneSettingPanal);
            fade.setDuration(Duration.millis(500));
            fade.setInterpolator(Interpolator.LINEAR);
            fade.setFromValue(0);
            fade.setToValue(1);
            fade.play();
            isSettingPanelVisible = true;
        }else{
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

    public void lblChangePasswordOnMouseClicked(MouseEvent mouseEvent) {

    }

    public void lblLogOutOnMouseClicked(MouseEvent mouseEvent) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to log out", ButtonType.YES, ButtonType.NO);
        Optional <ButtonType> buttonType = alert.showAndWait();

        if(buttonType.get().equals(ButtonType.YES)) {
            Parent parent = FXMLLoader.load(this.getClass().getResource("../view/LoginForm.fxml"));
            Scene scene = new Scene(parent);
            Stage primaryStage = (Stage) this.root2.getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.setTitle("Login Form");
            primaryStage.centerOnScreen();
        }
    }
}
