package controller;

import com.jfoenix.controls.JFXButton;
import db.DBConnection;
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
import model.User;
import org.controlsfx.control.Notifications;
import util.ValidationUtil;
import view.tm.UserTM;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-05
 **/

public class AddNewUserFormController {

    public TextField txtUserName;
    public TextField txtEmail;
    public PasswordField txtNewPassword;
    public PasswordField txtConfirmPassword;
    public TextField txtRole;
    public JFXButton btnRegister;
    public JFXButton btnReception;
    public Label lblID;
    public JFXButton btnManager;
    public Label lblPasswordNotMatch;
    public TableView<UserTM> tblUserDetails;
    public TableColumn colUserID;
    public TableColumn colUserName;
    public TableColumn colUserEmail;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    Pattern namePattern = Pattern.compile("^[A-z ]{5,20}$");
    Pattern eMailPattern = Pattern.compile("^[A-z0-9]{3,40}[@][a-z]{2,}[.](com|lk|uk|[a-z]{2,})$");
    Pattern newPasswordPattern = Pattern.compile("^[A-z0-9]{8,}(@|#|$|%|^|&|!)$");
    Pattern confirmPasswordPattern = Pattern.compile("^[A-z0-9]{8,}$");
    Pattern rolePattern = Pattern.compile("^(Manager|Reception)$");

    ObservableList<UserTM> obList = FXCollections.observableArrayList();

    public void initialize() {
        lblPasswordNotMatch.setVisible(false);
        txtUserName.setDisable(true);
        txtEmail.setDisable(true);
        txtNewPassword.setDisable(true);
        txtConfirmPassword.setDisable(true);
        txtRole.setDisable(true);
        btnRegister.setDisable(true);

        try {

            colUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));
            colUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
            colUserEmail.setCellValueFactory(new PropertyValueFactory<>("userEmail"));

            loadAllUsers();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        storeValidations();

    }

    private void loadAllUsers() throws SQLException {
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().
                prepareStatement("select * from login");
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<User> users = new ArrayList<>();

        while (resultSet.next()) {

            users.add(new User(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(4)
            ));
        }
        setUsersToTable(users);
    }

    private void setUsersToTable(ArrayList<User> users) {
        users.forEach(e -> {
            obList.add(
                    new UserTM(e.getUserID(), e.getUserName(), e.getUserEmail()));
        });
        tblUserDetails.setItems(obList);
    }

    public void btnRegisterOnAction(ActionEvent actionEvent) {
        String newPassword = txtNewPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();
        String id = lblID.getText();
        String userName = txtUserName.getText();
        String eMail = txtEmail.getText();
        String role = txtRole.getText();

        if (newPassword.equals(confirmPassword)) {
            txtNewPassword.setStyle("-fx-border-color: transparent");
            txtConfirmPassword.setStyle("-fx-border-color: transparent");

            Connection connection = DBConnection.getInstance().getConnection();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("insert into login values(?,?,md5(?),?,?)");

                preparedStatement.setObject(1, id);
                preparedStatement.setObject(2, userName);
                preparedStatement.setObject(3, confirmPassword);
                preparedStatement.setObject(4, eMail);
                preparedStatement.setObject(5, role);

                if (preparedStatement.executeUpdate() != 0) {

                    successNotification();

                    clearForms();

                    User user = new User(
                            lblID.getText(),
                            txtUserName.getText(),
                            txtEmail.getText()
                    );

                    obList.add(new UserTM(user.getUserID(), user.getUserName(), user.getUserEmail()));
                    clearForms();
                    loadAllUsers();

                } else {
                    failedNotification();
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            txtNewPassword.setStyle("-fx-border-color: red");
            txtConfirmPassword.setStyle("-fx-border-color: red");
            txtNewPassword.requestFocus();
            lblPasswordNotMatch.setVisible(true);
        }
    }

    private void clearForms() {
        lblID.setText("");
        txtUserName.setText("");
        txtEmail.setText("");
        txtNewPassword.setText("");
        txtConfirmPassword.setText("");
        txtRole.setText("");
        tblUserDetails.getItems().clear();
        lblPasswordNotMatch.setVisible(false);

    }

    public void btnAddNewManagerOnAction(ActionEvent actionEvent) {
        autoGenerateManagerID("M");

        txtUserName.setDisable(false);
        txtEmail.setDisable(false);
        txtNewPassword.setDisable(false);
        txtConfirmPassword.setDisable(false);
        txtRole.setDisable(false);

        txtUserName.requestFocus();
    }

    public void btnAddNewReceptionOnAction(ActionEvent actionEvent) {
        autoGenerateManagerID("R");

        txtUserName.setDisable(false);
        txtEmail.setDisable(false);
        txtNewPassword.setDisable(false);
        txtConfirmPassword.setDisable(false);
        txtRole.setDisable(false);

        txtUserName.requestFocus();
    }

    public void autoGenerateManagerID(String type) {
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select userId from login  WHERE userId LIKE ".concat("'")
                    .concat(type).concat("%").concat("'").concat(" order by userId ").concat("desc limit 1"));

            if (resultSet.next()) {

                String oldId = resultSet.getString(1);

                String id = oldId.substring(1, 4);
                type = oldId.substring(0, 1);

                int intId = Integer.parseInt(id);

                ++intId;

                if (intId < 10) {
                    lblID.setText(type + "00" + intId);
                } else if (intId < 100) {
                    lblID.setText(type + "0" + intId);
                } else {
                    lblID.setText(type + +intId);
                }
            } else {
                lblID.setText(type + "001");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void storeValidations() {
        map.put(txtUserName, namePattern);
        map.put(txtEmail, eMailPattern);
        map.put(txtNewPassword, newPasswordPattern);
        map.put(txtConfirmPassword, confirmPasswordPattern);
        map.put(txtRole, rolePattern);
    }

    public void textFields_Key_Realeased(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map, btnRegister);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                successNotification();
            }
        }
    }

    public void successNotification(){
        Image image = new Image("/assests/images/pass.png");
        Notifications notifications = Notifications.create();
        notifications.graphic(new ImageView(image));
        notifications.text("Successfully Added !");
        notifications.title("Success Message");
        notifications.hideAfter(Duration.seconds(5));
        notifications.position(Pos.TOP_CENTER);
        notifications.darkStyle();
        notifications.show();
    }

    public void failedNotification(){
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
