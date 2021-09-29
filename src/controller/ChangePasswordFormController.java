package controller;

import com.jfoenix.controls.JFXButton;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Login;
import util.NotificationMessageUtil;
import util.ValidationUtil;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-28
 **/
public class ChangePasswordFormController {

    public Label lblUserId;
    public Label lblUserNameText;
    public Label lblUserEmail;
    public Label lblUserRole;
    public PasswordField txtNewPassword;
    public PasswordField txtConfirmPassword;
    public JFXButton btnUpdate;
    public Label lblPasswordNotMatch;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    Pattern newPasswordPattern = Pattern.compile("^[A-z0-9]{8,}(@|#|$|%|^|&|!)$");
    Pattern confirmPasswordPattern = Pattern.compile("^[A-z0-9]{8,}$");

    public void initialize(){

        btnUpdate.setDisable(true);

        lblUserId.setText(LoginFormController.userID);
        lblUserNameText.setText(LoginFormController.name);
        lblUserEmail.setText(LoginFormController.eMail);
        lblUserRole.setText(LoginFormController.role);

        lblPasswordNotMatch.setVisible(false);

        storeValidations();

    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException {

        String newPassword = txtNewPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();

        if (newPassword.equals(confirmPassword)){
            PreparedStatement preparedStatement = DBConnection.getInstance().getConnection()
                    .prepareStatement("update login set password = md5(?)");
            preparedStatement.setObject(1, confirmPassword);

            preparedStatement.executeUpdate();

            txtNewPassword.clear();
            txtConfirmPassword.clear();
            lblPasswordNotMatch.setVisible(false);
            btnUpdate.setDisable(true);

            new NotificationMessageUtil().successMessage("Password Change Is Successful !");
        }else {
                txtNewPassword.setStyle("-fx-border-color: red");
                txtConfirmPassword.setStyle("-fx-border-color: red");
                txtNewPassword.requestFocus();
                lblPasswordNotMatch.setVisible(true);
        }
    }

    private void storeValidations() {
        map.put(txtNewPassword, newPasswordPattern);
        map.put(txtConfirmPassword, confirmPasswordPattern);

    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map, btnUpdate);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                new NotificationMessageUtil().successMessage("Successfully Added !");
            }
        }
    }
}
