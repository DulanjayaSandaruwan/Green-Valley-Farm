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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import model.Supplier;
import org.controlsfx.control.Notifications;
import util.ValidationUtil;
import view.tm.SupplierTM;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-04
 **/
public class ManageSupplierDetailsController {


    public TableView<SupplierTM> tblSupplierDetails;
    public TableColumn colSupID;
    public TableColumn colSupName;
    public TableColumn colSupAddress;
    public TableColumn colSupContact;
    public TextField txtSupID;
    public TextField txtSupplierName;
    public TextField txtSupAddress;
    public TextField txtSupContact;
    public JFXButton btnSaveSupplier;
    public JFXButton btnUpdateSupplier;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    Pattern namePattern = Pattern.compile("^[A-z ]{5,20}$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9/ ]{6,30}$");
    Pattern contactPattern = Pattern.compile("^(070|071|075|076|077|078)[-]?[0-9]{7}$");

    ObservableList<SupplierTM> observableList = FXCollections.observableArrayList();

    public void initialize() {

        colSupID.setCellValueFactory(new PropertyValueFactory<>("supID"));
        colSupName.setCellValueFactory(new PropertyValueFactory<>("supName"));
        colSupAddress.setCellValueFactory(new PropertyValueFactory<>("supAddress"));
        colSupContact.setCellValueFactory(new PropertyValueFactory<>("supContact"));

        try {
            setSupplierValuesToTable(new SupplierController().selectAllSuppliers());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        storeValidations();

    }

    public void txtGetSupplierID(ActionEvent actionEvent) throws SQLException {
        String supId = txtSupID.getText();

        Supplier supplier1 = new SupplierController().searchSupplier(supId);
        if (supplier1 == null) {
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
            setData(supplier1);
        }
    }

    void setData(Supplier supplier) {
        txtSupID.setText(supplier.getSupID());
        txtSupplierName.setText(supplier.getSupName());
        txtSupAddress.setText(supplier.getSupAddress());
        txtSupContact.setText(supplier.getSupContact());
    }

    private void setSupplierValuesToTable(ArrayList<Supplier> suppliers) {
        suppliers.forEach(e -> {
                    observableList.add(new SupplierTM(e.getSupID(), e.getSupName(), e.getSupAddress(), e.getSupContact()));
                }
        );
        tblSupplierDetails.setItems(observableList);
    }

    public void btnSupplierIdOnAction(ActionEvent actionEvent) {

        Connection connection = DBConnection.getInstance().getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select supId from supplier order by supId desc limit 1");

            boolean next = resultSet.next();

            if (next) {

                String oldId = resultSet.getString(1);

                String id = oldId.substring(1, 4);

                int intId = Integer.parseInt(id);

                intId = intId + 1;

                if (intId < 10) {
                    txtSupID.setText("S00" + intId);
                } else if (intId < 100) {
                    txtSupID.setText("S0" + intId);
                } else {
                    txtSupID.setText("S" + intId);
                }
            } else {
                txtSupID.setText("S001");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException {
        Supplier supplier2 = new Supplier(txtSupID.getText(),
                txtSupplierName.getText(),
                txtSupAddress.getText(),
                txtSupContact.getText()
        );
        if (!supplier2.getSupID().isEmpty() && !supplier2.getSupName().isEmpty() &&
                !supplier2.getSupAddress().isEmpty() && !supplier2.getSupContact().isEmpty()) {
            if (new SupplierController().saveSupplier(supplier2)) {

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
                tblSupplierDetails.getItems().clear();
                setSupplierValuesToTable(new SupplierController().selectAllSuppliers());
            }else {
                Image image = new Image("/assests/images/fail.png");
                Notifications notifications = Notifications.create();
                notifications.graphic(new ImageView(image));
                notifications.text("Duplicate Entry, Try Again !");
                notifications.title("Failed Message");
                notifications.hideAfter(Duration.seconds(5));
                notifications.position(Pos.TOP_CENTER);
                notifications.darkStyle();
                notifications.show();
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

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException {
        Supplier supplier3 = new Supplier(
                txtSupID.getText(),
                txtSupplierName.getText(),
                txtSupAddress.getText(),
                txtSupContact.getText()
        );
        if (!supplier3.getSupID().isEmpty()) {
            if (new SupplierController().updateSupplier(supplier3)) {
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
                    tblSupplierDetails.getItems().clear();
                    setSupplierValuesToTable(new SupplierController().selectAllSuppliers());
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

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException {
        if (new SupplierController().deleteSupplier(txtSupID.getText())) {
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
            tblSupplierDetails.getItems().clear();
            setSupplierValuesToTable(new SupplierController().selectAllSuppliers());
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

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearForms();
    }

    private void clearForms() {
        txtSupID.setText("");
        txtSupplierName.setText("");
        txtSupAddress.setText("");
        txtSupContact.setText("");
    }


    public void tblSupplierDetailsOnMouseClicked(MouseEvent mouseEvent) {
        if (tblSupplierDetails.getSelectionModel().getSelectedItem() != null) {
            SupplierTM supplierTM = tblSupplierDetails.getSelectionModel().getSelectedItem();
            txtSupID.setText(supplierTM.getSupID());
            txtSupplierName.setText(supplierTM.getSupName());
            txtSupAddress.setText(supplierTM.getSupAddress());
            txtSupContact.setText(supplierTM.getSupContact());

        }
    }

    private void storeValidations() {
        btnSaveSupplier.setDisable(true);
        map.put(txtSupplierName, namePattern);
        map.put(txtSupAddress, addressPattern);
        map.put(txtSupContact, contactPattern);

    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map, btnSaveSupplier);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                Image image = new Image("/assests/images/pass.png");
                Notifications notifications = Notifications.create();
                notifications.graphic(new ImageView(image));
                notifications.text("Successfully Saved !");
                notifications.title("Success Message");
                notifications.hideAfter(Duration.seconds(5));
                notifications.position(Pos.TOP_CENTER);
                notifications.darkStyle();
                notifications.show();
            }
        }
    }

}
