package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import model.*;
import org.controlsfx.control.Notifications;
import view.tm.CollectCartTM;
import view.tm.OrderCartTM;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-10
 **/
public class CollectProductFormController {

    public Label lblCollectID;
    public Label lblDate;
    public Label lblTime;
    public ComboBox <String> cmbGardenIds;
    public ComboBox <String> cmbProductIds;
    public TextField txtGardenType;
    public TextField txtProductType;
    public TextField txtDescription;
    public TextField txtProductName;
    public TextField txtGardenLocation;
    public TextField txtQtyOnHand;
    public TextField txtQty;
    public TableView tblCollectDetails;
    public TableColumn colProductId;
    public TableColumn colProductName;
    public TableColumn colProductType;
    public TableColumn colQuantity;
    public int selectedRowInCartRemove = -1;

    ObservableList<CollectCartTM> observableList = FXCollections.observableArrayList();

    public void initialize(){
        loadDateAndTime();

        setCollectId();

        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colProductType.setCellValueFactory(new PropertyValueFactory<>("productType"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("qty"));

        try {
            loadGardenIds();
            loadProductsIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        cmbGardenIds.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    try {
                        setGardenData(newValue);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
        );

        cmbProductIds.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    try {
                        setProductData(newValue);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
        );

        tblCollectDetails.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) ->
        {
            selectedRowInCartRemove = (int) newValue;
        });

    }

    private void setProductData(String productId) throws SQLException {
        Products products = new ProductsController().searchProducts(productId);
        if (products == null) {
            Image image = new Image("/assests/images/fail.png");
            Notifications notifications = Notifications.create();
            notifications.graphic(new ImageView(image));
            notifications.text("Something Went Wrong , Empty Results Set , Try Again !");
            notifications.title("Failed Message");
            notifications.hideAfter(Duration.seconds(5));
            notifications.position(Pos.TOP_CENTER);
            notifications.darkStyle();
            notifications.show();
        } else {
            txtProductName.setText(products.getProductName());
            txtProductType.setText(products.getProductType());
            txtQtyOnHand.setText(String.valueOf(products.getQtyOnHand()));
        }
    }

    private void setGardenData(String gardenId) throws SQLException {
        Garden garden = new GardenController().searchGarden(gardenId);
        if (garden == null) {
            Image image = new Image("/assests/images/fail.png");
            Notifications notifications = Notifications.create();
            notifications.graphic(new ImageView(image));
            notifications.text("Something Went Wrong , Empty Results Set , Try Again !");
            notifications.title("Failed Message");
            notifications.hideAfter(Duration.seconds(5));
            notifications.position(Pos.TOP_CENTER);
            notifications.darkStyle();
            notifications.show();
        } else {
            txtGardenType.setText(garden.getGardenType());
            txtGardenLocation.setText(garden.getGardenLocation());
            txtDescription.setText(garden.getDescription());
        }
    }

    private void loadGardenIds() throws SQLException {
        cmbGardenIds.getItems().addAll(new GardenController().getGardenID());
    }

    private void loadProductsIds() throws SQLException {
        cmbProductIds.getItems().addAll(new ProductsController().getProductID());
    }

    private void setCollectId() {
        try {
            lblCollectID.setText(new CollectProductsController().getCollectId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        if (selectedRowInCartRemove == -1) {
            Image image = new Image("/assests/images/fail.png");
            Notifications notifications = Notifications.create();
            notifications.graphic(new ImageView(image));
            notifications.text("Something Went Wrong , Try Again !");
            notifications.title("Failed Message");
            notifications.hideAfter(Duration.seconds(5));
            notifications.position(Pos.TOP_CENTER);
            notifications.darkStyle();
            notifications.show();
        } else {
            observableList.remove(selectedRowInCartRemove);
            tblCollectDetails.refresh();
        }
    }

    public void btnAddToCartOnAction(ActionEvent actionEvent) {
        String productName = txtProductName.getText();
        String productType = txtProductType.getText();
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
        int qtyForStore = Integer.parseInt(txtQty.getText());

        CollectCartTM collectCartTM = new CollectCartTM(
                cmbProductIds.getValue(),
                productName,
                productType,
                qtyForStore
        );

        int rowNumber = isExists(collectCartTM);

        if (isExists(collectCartTM) == -1) {
            observableList.add(collectCartTM);
        } else {
            CollectCartTM tempTm = observableList.get(rowNumber);
            CollectCartTM newTm = new CollectCartTM(
                    tempTm.getProductId(),
                    tempTm.getProductName(),
                    tempTm.getProductType(),
                    tempTm.getQty() + qtyForStore
            );

            observableList.remove(rowNumber);
            observableList.add(newTm);
        }
        tblCollectDetails.setItems(observableList);
    }

    private int isExists(CollectCartTM collectCartTM) {

        for (int i = 0; i < observableList.size(); i++) {
            if (collectCartTM.getProductId().equals(observableList.get(i).getProductId())) {
                return i;
            }
        }
        return -1;
    }

    public void btnCollectOnAction(ActionEvent actionEvent) {
        ArrayList<FinalProductDetails> finalProductDetails = new ArrayList<>();

        for (CollectCartTM tempTm : observableList) {
            finalProductDetails.add(
                    new FinalProductDetails(
                            tempTm.getProductId(),
                            lblCollectID.getText(),
                            tempTm.getQty()
                    )
            );
        }
        Collect collect = new Collect(
                lblCollectID.getText(),
                lblDate.getText(),
                cmbGardenIds.getValue(),
                finalProductDetails
        );

        try {
            if (new CollectProductsController().collectProducts(collect)) {
                Image image = new Image("/assests/images/pass.png");
                Notifications notifications = Notifications.create();
                notifications.graphic(new ImageView(image));
                notifications.text("Successfully Saved !");
                notifications.title("Success Message");
                notifications.hideAfter(Duration.seconds(5));
                notifications.position(Pos.TOP_CENTER);
                notifications.darkStyle();
                notifications.show();

                setCollectId();
                clearForms();

            } else {
                Image image = new Image("/assests/images/fail.png");
                Notifications notifications = Notifications.create();
                notifications.graphic(new ImageView(image));
                notifications.text("Something Went Wrong , Try Again !");
                notifications.title("Failed Message");
                notifications.hideAfter(Duration.seconds(5));
                notifications.position(Pos.TOP_CENTER);
                notifications.darkStyle();
                notifications.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearForms(){
        txtQty.setText("");
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

    public void textFields_Key_Released(KeyEvent keyEvent) {

    }
}
