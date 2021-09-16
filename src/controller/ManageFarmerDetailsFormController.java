package controller;

import com.jfoenix.controls.JFXButton;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
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
import model.Farmer;
import org.controlsfx.control.Notifications;
import util.ValidationUtil;
import view.tm.FarmerTM;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-09
 **/
public class ManageFarmerDetailsFormController {
    public ComboBox cmbGardenIds;
    public TextField txtFarmerId;
    public TableColumn colFarmerId;
    public TableColumn colFarmerName;
    public TableColumn colFarmerAddress;
    public TableColumn colFarmerContact;
    public TableColumn colGardenId;
    public TableView<FarmerTM> tblFarmerDetails;
    public JFXButton btnSaveFarmer;
    public TextField txtFarmerName;
    public TextField txtFarmerAddress;
    public TextField txtFarmerContact;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    Pattern namePattern = Pattern.compile("^[A-z ]{5,20}$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9/ ]{6,30}$");
    Pattern contactPattern = Pattern.compile("^(070|071|075|076|077|078)[-]?[0-9]{7}$");

    ObservableList<FarmerTM> observableList = FXCollections.observableArrayList();

    public void initialize() {

        colFarmerId.setCellValueFactory(new PropertyValueFactory<>("farmerId"));
        colFarmerName.setCellValueFactory(new PropertyValueFactory<>("farmerName"));
        colFarmerAddress.setCellValueFactory(new PropertyValueFactory<>("farmerAddress"));
        colFarmerContact.setCellValueFactory(new PropertyValueFactory<>("farmerContact"));
        colGardenId.setCellValueFactory(new PropertyValueFactory<>("gardenId"));

        try {
            setFarmerValuesToTable(new FarmerController().selectAllFarmers());
            loadGardenIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        storeValidations();

    }

    public void btnFarmerIdOnAction(ActionEvent actionEvent) {
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select farmerId from farmer order by farmerId desc limit 1");

            boolean next = resultSet.next();

            if (next) {

                String oldId = resultSet.getString(1);

                String id = oldId.substring(1, 4);

                int intId = Integer.parseInt(id);

                intId = intId + 1;

                if (intId < 10) {
                    txtFarmerId.setText("F00" + intId);
                } else if (intId < 100) {
                    txtFarmerId.setText("F0" + intId);
                } else {
                    txtFarmerId.setText("F" + intId);
                }
            } else {
                txtFarmerId.setText("F001");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void txtGetFarmerId(ActionEvent actionEvent) throws SQLException {
        String farmerId = txtFarmerId.getText();

        Farmer farmer = new FarmerController().searchFarmer(farmerId);
        if (farmer == null) {
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
            setData(farmer);
        }
    }

    void setData(Farmer farmer) {
        txtFarmerId.setText(farmer.getFarmerId());
        txtFarmerName.setText(farmer.getFarmerName());
        txtFarmerAddress.setText(farmer.getFarmerAddress());
        txtFarmerContact.setText(farmer.getFarmerContact());

    }

    private void setFarmerValuesToTable(ArrayList<Farmer> farmers) {
        farmers.forEach(e -> {
                    observableList.add(new FarmerTM(
                            e.getFarmerId(),
                            e.getFarmerName(),
                            e.getFarmerAddress(),
                            e.getFarmerContact(),
                            e.getGardenId()));
                }
        );
        tblFarmerDetails.setItems(observableList);
    }

    private void loadGardenIds() throws SQLException {
        cmbGardenIds.getItems().addAll(new GardenController().getGardenID());
    }

    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException {
        Farmer farmer = new Farmer(txtFarmerId.getText(),
                txtFarmerName.getText(),
                txtFarmerAddress.getText(),
                txtFarmerContact.getText(),
                (String) cmbGardenIds.getSelectionModel().getSelectedItem()
        );
        if (!farmer.getFarmerId().isEmpty() && !farmer.getFarmerName().isEmpty() &&
                !farmer.getFarmerAddress().isEmpty() && !farmer.getFarmerContact().isEmpty()) {
            if (new FarmerController().saveFarmer(farmer)) {

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

                tblFarmerDetails.getItems().clear();
                setFarmerValuesToTable(new FarmerController().selectAllFarmers());
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
        Farmer farmer = new Farmer(
                txtFarmerId.getText(),
                txtFarmerName.getText(),
                txtFarmerAddress.getText(),
                txtFarmerContact.getText(),
                (String) cmbGardenIds.getSelectionModel().getSelectedItem()

        );
        if (!farmer.getFarmerId().isEmpty()) {
            if (new FarmerController().updateFarmer(farmer)) {
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
                    tblFarmerDetails.getItems().clear();
                    setFarmerValuesToTable(new FarmerController().selectAllFarmers());
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
        if (new FarmerController().deleteFarmer(txtFarmerId.getText())) {
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
            tblFarmerDetails.getItems().clear();
            setFarmerValuesToTable(new FarmerController().selectAllFarmers());

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

    public void tblFarmerDetailsOnMouseClicked(MouseEvent mouseEvent) {
        if (tblFarmerDetails.getSelectionModel().getSelectedItem() != null) {
            FarmerTM farmerTM = tblFarmerDetails.getSelectionModel().getSelectedItem();
            txtFarmerId.setText(farmerTM.getFarmerId());
            txtFarmerName.setText(farmerTM.getFarmerName());
            txtFarmerAddress.setText(farmerTM.getFarmerAddress());
            txtFarmerContact.setText(farmerTM.getFarmerContact());
            cmbGardenIds.setValue(farmerTM.getGardenId());
        }
    }

    private void clearForms() {
        txtFarmerId.setText("");
        txtFarmerName.setText("");
        txtFarmerAddress.setText("");
        txtFarmerContact.setText("");
        cmbGardenIds.getSelectionModel().clearSelection();
    }

    private void storeValidations() {
        btnSaveFarmer.setDisable(true);
        map.put(txtFarmerName, namePattern);
        map.put(txtFarmerAddress, addressPattern);
        map.put(txtFarmerContact, contactPattern);

    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map, btnSaveFarmer);

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
