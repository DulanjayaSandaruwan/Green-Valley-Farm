package controller;

import com.jfoenix.controls.JFXButton;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.Item;
import view.tm.ItemTM;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-08
 **/
public class ManagePurchasedItemDetailsFormController {

    public JFXButton btnUpdateItem;
    public JFXButton btnSaveItem;
    public TableView tblItemDetails;
    public TextField txtItemCode;
    public TextField txtItemName;
    public TextField txtItemType;
    public TableColumn colItemCode;
    public TableColumn colItemName;
    public TableColumn colItemType;
    public TableColumn colUnitPrice;
    public TextField txtUnitPrice;

    ObservableList<ItemTM> observableList = FXCollections.observableArrayList();

    public void initialize() throws SQLException {

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colItemType.setCellValueFactory(new PropertyValueFactory<>("itemType"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        setItemValuesToTable(new ItemController().selectAllItems());


    }

    public void txtGetItemCode(ActionEvent actionEvent) throws SQLException {
        String itemCode = txtItemCode.getText();

        Item item1 = new ItemController().searchItem(itemCode);
        if (item1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(item1);
        }
    }

    void setData(Item item) {
        txtItemCode.setText(item.getItemCode());
        txtItemName.setText(item.getItemName());
        txtItemType.setText(item.getItemType());
        txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));
    }

    private void setItemValuesToTable(ArrayList<Item> item) {
        item.forEach(e -> {
                    observableList.add(new ItemTM(e.getItemCode(), e.getItemName(), e.getItemType(), e.getUnitPrice()));
                }
        );
        tblItemDetails.setItems(observableList);
    }

    public void btnItemCodeOnAction(ActionEvent actionEvent) {
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select supItemCode from supplyItem order by supItemCode desc limit 1");

            boolean next = resultSet.next();

            if (next) {

                String oldId = resultSet.getString(1);

                String id = oldId.substring(1, 4);

                int intId = Integer.parseInt(id);

                intId = intId + 1;

                if (intId < 10) {
                    txtItemCode.setText("I00" + intId);
                } else if (intId < 100) {
                    txtItemCode.setText("I0" + intId);
                } else {
                    txtItemCode.setText("I" + intId);
                }
            } else {
                txtItemCode.setText("I001");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearForms();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException {
        if (new ItemController().deleteItem(txtItemCode.getText())) {
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();

            clearForms();
            tblItemDetails.getItems().clear();
            setItemValuesToTable(new ItemController().selectAllItems());
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException {
        Item item3 = new Item(
                txtItemCode.getText(),
                txtItemName.getText(),
                txtItemType.getText(),
                Double.parseDouble("".equals(txtUnitPrice.getText()) ? "0" : txtUnitPrice.getText())
        );

        if (!item3.getItemCode().isEmpty()) {
            if (new ItemController().updateItem(item3)) {
                try {
                    new Alert(Alert.AlertType.CONFIRMATION, "Updated !").showAndWait();
                    clearForms();
                    tblItemDetails.getItems().clear();
                    setItemValuesToTable(new ItemController().selectAllItems());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again").showAndWait();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException {
        Item item2 = new Item(
                txtItemCode.getText(),
                txtItemName.getText(),
                txtItemType.getText(),
                Double.parseDouble("".equals(txtUnitPrice.getText()) ? "0" : txtUnitPrice.getText())
        );
        if (!item2.getItemCode().isEmpty()) {

            if (new ItemController().saveItem(item2)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved..").showAndWait();
                clearForms();
                tblItemDetails.getItems().clear();
                setItemValuesToTable(new ItemController().selectAllItems());
            }

        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again..").showAndWait();
        }
    }

    private void clearForms() {
        txtItemCode.setText("");
        txtItemName.setText("");
        txtItemType.setText("");
        txtUnitPrice.setText("");
    }

    public void tblItemDetailsOnMouseClicked(MouseEvent mouseEvent) {
        if (tblItemDetails.getSelectionModel().getSelectedItem() != null) {
            ItemTM itemTM = (ItemTM) tblItemDetails.getSelectionModel().getSelectedItem();
            txtItemCode.setText(itemTM.getItemCode());
            txtItemName.setText(itemTM.getItemName());
            txtItemType.setText(itemTM.getItemType());
            txtUnitPrice.setText(String.valueOf(itemTM.getUnitPrice()));

        }
    }

    public void textFields_Key_Realeased(KeyEvent keyEvent) {

    }
}
