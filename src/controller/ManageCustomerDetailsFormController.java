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
import model.Customer;
import org.controlsfx.control.Notifications;
import util.ValidationUtil;
import view.tm.CustomerTM;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-11
 **/
public class ManageCustomerDetailsFormController {
    public JFXButton btnSaveCustomer;
    public TableView<CustomerTM> tblCustomerDetails;
    public TableColumn colCustomerID;
    public TableColumn colCustomerName;
    public TableColumn colCustomerAddress;
    public TableColumn colCustomerContact;
    public TextField txtCustomerID;
    public TextField txtCustomerName;
    public TextField txtCustomerAddress;
    public TextField txtCustomerContact;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    Pattern namePattern = Pattern.compile("^[A-z ]{5,20}$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9/ ]{6,30}$");
    Pattern contactPattern = Pattern.compile("^(070|071|075|076|077|078)[-]?[0-9]{7}$");

    ObservableList<CustomerTM> observableList = FXCollections.observableArrayList();

    public void initialize() {

        colCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colCustomerAddress.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        colCustomerContact.setCellValueFactory(new PropertyValueFactory<>("customerContact"));

        try {
            setCustomerValuesToTable(new CustomerController().selectAllCustomers());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        storeValidations();

    }

    public void txtGetCustomerId(ActionEvent actionEvent) throws SQLException {
        String customerId = txtCustomerID.getText();

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
            setData(customer);
        }
    }

    void setData(Customer customer) {
        txtCustomerID.setText(customer.getCustomerId());
        txtCustomerName.setText(customer.getCustomerName());
        txtCustomerAddress.setText(customer.getCustomerAddress());
        txtCustomerContact.setText(customer.getCustomerContact());
    }

    private void setCustomerValuesToTable(ArrayList<Customer> customers) {
        customers.forEach(e -> {
                    observableList.add(new CustomerTM(e.getCustomerId(), e.getCustomerName(), e.getCustomerAddress(), e.getCustomerContact()));
                }
        );
        tblCustomerDetails.setItems(observableList);
    }

    public void btnCustomerIdOnAction(ActionEvent actionEvent) {
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select customerId from customer order by customerId desc limit 1");

            boolean next = resultSet.next();

            if (next) {

                String oldId = resultSet.getString(1);

                String id = oldId.substring(1, 4);

                int intId = Integer.parseInt(id);

                intId = intId + 1;

                if (intId < 10) {
                    txtCustomerID.setText("C00" + intId);
                } else if (intId < 100) {
                    txtCustomerID.setText("C0" + intId);
                } else {
                    txtCustomerID.setText("C" + intId);
                }
            } else {
                txtCustomerID.setText("C001");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearForms();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException {
            if (new CustomerController().deleteCustomer(txtCustomerID.getText())) {
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
                tblCustomerDetails.getItems().clear();
                setCustomerValuesToTable(new CustomerController().selectAllCustomers());
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
        Customer customer = new Customer(
                txtCustomerID.getText(),
                txtCustomerName.getText(),
                txtCustomerAddress.getText(),
                txtCustomerContact.getText()
        );
            if (!customer.getCustomerId().isEmpty()) {
                if (new CustomerController().updateCustomer(customer)) {

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
                    tblCustomerDetails.getItems().clear();
                    setCustomerValuesToTable(new CustomerController().selectAllCustomers());

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
            }else {
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
        Customer customer = new Customer(txtCustomerID.getText(),
                txtCustomerName.getText(),
                txtCustomerAddress.getText(),
                txtCustomerContact.getText()
        );
        if (!customer.getCustomerId().isEmpty() && !customer.getCustomerName().isEmpty() &&
                !customer.getCustomerAddress().isEmpty() && !customer.getCustomerContact().isEmpty()) {

                if (new CustomerController().saveCustomer(customer)) {

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

                    tblCustomerDetails.getItems().clear();
                    setCustomerValuesToTable(new CustomerController().selectAllCustomers());
                } else {
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

    public void tblFarmerDetailsOnMouseClicked(MouseEvent mouseEvent) {
        if (tblCustomerDetails.getSelectionModel().getSelectedItem() != null) {
            CustomerTM customerTM = tblCustomerDetails.getSelectionModel().getSelectedItem();
            txtCustomerID.setText(customerTM.getCustomerId());
            txtCustomerName.setText(customerTM.getCustomerName());
            txtCustomerAddress.setText(customerTM.getCustomerAddress());
            txtCustomerContact.setText(customerTM.getCustomerContact());

        }
    }

    private void clearForms() {
        txtCustomerID.setText("");
        txtCustomerName.setText("");
        txtCustomerAddress.setText("");
        txtCustomerContact.setText("");
    }

    private void storeValidations() {
        btnSaveCustomer.setDisable(true);
        map.put(txtCustomerName, namePattern);
        map.put(txtCustomerAddress, addressPattern);
        map.put(txtCustomerContact, contactPattern);
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map, btnSaveCustomer);

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
