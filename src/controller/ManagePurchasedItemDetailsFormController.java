package controller;

import com.jfoenix.controls.JFXButton;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import model.Item;
import org.controlsfx.control.Notifications;
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
    public TableView <ItemTM> tblItemDetails;
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
            Image image = new Image("/assests/images/pass.png");
            Notifications notifications = Notifications.create();
            notifications.graphic(new ImageView(image));
            notifications.text("Successfully Deleted !");
            notifications.title("Success Message");
            notifications.hideAfter(Duration.seconds(5));
            notifications.position(Pos.TOP_CENTER);
            notifications.darkStyle();
            notifications.show();

            clearForms();
            tblItemDetails.getItems().clear();
            setItemValuesToTable(new ItemController().selectAllItems());
        } else {
            Image image = new Image("/assests/images/fail.png");
            Notifications notifications = Notifications.create();
            notifications.graphic(new ImageView(image));
            notifications.text("Something Went Wrong , Try Again !");
            notifications.title("Failed Message");
            notifications.position(Pos.TOP_CENTER);
            notifications.hideAfter(Duration.seconds(5));
            notifications.darkStyle();
            notifications.show();
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

                    Image image = new Image("/assests/images/pass.png");
                    Notifications notifications = Notifications.create();
                    notifications.graphic(new ImageView(image));
                    notifications.text("Successfully Updated !");
                    notifications.title("Success Message");
                    notifications.hideAfter(Duration.seconds(5));
                    notifications.position(Pos.TOP_CENTER);
                    notifications.darkStyle();
                    notifications.show();

                    clearForms();
                    tblItemDetails.getItems().clear();
                    setItemValuesToTable(new ItemController().selectAllItems());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
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
    }

    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException {
        Item item2 = new Item(
                txtItemCode.getText(),
                txtItemName.getText(),
                txtItemType.getText(),
                Double.parseDouble("".equals(txtUnitPrice.getText()) ? "0" : txtUnitPrice.getText())
        );
        if (!item2.getItemCode().isEmpty() && !item2.getItemName().isEmpty() && !item2.getItemType().isEmpty()) {

            if (new ItemController().saveItem(item2)) {

                Image image = new Image("/assests/images/pass.png");
                Notifications notifications = Notifications.create();
                notifications.graphic(new ImageView(image));
                notifications.text("Successfully Saved !");
                notifications.title("Success Message");
                notifications.hideAfter(Duration.seconds(5));
                notifications.position(Pos.TOP_CENTER);
                notifications.darkStyle();
                notifications.show();

                clearForms();
                tblItemDetails.getItems().clear();
                setItemValuesToTable(new ItemController().selectAllItems());
            }

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
    }

    private void clearForms() {
        txtItemCode.setText("");
        txtItemName.setText("");
        txtItemType.setText("");
        txtUnitPrice.setText("");
    }

    public void tblItemDetailsOnMouseClicked(MouseEvent mouseEvent) {
        if (tblItemDetails.getSelectionModel().getSelectedItem() != null) {
            ItemTM itemTM = tblItemDetails.getSelectionModel().getSelectedItem();
            txtItemCode.setText(itemTM.getItemCode());
            txtItemName.setText(itemTM.getItemName());
            txtItemType.setText(itemTM.getItemType());
            txtUnitPrice.setText(String.valueOf(itemTM.getUnitPrice()));

        }
    }

    public void textFields_Key_Realeased(KeyEvent keyEvent) {

    }
}
