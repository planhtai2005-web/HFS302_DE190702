package com.example.ui.controller;

import com.example.data.pojo.Student;
import com.example.data.service.StudentService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.stereotype.Component;

@Component
public class StudentController {

    @FXML private TableView<Student> tableView;
    @FXML private TextField searchField, idField, nameField, emailField, ageField;
    @FXML private Button btnUpdate, btnDelete;
    @FXML private Label messageLabel, statusLabel;

    private final StudentService studentService;   // INTERFACE, không phải StudentServiceImpl

    // TODO 5.1
    private final ObservableList<Student> data = FXCollections.observableArrayList();

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // TODO 5.2
    @FXML
    public void initialize() {
        tableView.setItems(data);

        tableView.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldValue, selected) -> fillForm(selected));

        btnUpdate.disableProperty()
                .bind(tableView.getSelectionModel().selectedItemProperty().isNull());
        btnDelete.disableProperty()
                .bind(tableView.getSelectionModel().selectedItemProperty().isNull());

        loadData();
    }

    // TODO 5.3
    private void loadData() {
        try {
            data.setAll(studentService.findAll());
            statusLabel.setText("Loaded " + data.size() + " students");
        } catch (Exception e) {
            showError("Cannot load data: " + e.getMessage());
            statusLabel.setText("Database error");
        }
    }

    // TODO 5.4
    @FXML
    private void handleSearch() {
        try {
            data.setAll(studentService.search(searchField.getText()));
            statusLabel.setText("Found " + data.size() + " students");
        } catch (Exception e) {
            showError("Search failed: " + e.getMessage());
            statusLabel.setText("Database error");
        }
    }

    @FXML
    private void handleRefresh() {
        searchField.clear();
        loadData();
    }

    // Các TODO sau (Bài 6)
    @FXML private void handleAdd()     { }
    @FXML private void handleUpdate()  { }
    @FXML private void handleDelete()  { }
    @FXML private void handleClear()   { }

    // TODO 5.5
    private void fillForm(Student s) {
        if (s == null) {
            return;
        }
        idField.setText(String.valueOf(s.getId()));
        nameField.setText(s.getName());
        emailField.setText(s.getEmail());
        ageField.setText(String.valueOf(s.getAge()));
    }

    private void showInfo(String message) {
        messageLabel.setStyle("-fx-text-fill: green;");
        messageLabel.setText(message);
    }

    private void showError(String message) {
        messageLabel.setStyle("-fx-text-fill: red;");
        messageLabel.setText(message);
    }
}