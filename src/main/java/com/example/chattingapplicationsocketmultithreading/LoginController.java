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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;


public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private BufferedReader reader;
    private OutputStreamWriter writer;
    private Client client;

    public void setMainApp(Client client) {
        this.client = client;
    }

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



        try {
            Socket socket = new Socket("localhost", 9091);
            client.showContactsPage(socket, username);
        } catch (IOException exception) {
            exception.printStackTrace();
        }


//        FXMLLoader fxmlLoader = new FXMLLoader(Client.class.getResource("ContactsLayout.fxml"));
//        Scene scene = new Scene(fxmlLoader.load());
//
//        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
//        stage.setScene(scene);
//        stage.show();


    }

    @FXML
    private void cancelButtonAction() {
        // Get a reference to the current stage (window)
        Stage stage = (Stage) usernameField.getScene().getWindow();

        // Close the stage
        stage.close();
    }

    @FXML
    private void signupButtonAction(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Client.class.getResource("signup.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}