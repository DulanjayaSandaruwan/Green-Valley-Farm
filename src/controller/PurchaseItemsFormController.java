package controller;

import com.jfoenix.controls.JFXButton;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import model.*;
import org.controlsfx.control.Notifications;
import util.NotificationMessageUtil;
import util.ValidationUtil;
import view.tm.PurchaseCartTM;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-08
 **/
public class PurchaseItemsFormController {
    public Label lblBuyId;
    public Label lblDate;
    public Label lblTime;
    public ComboBox<String> cmbSupplierIds;
    public TextField txtSupName;
    public TextField txtItemType;
    public TextField txtSupContact;
    public ComboBox<String> cmbItemCodes;
    public TextField txtItemName;
    public TextField txtSupAddress;
    public TextField txtUnitPrice;
    public TextField txtQty;
    public TableView tblBuyDetails;
    public TableColumn colItemCode;
    public TableColumn colItemType;
    public TableColumn colQty;
    public TableColumn colUnitPrice;
    public TableColumn colTotal;
    public Label lblNetPrice;
    public int selectedRowInCartRemove = -1;
    public JFXButton btnAddToCart;
    public JFXButton btnPurchase;
    public JFXButton btnClear;

    ObservableList<PurchaseCartTM> observableList = FXCollections.observableArrayList();

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    Pattern qtyPattern = Pattern.compile("^[0-9]{1,20}$");

    public void initialize() {
        loadDateAndTime();

        setBuyId();

        btnAddToCart.setDisable(true);
        btnPurchase.setDisable(true);
        btnClear.setDisable(true);

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colItemType.setCellValueFactory(new PropertyValueFactory<>("itemType"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        try {
            loadSupplierIds();
            loadItemCodes();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        cmbSupplierIds.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    try {
                        setSupplierData(newValue);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
        );

        cmbItemCodes.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    try {
                        setItemData(newValue);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
        );

        tblBuyDetails.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) ->
        {
            selectedRowInCartRemove = (int) newValue;
        });


        storeValidations();

//        checkTextFields();
//        checkQtyTextField();

    }

    private void loadSupplierIds() throws SQLException {
        cmbSupplierIds.getItems().addAll(new SupplierController().getSupplierID());

    }

    private void loadItemCodes() throws SQLException {
        cmbItemCodes.getItems().addAll(new ItemController().getItemCode());
    }

    private void setItemData(String itemCode) throws SQLException {
        Item item = new ItemController().searchItem(itemCode);
        if (item == null) {
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
            txtItemName.setText(item.getItemName());
            txtItemType.setText(item.getItemType());
            txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));

        }
    }

    private void setSupplierData(String supplierId) throws SQLException {
        Supplier supplier = new SupplierController().searchSupplier(supplierId);
        if (supplier == null) {
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
            txtSupName.setText(supplier.getSupName());
            txtSupAddress.setText(supplier.getSupAddress());
            txtSupContact.setText(supplier.getSupContact());
        }
    }

    private void setBuyId() {
        try {
            lblBuyId.setText(new PurchaseController().getBuyId());
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
            calculateNetPrice();
            tblBuyDetails.refresh();
        }
    }

    public void btnAddToCartOnAction(ActionEvent actionEvent) {
        String itemName = txtItemName.getText();
        int qty = Integer.parseInt(txtQty.getText());
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        double total = (qty * unitPrice);

        PurchaseCartTM purchaseCartTM = new PurchaseCartTM(
                cmbItemCodes.getValue(),
                itemName,
                qty,
                unitPrice,
                total
        );

        int rowNumber = isExists(purchaseCartTM);

        if (isExists(purchaseCartTM) == -1) {
            observableList.add(purchaseCartTM);
        } else {
            PurchaseCartTM tempTm = observableList.get(rowNumber);
            PurchaseCartTM newTm = new PurchaseCartTM(
                    tempTm.getItemCode(),
                    tempTm.getItemType(),
                    tempTm.getQty(),
                    unitPrice,
                    total + tempTm.getTotal()
            );

            observableList.remove(rowNumber);
            observableList.add(newTm);

        }
        tblBuyDetails.setItems(observableList);
        btnClear.setDisable(false);
        btnPurchase.setDisable(false);
        calculateNetPrice();

    }

    private int isExists(PurchaseCartTM purchaseCartTM) {

        for (int i = 0; i < observableList.size(); i++) {
            if (purchaseCartTM.getItemCode().equals(observableList.get(i).getItemCode())) {
                return i;
            }
        }
        return -1;
    }

    public void calculateNetPrice() {
        double netPrice = 0;

        for (PurchaseCartTM purchaseCartTM : observableList
        ) {
            netPrice += purchaseCartTM.getTotal();
        }
        lblNetPrice.setText(String.valueOf(netPrice));
    }

    public void btnPurchaseOnAction(ActionEvent actionEvent) {
        ArrayList<PurchaseDetails> purchaseDetails = new ArrayList<>();

        for (PurchaseCartTM tempTm : observableList) {
            purchaseDetails.add(
                    new PurchaseDetails(
                            lblBuyId.getText(),
                            tempTm.getItemCode(),
                            tempTm.getQty()
                    )
            );
        }
        Purchase purchase = new Purchase(
                lblBuyId.getText(),
                cmbSupplierIds.getValue(),
                lblDate.getText(),
                Double.parseDouble(lblNetPrice.getText()),
                purchaseDetails
        );

        try {
            if (new PurchaseController().purchase(purchase)) {
                Image image = new Image("/assests/images/pass.png");
                Notifications notifications = Notifications.create();
                notifications.graphic(new ImageView(image));
                notifications.text("Successfully Saved !");
                notifications.title("Success Message");
                notifications.hideAfter(Duration.seconds(5));
                notifications.position(Pos.TOP_CENTER);
                notifications.darkStyle();
                notifications.show();

                setBuyId();
                clearForms();

                txtSupName.clear();
                txtSupAddress.clear();
                txtSupContact.clear();
                txtItemName.clear();
                txtItemType.clear();
                txtUnitPrice.clear();

                btnPurchase.setDisable(true);
                btnClear.setDisable(true);
                btnAddToCart.setDisable(true);

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
        lblNetPrice.setText("0");
        tblBuyDetails.getItems().clear();
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

    private void storeValidations() {
        map.put(txtQty, qtyPattern);

    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map, btnAddToCart);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                new NotificationMessageUtil().successMessage("Successfully Added !");
            }
        }
    }

}
