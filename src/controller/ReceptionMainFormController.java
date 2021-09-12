package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

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

    public void imgSettingOnMouseClicked(MouseEvent mouseEvent) {

    }

    public void imgExitOnMouseClicked(MouseEvent mouseEvent) {

    }
}
