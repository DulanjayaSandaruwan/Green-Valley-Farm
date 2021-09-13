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
import model.Garden;
import model.Supplier;
import org.controlsfx.control.Notifications;
import view.tm.GardenTM;
import view.tm.SupplierTM;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-09
 **/
public class ManageGardenDetailsFormController {
    public JFXButton btnSaveGarden;
    public TableView <GardenTM> tblGardenDetails;
    public TableColumn colGardenId;
    public TableColumn colGardenType;
    public TableColumn colGardenLocation;
    public TableColumn colExtendOfLand;
    public TableColumn colDescription;
    public TextField txtGardenId;
    public TextField txtGardenType;
    public TextField txtGardenLocation;
    public TextField txtExtendOfLand;
    public TextField txtDescription;

    ObservableList<GardenTM> observableList = FXCollections.observableArrayList();

    public void initialize() {

        colGardenId.setCellValueFactory(new PropertyValueFactory<>("gardenId"));
        colGardenType.setCellValueFactory(new PropertyValueFactory<>("gardenType"));
        colGardenLocation.setCellValueFactory(new PropertyValueFactory<>("gardenLocation"));
        colExtendOfLand.setCellValueFactory(new PropertyValueFactory<>("extendOfLand"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        try {
            setGardenValuesToTable(new GardenController().selectAllGardens());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void txtGetGardenId(ActionEvent actionEvent) throws SQLException {
        String gardenId = txtGardenId.getText();

        Garden garden = new GardenController().searchGarden(gardenId);
        if (garden == null) {
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
            setData(garden);
        }
    }

    void setData(Garden garden) {
        txtGardenId.setText(garden.getGardenId());
        txtGardenType.setText(garden.getGardenType());
        txtGardenLocation.setText(garden.getGardenLocation());
        txtExtendOfLand.setText(garden.getExtendOfLand());
        txtDescription.setText(garden.getDescription());
    }

    private void setGardenValuesToTable(ArrayList<Garden> gardens) {
        gardens.forEach(e -> {
                    observableList.add(new GardenTM(e.getGardenId(), e.getGardenType(), e.getGardenLocation(),
                            e.getExtendOfLand(), e.getDescription()));
                }
        );
        tblGardenDetails.setItems(observableList);
    }

    public void btnGardenIdOnAction(ActionEvent actionEvent) {
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select gardenId from garden order by gardenId desc limit 1");

            boolean next = resultSet.next();

            if (next) {

                String oldId = resultSet.getString(1);

                String id = oldId.substring(1, 4);

                int intId = Integer.parseInt(id);

                intId = intId + 1;

                if (intId < 10) {
                    txtGardenId.setText("G00" + intId);
                } else if (intId < 100) {
                    txtGardenId.setText("G0" + intId);
                } else {
                    txtGardenId.setText("G" + intId);
                }
            } else {
                txtGardenId.setText("G001");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException {
        Garden garden = new Garden(txtGardenId.getText(),
                txtGardenType.getText(),
                txtGardenLocation.getText(),
                txtExtendOfLand.getText(),
                txtDescription.getText()
        );

        if (!garden.getGardenId().isEmpty() && !garden.getGardenType().isEmpty() && !garden.getGardenLocation().isEmpty() && !garden.getExtendOfLand().isEmpty() && !garden.getDescription().isEmpty()) {
            if (new GardenController().saveGarden(garden)) {

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

                tblGardenDetails.getItems().clear();
                setGardenValuesToTable(new GardenController().selectAllGardens());
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
        Garden garden = new Garden(
                txtGardenId.getText(),
                txtGardenType.getText(),
                txtGardenLocation.getText(),
                txtExtendOfLand.getText(),
                txtDescription.getText()
        );
        if (!garden.getGardenId().isEmpty()) {
            if (new GardenController().updateGarden(garden)) {
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
                    tblGardenDetails.getItems().clear();
                    setGardenValuesToTable(new GardenController().selectAllGardens());
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
        if (new GardenController().deleteGarden(txtGardenId.getText())) {
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
            tblGardenDetails.getItems().clear();
            setGardenValuesToTable(new GardenController().selectAllGardens());
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

    public void tblGardenDetailsOnMouseClicked(MouseEvent mouseEvent) {
        if (tblGardenDetails.getSelectionModel().getSelectedItem() != null) {
            GardenTM gardenTM = tblGardenDetails.getSelectionModel().getSelectedItem();
            txtGardenId.setText(gardenTM.getGardenId());
            txtGardenType.setText(gardenTM.getGardenType());
            txtGardenLocation.setText(gardenTM.getGardenLocation());
            txtExtendOfLand.setText(gardenTM.getExtendOfLand());
            txtDescription.setText(gardenTM.getDescription());

        }
    }

    private void clearForms() {
        txtGardenId.setText("");
        txtGardenType.setText("");
        txtGardenLocation.setText("");
        txtExtendOfLand.setText("");
        txtDescription.setText("");
    }


    public void textFields_Key_Realeased(KeyEvent keyEvent) {

    }
}
