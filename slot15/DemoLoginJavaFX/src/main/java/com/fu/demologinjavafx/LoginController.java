package com.fu.demologinjavafx;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private CheckBox rememberCheckBox;

    @FXML
    private Hyperlink forgotPasswordLink;

    @FXML
    private Hyperlink registerLink;

    @FXML
    private Button loginButton;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showMessage("Vui lòng nhập đầy đủ tài khoản và mật khẩu.");
            return;
        }

        showMessage("Đã nhận thông tin đăng nhập của: " + username);
    }

    @FXML
    private void handleForgotPassword() {
        showMessage("Chức năng quên mật khẩu.");
    }

    @FXML
    private void handleRegister() {
        showMessage("Chức năng đăng ký tài khoản.");
    }

    private void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}