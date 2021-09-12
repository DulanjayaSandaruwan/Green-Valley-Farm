package controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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

    public void initialize() {
        loadDateAndTime();

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
}
