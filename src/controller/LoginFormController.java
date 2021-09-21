package controller;

import com.jfoenix.controls.JFXButton;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-02
 **/
public class LoginFormController {

    public static String name;
    public static String userID;
    public static String role;
    public static String eMail;
    public TextField txtEnterUserName;
    public PasswordField txtEnterPassword;
    public JFXButton btnLogin;
    public AnchorPane root1;
    public Label lblDate;
    public Label lblTime;

    public void initialize() {


    }

    public void btnLoginOnAction(ActionEvent actionEvent) {
        login();
    }

    public void login() {
        String userName = txtEnterUserName.getText();
        String password = txtEnterPassword.getText();

        Connection connection = DBConnection.getInstance().getConnection();

        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement("select * from login where userName = ? and password = md5(?) ");

            preparedStatement.setObject(1, userName);
            preparedStatement.setObject(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {

                    userID = resultSet.getString(1);
                    name = resultSet.getString(2);
                    eMail = resultSet.getString(4);
                    role = resultSet.getString(5);

                    Parent parent = FXMLLoader.load(this.getClass().getResource(role.equals("Manager")
                            ? "../view/ManagerMainForm.fxml" : "../view/ReceptionMainForm.fxml"));
                    Scene scene = new Scene(parent);
                    Stage primaryStage = (Stage) this.root1.getScene().getWindow();
                    primaryStage.setScene(scene);
                    primaryStage.setTitle("Main Form");
                    primaryStage.centerOnScreen();

                } else {

                    Image image = new Image("/assests/images/fail.png");
                    Notifications notifications = Notifications.create();
                    notifications.graphic(new ImageView(image));
                    notifications.text("Something Went Wrong , Wrong User Name Or Password , Try Again !");
                    notifications.title("Failed Message");
                    notifications.hideAfter(Duration.seconds(10));
                    notifications.position(Pos.TOP_CENTER);
                    notifications.darkStyle();
                    notifications.show();

                    txtEnterUserName.clear();
                    txtEnterPassword.clear();
                    txtEnterUserName.requestFocus();
                }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
