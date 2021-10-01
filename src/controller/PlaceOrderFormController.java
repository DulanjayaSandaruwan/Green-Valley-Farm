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
import model.Customer;
import model.Order;
import model.OrderDetails;
import model.Products;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.Notifications;
import util.NotificationMessageUtil;
import util.ValidationUtil;
import view.tm.OrderCartTM;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

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
    public JFXButton btnAddToCart;
    public JFXButton btnPlaceOrder;
    public JFXButton btnClear;

    ObservableList<OrderCartTM> observableList = FXCollections.observableArrayList();

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    Pattern qtyPattern = Pattern.compile("^[0-9]{1,20}$");
    Pattern discountPattern = Pattern.compile("^[0-9]{0,20}$");

    public void initialize() {
        loadDateAndTime();

        setOrderId();

        btnPlaceOrder.setDisable(true);

        btnAddToCart.setDisable(true);

        btnClear.setDisable(true);

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

        storeValidations();

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
            new NotificationMessageUtil().errorMessage("Empty Results Set , Try Again !");

        } else {
            txtProductName.setText(products.getProductName());
            txtUnitPrice.setText(String.valueOf(products.getUnitPrice()));
            txtQtyOnHand.setText(String.valueOf(products.getQtyOnHand()));
        }
    }

    private void setCustomerData(String customerId) throws SQLException {
        Customer customer = new CustomerController().searchCustomer(customerId);
        if (customer == null) {
            new NotificationMessageUtil().errorMessage("Empty Results Set , Try Again !");
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
        double discount = 0.0;
        int qtyForCustomer = Integer.parseInt(txtQty.getText());

        if (txtDiscount.getText().isEmpty()) {
            discount = 0.0;
        } else {
            discount = Double.parseDouble(txtDiscount.getText());
        }
        double total = (qtyForCustomer * unitPrice);
        double finalTotal = total - ((total / 100) * discount);

        if (qtyOnHand < qtyForCustomer) {
            new NotificationMessageUtil().errorMessage("Item count has exceed limit of " + qtyOnHand + " Plz select correct one");
            return;
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
                new NotificationMessageUtil().errorMessage("Something Went Wrong , Try Again !");
            }

            observableList.remove(rowNumber);
            observableList.add(newTm);

        }
        tblOrderDetailsCart.setItems(observableList);

        calculateNetPrice();
        btnClear.setDisable(false);
        btnPlaceOrder.setDisable(false);

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
        double netPrice = 0;

        for (OrderCartTM orderCartTM : observableList
        ) {
            netPrice += orderCartTM.getTotal();
        }
        lblNetPrice.setText(String.valueOf(netPrice));
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        if (selectedRowInCartRemove == -1) {
            new NotificationMessageUtil().errorMessage("Something Went Wrong , Try Again !");

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
                            tempTm.getDiscount(),
                            "",
                            "",
                            0,
                            tempTm.getTotal()
                    )
            );
        }
        Order order = new Order(
                lblOrderID.getText(),
                lblDate.getText(),
                cmbCustomerIds.getValue(),
                Double.parseDouble(lblNetPrice.getText()),
                orderDetails
        );

        try {
            if (new OrderController().placeOrder(order)) {
                new NotificationMessageUtil().successMessage("The Order Is Complete !");

                salesInvoice();

                setOrderId();
                clearForms();
                btnPlaceOrder.setDisable(true);
                btnAddToCart.setDisable(true);
                btnClear.setDisable(true);

                txtCustomerName.clear();
                txtCustomerAddress.clear();
                txtCustomerContact.clear();
                txtProductName.clear();
                txtQtyOnHand.clear();
                txtUnitPrice.clear();

            } else {
               new NotificationMessageUtil().errorMessage("Something Went Wrong , Try Again !");
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

    public void clearForms() {
        txtQty.setText("");
        txtDiscount.setText("");
        lblNetPrice.setText("0");
        tblOrderDetailsCart.getItems().clear();
    }

    private void storeValidations() {
        map.put(txtQty, qtyPattern);
        map.put(txtDiscount, discountPattern);

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

    public void salesInvoice() throws SQLException {
        JasperDesign jasperDesign = null;

        String orderId = lblOrderID.getText();

        LinkedHashMap map = new LinkedHashMap();
        map.put("OrderId", orderId);

        try {
            jasperDesign = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/SalesInvoice.jrxml"));
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, new JRBeanArrayDataSource(observableList.toArray()));
            JasperViewer.viewReport(jasperPrint);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}
