package com.example.chattingapplicationsocketmultithreading;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;


public class Client extends Application {


    private Stage primaryStage;
    private AnchorPane loginPage;
    private AnchorPane signUpPage;
    private AnchorPane contactsPage;
    private BorderPane chatPage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("JavaFX Chat App");

        // Load login page
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            loginPage = loader.load();
            LoginController loginController = loader.getController();
            loginController.setMainApp(this);

            Scene scene = new Scene(loginPage);
            this.primaryStage.setScene(scene);
            this.primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showContactsPage(Socket socket, String username) {
        // Load main page
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
            contactsPage = loader.load();
            ContactsLayoutController mainController = loader.getController();
            mainController.setMainApp(this);
            mainController.setSocket(socket);
            mainController.setUsername(username);

            Scene scene = new Scene(contactsPage);
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        public static void main(String[] args) {
        launch();
    }
}
