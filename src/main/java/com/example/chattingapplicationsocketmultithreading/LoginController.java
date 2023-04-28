package com.example.chattingapplicationsocketmultithreading;

import com.example.chattingapplicationsocketmultithreading.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void loginButtonAction(ActionEvent e) throws IOException {
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

        /**
         * if login successfuly go to contacts list
         */
        FXMLLoader fxmlLoader = new FXMLLoader(Client.class.getResource("ContactsLayout.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();


    }

    @FXML
    private void cancelButtonAction() {
        // Get a reference to the current stage (window)
        Stage stage = (Stage) usernameField.getScene().getWindow();

        // Close the stage
        stage.close();
    }

    @FXML
    private void signupButtonAction(ActionEvent event) throws IOException {
        // Load the sign-up page from the FXML file
        FXMLLoader loader = new FXMLLoader(Client.class.getResource("signup.fxml"));
        Parent root = loader.load();

        // Create a new stage and set the sign-up page as its scene
        Stage signupStage = new Stage();
        signupStage.setScene(new Scene(root));

        // Set the modality and owner of the sign-up stage to prevent interaction with the parent stage
        signupStage.initModality(Modality.APPLICATION_MODAL);
        signupStage.initOwner(((Node) event.getSource()).getScene().getWindow());

        // Show the sign-up stage to display the sign-up page
        signupStage.show();
    }
}