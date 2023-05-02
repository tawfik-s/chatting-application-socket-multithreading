package com.example.chattingapplicationsocketmultithreading;

import com.example.chattingapplicationsocketmultithreading.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ChatPageController {

    @FXML
    private HBox headerBox;

    @FXML
    private Button backButton;

    @FXML
    private Label headerLabel;

    @FXML
    private ListView<String> messageList;

    @FXML
    private TextField messageField;

    @FXML
    private Button sendButton;

    public String chatPartner = "person";

    private Client client;
    private Socket socket;
    private String username;

    private BufferedReader reader;
    private OutputStreamWriter writer;

    public void setclientApp(Client client) {
        this.client = client;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void initialize() {
        // Set the header label text and the avatar image here
        headerLabel.setText("Chat with " + chatPartner);

        // Add event handler for the back button
        backButton.setOnAction(e -> {
            FXMLLoader fxmlLoader = new FXMLLoader(Client.class.getResource("ContactsLayout.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        });

        // Add event handler for the send button
        sendButton.setOnAction(event -> {
            String message = messageField.getText().trim();
            if (!message.isEmpty()) {
                sendMessage(message);
                messageField.clear();
            }
        });

        // Add event handler for the message field
        messageField.setOnAction(event -> {
            String message = messageField.getText().trim();
            if (!message.isEmpty()) {
                sendMessage(message);
                messageField.clear();
            }
        });
    }

    private void sendMessage(String message) {
        // Add the message to the message list as a sent message
        messageList.getItems().add("You: " + message);

        // Simulate receiving a message after a delay
        String response = "John: Hello, how are you?";
        messageList.getItems().add(response);
    }
}