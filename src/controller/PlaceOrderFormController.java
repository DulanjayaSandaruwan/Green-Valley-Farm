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
import model.Customer;
import model.Order;
import model.OrderDetails;
import model.Products;
import org.controlsfx.control.Notifications;
import view.tm.OrderCartTM;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-11
 **/
public class PlaceOrderFormController {


    public Label lblOrderID;
    public Label lblDate;
    public Label lblTime;
    public ComboBox<String> cmbCustomerIds;
    public TextField txtCustomerName;
    public TextField txtUnitPrice;
    public TextField txtCustomerContact;
    public ComboBox<String> cmbProductIds;
    public TextField txtProductName;
    public TextField txtQtyOnHand;
    public TextField txtQty;
    public TableView tblOrderDetailsCart;
    public TableColumn colProductId;
    public TableColumn colProductName;
    public TableColumn colQty;
    public TableColumn colUnitPrice;
    public TableColumn colDiscount;
    public TableColumn colTotalPrice;
    public Label lblNetPrice;
    public TextField txtDiscount;
    public TextField txtCustomerAddress;
    public int selectedRowInCartRemove = -1;
    public double netPrice = 0;

    ObservableList<OrderCartTM> observableList = FXCollections.observableArrayList();

    public void initialize() {
        loadDateAndTime();

        setOrderId();

        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colTotalPrice.setCellValueFactory(new PropertyValueFactory<>("total"));

        try {
            loadCustomerIds();
            loadProductsIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        cmbCustomerIds.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    try {
                        setCustomerData(newValue);
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

        tblOrderDetailsCart.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) ->
        {
            selectedRowInCartRemove = (int) newValue;
        });

    }

    private void setOrderId() {
        try {
            lblOrderID.setText(new OrderController().getOrderId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
            txtUnitPrice.setText(String.valueOf(products.getUnitPrice()));
            txtQtyOnHand.setText(String.valueOf(products.getQtyOnHand()));
        }
    }

    private void setCustomerData(String customerId) throws SQLException {
        Customer customer = new CustomerController().searchCustomer(customerId);
        if (customer == null) {
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
            txtCustomerName.setText(customer.getCustomerName());
            txtCustomerAddress.setText(customer.getCustomerAddress());
            txtCustomerContact.setText(customer.getCustomerContact());
        }
    }

    private void loadCustomerIds() throws SQLException {
        cmbCustomerIds.getItems().addAll(new CustomerController().getCustomerID());
    }

    private void loadProductsIds() throws SQLException {
        cmbProductIds.getItems().addAll(new ProductsController().getProductID());
    }

    public void btnAddToCartOnAction(ActionEvent actionEvent) {
        String productName = txtProductName.getText();
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        double discount = Double.parseDouble(txtDiscount.getText());
        int qtyForCustomer = Integer.parseInt(txtQty.getText());
        double total = (qtyForCustomer * unitPrice);
        double finalTotal = total - ((total / 100) * discount);

        if (qtyOnHand < qtyForCustomer) {
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

        OrderCartTM orderCartTM = new OrderCartTM(
                cmbProductIds.getValue(),
                productName,
                qtyForCustomer,
                unitPrice,
                discount,
                finalTotal
        );

        int rowNumber = isExists(orderCartTM);

        if (isExists(orderCartTM) == -1) {
            observableList.add(orderCartTM);
        } else {
            OrderCartTM tempTm = observableList.get(rowNumber);
            OrderCartTM newTm = new OrderCartTM(
                    tempTm.getProductId(),
                    tempTm.getProductName(),
                    tempTm.getQty() + qtyForCustomer,
                    unitPrice,
                    discount,
                    total + tempTm.getTotal()
            );

            if (qtyOnHand < tempTm.getQty()) {
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

            observableList.remove(rowNumber);
            observableList.add(newTm);

        }
        tblOrderDetailsCart.setItems(observableList);

        calculateNetPrice();

    }

    private int isExists(OrderCartTM orderCartTM) {

        for (int i = 0; i < observableList.size(); i++) {
            if (orderCartTM.getProductId().equals(observableList.get(i).getProductId())) {
                return i;
            }
        }
        return -1;
    }

    public void calculateNetPrice() {

        for (OrderCartTM orderCartTM : observableList
        ) {
            netPrice += orderCartTM.getTotal();
        }
        lblNetPrice.setText(netPrice + " /=");
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
            tblOrderDetailsCart.refresh();
        }
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
        ArrayList<OrderDetails> orderDetails = new ArrayList<>();

        for (OrderCartTM tempTm : observableList) {
            orderDetails.add(
                    new OrderDetails(
                            tempTm.getProductId(),
                            lblOrderID.getText(),
                            tempTm.getQty(),
                            tempTm.getDiscount()
                    )
            );
        }
        Order order = new Order(
                lblOrderID.getText(),
                lblDate.getText(),
                cmbCustomerIds.getValue(),
                netPrice,
                orderDetails
        );

        try {
            if (new OrderController().placeOrder(order)) {
                Image image = new Image("/assests/images/pass.png");
                Notifications notifications = Notifications.create();
                notifications.graphic(new ImageView(image));
                notifications.text("Successfully Saved !");
                notifications.title("Success Message");
                notifications.hideAfter(Duration.seconds(5));
                notifications.position(Pos.TOP_CENTER);
                notifications.darkStyle();
                notifications.show();

                setOrderId();
                tblOrderDetailsCart.getItems().clear();

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
