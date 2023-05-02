package com.example.chattingapplicationsocketmultithreading;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;


public class SignupController implements Initializable {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button signupButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button loginButton;

    private Client client;

    public void setclientApp(Client client) {
        this.client = client;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        signupButton.setOnAction(event -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            String confirmPassword = confirmPasswordField.getText().trim();
            String message = "Register|";

            // Validate input
            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                signUPAlert("Warning","Please fill in all fields.");
            } else if (!password.equals(confirmPassword)) {
                signUPAlert("Warning","Passwords do not match.");
            } else {
                try {
                    Socket socket = new Socket("localhost", 9091);
                    var br = new BufferedReader( new InputStreamReader( socket.getInputStream()) ) ;
                    var pw = new PrintWriter(socket.getOutputStream(),true);

                    message+=username+'|'+password;
                    pw.println(message);

                    String response = new String(br.readLine());

                    System.out.println(message);
                    System.out.println(response);

                    if(response.equalsIgnoreCase("done")) {
                        client.showContactsPage(socket, username);
                        signUPAlert("Success",response);
                    } else {
                        signUPAlert("Warning",response);
                    }

                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        });

        cancelButton.setOnAction(e -> {
            client.showLoginPage();
        });

        loginButton.setOnAction(e -> {
            client.showLoginPage();
        });
    }

    private void signUPAlert(String title, String alMessage) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(alMessage);
        alert.showAndWait();
    }
}