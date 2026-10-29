
package com.fu.ch08.b3;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

public class RegisterApp extends Application {

    private static final String STRONG_PASSWORD =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w\\s]).{8,}$";

    private final TextField tfFullName = new TextField();
    private final TextField tfEmail = new TextField();
    private final TextField tfUsername = new TextField();
    private final PasswordField pfPassword = new PasswordField();
    private final PasswordField pfConfirm = new PasswordField();

    private final ToggleGroup tgGender = new ToggleGroup();
    private final RadioButton rbMale = new RadioButton("Nam");
    private final RadioButton rbFemale = new RadioButton("Nữ");
    private final RadioButton rbOther = new RadioButton("Khác");

    private final DatePicker dpDob = new DatePicker();
    private final ComboBox<String> cbMajor = new ComboBox<>();

    private final CheckBox chkJava = new CheckBox("Java");
    private final CheckBox chkJavaFX = new CheckBox("JavaFX");
    private final CheckBox chkDatabase = new CheckBox("Database");

    private final Label errFullName = createErrorLabel();
    private final Label errEmail = createErrorLabel();
    private final Label errUsername = createErrorLabel();
    private final Label errPassword = createErrorLabel();
    private final Label errConfirm = createErrorLabel();
    private final Label errGender = createErrorLabel();
    private final Label errDob = createErrorLabel();
    private final Label errMajor = createErrorLabel();
    private final Label errHobbies = createErrorLabel();

    private final Label lblStrength = new Label("Độ mạnh mật khẩu");
    private final ProgressBar pbStrength = new ProgressBar(0);

    private Stage stage;
    private ScrollPane registerView;

    @Override
    public void start(Stage stage) {
        this.stage = stage;

        setupControls();
        registerView = buildRegisterView();
        registerEvents();

        stage.setTitle("B3 - Registration & Validation");
        stage.setMinWidth(650);
        stage.setMinHeight(650);
        stage.setScene(new Scene(registerView, 760, 780));
        stage.show();
    }

    private void setupControls() {
        tfFullName.setPromptText("Nhập họ và tên");
        tfEmail.setPromptText("example@email.com");
        tfUsername.setPromptText("Tên đăng nhập");
        pfPassword.setPromptText("Ít nhất 8 ký tự");
        pfConfirm.setPromptText("Nhập lại mật khẩu");

        rbMale.setToggleGroup(tgGender);
        rbFemale.setToggleGroup(tgGender);
        rbOther.setToggleGroup(tgGender);

        cbMajor.getItems().addAll(
                "Kỹ thuật phần mềm",
                "Trí tuệ nhân tạo",
                "An toàn thông tin",
                "Thiết kế đồ họa"
        );
        cbMajor.setPromptText("Chọn ngành học");
        cbMajor.setMaxWidth(Double.MAX_VALUE);

        dpDob.setPromptText("Ngày sinh");
        dpDob.setMaxWidth(Double.MAX_VALUE);

        pbStrength.setMaxWidth(Double.MAX_VALUE);
        pbStrength.setPrefHeight(10);
    }

    private ScrollPane buildRegisterView() {
        Label title = new Label("ĐĂNG KÝ TÀI KHOẢN");
        title.setStyle(
                "-fx-font-size: 24px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #263746;"
        );

        GridPane grid = new GridPane();
        grid.setHgap(16);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        ColumnConstraints left = new ColumnConstraints();
        left.setMinWidth(135);

        ColumnConstraints right = new ColumnConstraints();
        right.setHgrow(Priority.ALWAYS);

        grid.getColumnConstraints().addAll(left, right);

        int row = 0;

        addRow(grid, row++, "Họ và tên", tfFullName, errFullName);
        addRow(grid, row++, "Email", tfEmail, errEmail);
        addRow(grid, row++, "Username", tfUsername, errUsername);

        VBox passwordBox = new VBox(
                5, pfPassword, pbStrength, lblStrength, errPassword
        );
        addRow(grid, row++, "Mật khẩu", passwordBox);

        addRow(grid, row++, "Xác nhận mật khẩu", pfConfirm, errConfirm);

        HBox genderBox = new HBox(16, rbMale, rbFemale, rbOther);
        genderBox.setAlignment(Pos.CENTER_LEFT);
        addRow(grid, row++, "Giới tính", genderBox, errGender);

        addRow(grid, row++, "Ngày sinh", dpDob, errDob);
        addRow(grid, row++, "Ngành học", cbMajor, errMajor);

        HBox hobbies = new HBox(14, chkJava, chkJavaFX, chkDatabase);
        hobbies.setAlignment(Pos.CENTER_LEFT);
        addRow(grid, row++, "Sở thích", hobbies, errHobbies);

        Button btnRegister = new Button("Đăng ký");
        Button btnReset = new Button("Làm lại");
        Button btnToLogin = new Button("Đã có tài khoản? Đăng nhập");

        btnRegister.setDefaultButton(true);
        btnRegister.setStyle(
                "-fx-background-color: #1976d2;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;"
        );

        btnRegister.setOnAction(e -> handleRegister());
        btnReset.setOnAction(e -> resetForm());
        btnToLogin.setOnAction(e -> showLoginView(""));

        HBox buttons = new HBox(10, btnRegister, btnReset);
        buttons.setAlignment(Pos.CENTER);

        VBox content = new VBox(
                18,
                title,
                new Label("Vui lòng điền đầy đủ thông tin"),
                grid,
                buttons,
                btnToLogin
        );

        content.setAlignment(Pos.TOP_CENTER);
        content.setPadding(new Insets(24));
        content.setMaxWidth(680);
        content.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 12px;"
        );

        StackPane wrapper = new StackPane(content);
        wrapper.setPadding(new Insets(20));
        wrapper.setStyle("-fx-background-color: #edf2f7;");

        ScrollPane scroll = new ScrollPane(wrapper);
        scroll.setFitToWidth(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        return scroll;
    }

    private void addRow(
            GridPane grid,
            int row,
            String title,
            Node control,
            Label error
    ) {
        VBox field = new VBox(4, control, error);
        addRow(grid, row, title, field);
    }

    private void addRow(
            GridPane grid,
            int row,
            String title,
            Node control
    ) {
        Label label = new Label(title);
        grid.add(label, 0, row);
        grid.add(control, 1, row);
        GridPane.setValignment(label, VPos.TOP);
    }

    private void addRow(
            GridPane grid,
            int row,
            String title,
            VBox field
    ) {
        Label label = new Label(title);
        grid.add(label, 0, row);
        grid.add(field, 1, row);
        GridPane.setValignment(label, VPos.TOP);
    }

    private Label createErrorLabel() {
        Label label = new Label();
        label.setStyle(
                "-fx-text-fill: #d32f2f;" +
                        "-fx-font-size: 11px;"
        );
        label.setWrapText(true);
        return label;
    }

    private void registerEvents() {
        tfFullName.focusedProperty().addListener(
                (obs, oldValue, focused) -> {
                    if (!focused) {
                        validateFullName();
                    }
                }
        );

        tfEmail.focusedProperty().addListener(
                (obs, oldValue, focused) -> {
                    if (!focused) {
                        validateEmail();
                    }
                }
        );

        tfUsername.focusedProperty().addListener(
                (obs, oldValue, focused) -> {
                    if (!focused) {
                        validateUsername();
                    }
                }
        );

        pfPassword.textProperty().addListener(
                (obs, oldValue, newValue) -> {
                    updatePasswordStrength(newValue);
                    if (!newValue.isEmpty()) {
                        validatePassword();
                    }
                    if (!pfConfirm.getText().isEmpty()) {
                        validateConfirmPassword();
                    }
                }
        );

        pfConfirm.textProperty().addListener(
                (obs, oldValue, newValue) -> {
                    if (!newValue.isEmpty()) {
                        validateConfirmPassword();
                    } else {
                        errConfirm.setText("");
                    }
                }
        );

        tgGender.selectedToggleProperty().addListener(
                (obs, oldValue, newValue) -> {
                    if (newValue != null) {
                        errGender.setText("");
                    }
                }
        );

        dpDob.valueProperty().addListener(
                (obs, oldValue, newValue) -> {
                    if (newValue != null) {
                        validateDob();
                    }
                }
        );

        cbMajor.valueProperty().addListener(
                (obs, oldValue, newValue) -> {
                    if (newValue != null) {
                        errMajor.setText("");
                    }
                }
        );

        Stream.of(chkJava, chkJavaFX, chkDatabase).forEach(checkBox ->
                checkBox.selectedProperty().addListener(
                        (obs, oldValue, newValue) -> {
                            if (hasHobby()) {
                                errHobbies.setText("");
                            }
                        }
                )
        );
    }

    private boolean validateFullName() {
        String name = tfFullName.getText().trim();
        boolean valid = name.length() >= 3;

        errFullName.setText(
                valid ? "" : "Họ tên phải có ít nhất 3 ký tự."
        );
        return valid;
    }

    private boolean validateEmail() {
        String email = tfEmail.getText().trim();
        boolean valid = email.matches(
                "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
        );

        errEmail.setText(
                valid ? "" : "Email không đúng định dạng."
        );
        return valid;
    }

    private boolean validateUsername() {
        String username = tfUsername.getText().trim();

        if (username.length() < 4) {
            errUsername.setText(
                    "Username phải có ít nhất 4 ký tự."
            );
            return false;
        }

        if (!username.matches("[A-Za-z0-9_]+")) {
            errUsername.setText(
                    "Chỉ dùng chữ cái, chữ số và dấu gạch dưới."
            );
            return false;
        }

        if (UserStore.exists(username)) {
            errUsername.setText("Username này đã tồn tại.");
            return false;
        }

        errUsername.setText("");
        return true;
    }

    private boolean validatePassword() {
        String password = pfPassword.getText();
        boolean valid = password.matches(STRONG_PASSWORD);

        errPassword.setText(
                valid ? "" :
                        "Cần ít nhất 8 ký tự, chữ hoa, chữ thường, số và ký tự đặc biệt."
        );
        return valid;
    }

    private boolean validateConfirmPassword() {
        boolean valid = !pfConfirm.getText().isEmpty()
                && pfConfirm.getText().equals(pfPassword.getText());

        errConfirm.setText(
                valid ? "" : "Mật khẩu xác nhận không khớp."
        );
        return valid;
    }

    private boolean validateGender() {
        boolean valid = tgGender.getSelectedToggle() != null;

        errGender.setText(
                valid ? "" : "Vui lòng chọn giới tính."
        );
        return valid;
    }

    private boolean validateDob() {
        LocalDate dob = dpDob.getValue();
        boolean valid = dob != null
                && !dob.isAfter(LocalDate.now());

        if (valid) {
            valid = Period.between(dob, LocalDate.now()).getYears() >= 16;
        }

        errDob.setText(
                valid ? "" :
                        "Ngày sinh không hợp lệ hoặc người dùng chưa đủ 16 tuổi."
        );
        return valid;
    }

    private boolean validateMajor() {
        boolean valid = cbMajor.getValue() != null;

        errMajor.setText(
                valid ? "" : "Vui lòng chọn ngành học."
        );
        return valid;
    }

    private boolean hasHobby() {
        return chkJava.isSelected()
                || chkJavaFX.isSelected()
                || chkDatabase.isSelected();
    }

    private boolean validateHobbies() {
        boolean valid = hasHobby();

        errHobbies.setText(
                valid ? "" : "Chọn ít nhất một sở thích."
        );
        return valid;
    }

    private void updatePasswordStrength(String password) {
        int score = 0;

        if (password.length() >= 8) score++;
        if (password.matches(".*[a-z].*")) score++;
        if (password.matches(".*[A-Z].*")) score++;
        if (password.matches(".*\\d.*")) score++;
        if (password.matches(".*[^\\w\\s].*")) score++;

        String[] labels = {
                "Rất yếu", "Yếu", "Trung bình",
                "Khá", "Mạnh", "Rất mạnh"
        };

        String[] colors = {
                "#d32f2f", "#d32f2f", "#f9a825",
                "#1976d2", "#388e3c", "#1b5e20"
        };

        pbStrength.setProgress(score / 5.0);
        lblStrength.setText(
                "Độ mạnh mật khẩu: " + labels[score]
        );
        lblStrength.setStyle(
                "-fx-text-fill: " + colors[score] + ";"
        );
    }

    private boolean validateAll() {
        // Dùng & để mọi validator đều chạy và hiện lỗi.
        return validateFullName()
                & validateEmail()
                & validateUsername()
                & validatePassword()
                & validateConfirmPassword()
                & validateGender()
                & validateDob()
                & validateMajor()
                & validateHobbies();
    }

    private void handleRegister() {
        if (!validateAll()) {
            focusFirstInvalidField();
            return;
        }

        RadioButton selectedGender =
                (RadioButton) tgGender.getSelectedToggle();

        List<String> hobbies = Stream.of(
                        chkJava, chkJavaFX, chkDatabase
                )
                .filter(CheckBox::isSelected)
                .map(CheckBox::getText)
                .toList();

        User user = new User(
                tfFullName.getText().trim(),
                tfEmail.getText().trim(),
                tfUsername.getText().trim().toLowerCase(Locale.ROOT),
                pfPassword.getText(),
                selectedGender.getText(),
                dpDob.getValue(),
                cbMajor.getValue(),
                hobbies
        );

        try {
            UserStore.add(user);
        } catch (IllegalArgumentException ex) {
            errUsername.setText(ex.getMessage());
            tfUsername.requestFocus();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Đăng ký thành công");
        alert.setHeaderText("Chào mừng " + user.fullName());
        alert.setContentText(
                "Tài khoản đã được tạo. Hãy đăng nhập để tiếp tục."
        );
        alert.showAndWait();

        showLoginView(user.username());
    }

    private void focusFirstInvalidField() {
        if (!validateFullName()) {
            tfFullName.requestFocus();
        } else if (!validateEmail()) {
            tfEmail.requestFocus();
        } else if (!validateUsername()) {
            tfUsername.requestFocus();
        } else if (!validatePassword()) {
            pfPassword.requestFocus();
        } else if (!validateConfirmPassword()) {
            pfConfirm.requestFocus();
        } else if (!validateGender()) {
            rbMale.requestFocus();
        } else if (!validateDob()) {
            dpDob.requestFocus();
        } else if (!validateMajor()) {
            cbMajor.requestFocus();
        } else if (!validateHobbies()) {
            chkJava.requestFocus();
        }
    }

    private void resetForm() {
        tfFullName.clear();
        tfEmail.clear();
        tfUsername.clear();
        pfPassword.clear();
        pfConfirm.clear();

        tgGender.selectToggle(null);
        dpDob.setValue(null);
        cbMajor.setValue(null);

        chkJava.setSelected(false);
        chkJavaFX.setSelected(false);
        chkDatabase.setSelected(false);

        Stream.of(
                errFullName, errEmail, errUsername,
                errPassword, errConfirm, errGender,
                errDob, errMajor, errHobbies
        ).forEach(label -> label.setText(""));

        pbStrength.setProgress(0);
        lblStrength.setText("Độ mạnh mật khẩu");

        tfFullName.requestFocus();
    }

    private void showLoginView(String usernameToFill) {
        Label title = new Label("ĐĂNG NHẬP");
        title.setStyle(
                "-fx-font-size: 24px;" +
                        "-fx-font-weight: bold;"
        );

        TextField tfLoginUsername = new TextField(usernameToFill);
        tfLoginUsername.setPromptText("Username");

        PasswordField pfLoginPassword = new PasswordField();
        pfLoginPassword.setPromptText("Password");

        Label lblMessage = createErrorLabel();

        Button btnLogin = new Button("Đăng nhập");
        Button btnBack = new Button("Quay lại đăng ký");

        btnLogin.setDefaultButton(true);

        btnLogin.setOnAction(e -> {
            var result = UserStore.login(
                    tfLoginUsername.getText(),
                    pfLoginPassword.getText()
            );

            if (result.isPresent()) {
                User user = result.get();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Đăng nhập thành công");
                alert.setHeaderText("Xin chào, " + user.fullName());
                alert.setContentText("Ngành học: " + user.major());
                alert.showAndWait();
            } else {
                lblMessage.setText(
                        "Username hoặc password không đúng."
                );
                pfLoginPassword.clear();
                pfLoginPassword.requestFocus();
            }
        });

        btnBack.setOnAction(e ->
                stage.getScene().setRoot(registerView)
        );

        VBox box = new VBox(
                14,
                title,
                new Label("Username"),
                tfLoginUsername,
                new Label("Password"),
                pfLoginPassword,
                lblMessage,
                new HBox(10, btnLogin, btnBack)
        );

        box.setPadding(new Insets(30));
        box.setAlignment(Pos.CENTER);
        box.setMaxWidth(420);
        box.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 12px;"
        );

        StackPane root = new StackPane(box);
        root.setPadding(new Insets(25));
        root.setStyle("-fx-background-color: #edf2f7;");

        stage.setScene(new Scene(root, 600, 500));
        stage.setTitle("B3 - Đăng nhập");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
