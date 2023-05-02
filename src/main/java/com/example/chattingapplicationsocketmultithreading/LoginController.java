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

import java.io.*;
import java.net.Socket;


public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private Client client;

    public void setclientApp(Client client) {
        this.client = client;
    }

    @FXML
    private void loginButtonAction(ActionEvent e) throws IOException {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String message = "Login|";
        // Check if the username and password fields are empty
        if (username.isEmpty() || password.isEmpty()) {
            // Display an error message to the user
            loginAlert("Login Error","Please enter both a username and password.");
            return;
        }



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
                loginAlert("Login Success",response);
            } else {
                loginAlert("Login Error",response);
            }

        } catch (IOException exception) {
            exception.printStackTrace();
        }
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
        client.showSignUpPage();
    }

    private void loginAlert(String title, String alMessage)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(alMessage);
        alert.showAndWait();
    }
}