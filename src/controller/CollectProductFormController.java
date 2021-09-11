package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-10
 **/
public class CollectProductFormController {

    public Label lblCollectID;
    public Label lblDate;
    public Label lblTime;
    public ComboBox cmbGardenIds;
    public ComboBox cmbProductIds;
    public TextField txtGardenType;
    public TextField txtPoductType;
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

    public void btnClearOnAction(ActionEvent actionEvent) {
    }

    public void btnAddToCartOnAction(ActionEvent actionEvent) {
    }

    public void btnCollectOnAction(ActionEvent actionEvent) {
    }

    public void textFields_Key_Realeased(KeyEvent keyEvent) {
    }
}
