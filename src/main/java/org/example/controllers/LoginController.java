package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void loginButtonAction() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        // Check if the username and password fields are empty
        if (username.isEmpty() || password.isEmpty()) {
            // Display an error message to the user
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter both a username and password.");
            alert.showAndWait();
            return;
        }

        // Perform login validation and other operations here
        // For example, you can check if the username and password are valid
        // by comparing them to a database or hardcoded values

        // If the login is successful, you can open a new window or change the scene
        // If the login fails, you can display an error message to the user
    }

    @FXML
    private void cancelButtonAction() {
        // Get a reference to the current stage (window)
        Stage stage = (Stage) usernameField.getScene().getWindow();

        // Close the stage
        stage.close();
    }
}