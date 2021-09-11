package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-11
 **/
public class PlaceOrderFormController {


    public Label lblOrderID;
    public Label lblDate;
    public Label lblTime;
    public ComboBox cmbCustomerIds;
    public TextField txtCustomerName;
    public TextField txtUnitPrice;
    public TextField txtCustomerContact;
    public ComboBox cmbProductIds;
    public TextField txtProductName;
    public TextField txtGardenLocation;
    public TextField txtQtyOnHand;
    public TextField txtQty;
    public TableView txtOrderDetails;
    public TableColumn colProductId;
    public TableColumn colProductName;
    public TableColumn colQty;
    public TableColumn colUnitPrice;
    public TableColumn colDiscount;
    public TableColumn colTotalPrice;
    public Label lblNetPrice;
    public TextField txtDiscount;

    public void btnAddToCartOnAction(ActionEvent actionEvent) {
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
    }

    public void textFields_Key_Realeased(KeyEvent keyEvent) {
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
    }
}
