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
            loginController.setclientApp(this);

            Scene scene = new Scene(loginPage);
            this.primaryStage.setScene(scene);
            this.primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showLoginPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            loginPage = loader.load();
            LoginController loginController = loader.getController();
            loginController.setclientApp(this);

            Scene scene = new Scene(loginPage);
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showSignUpPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("signup.fxml"));
            signUpPage = loader.load();
            SignupController signupController = loader.getController();
            signupController.setclientApp(this);

            Scene scene = new Scene(signUpPage);
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showContactsPage(Socket socket, String username) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ContactsLayout.fxml"));
            contactsPage = loader.load();
            ContactsLayoutController contactsController = loader.getController();
            contactsController.setclientApp(this);
            contactsController.setSocket(socket);
            contactsController.setUsername(username);

            contactsController.initializeUI();

            Scene scene = new Scene(contactsPage);
            primaryStage.setScene(scene);
            contactsController.setSocket(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showChatPage(Socket socket, String username) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ChatPage.fxml"));
            chatPage = loader.load();
            ChatPageController chatController = loader.getController();
            chatController.setclientApp(this);
            chatController.setSocket(socket);
            chatController.setUsername(username);

            Scene scene = new Scene(chatPage);
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




        public static void main(String[] args) {
        launch();
    }
}
