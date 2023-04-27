package org.example.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

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

    public void initialize() {
        // Set the header label text and the avatar image here
        headerLabel.setText("Chat with " + chatPartner);

        // Add event handler for the back button
        backButton.setOnAction(e -> {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/java/org/example/views/ContactsLayout.fxml"));
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