package com.example.chattingapplicationsocketmultithreading;

import com.example.chattingapplicationsocketmultithreading.Client;
import com.example.chattingapplicationsocketmultithreading.UsersData.CardData;
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

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

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

    public void initialize() throws IOException {
        // Set the header label text and the avatar image here
        headerLabel.setText("Chat with " + chatPartner);
        String messageToServer = "Load|" + chatPartner;

        var br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        var pw = new PrintWriter(socket.getOutputStream(), true);

        pw.println(messageToServer);

        String response = new String(br.readLine());
        System.out.println(messageToServer);
        System.out.println(response);

        /**
         * we need to edit here to know how send to how
         */
        List<String> tokens = Arrays.stream(response.split("\\|")).toList();
        for (var token : tokens) {
            sendMessage(token);
        }

        // Add event handler for the back button
        backButton.setOnAction(e -> {
            client.showContactsPage(socket, username);
        });

        // Add event handler for the send button
        sendButton.setOnAction(event -> {
            String message = messageField.getText().trim();
            if (!message.isEmpty()) {
                pw.println("SendMessage|"+chatPartner+"|"+message);
                sendMessage(message);
                try {
                    String response1 = new String(br.readLine());
                    System.out.println(response1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                messageField.clear();
            }
        });

        // Add event handler for the message field
        messageField.setOnAction(event -> {
            String message = messageField.getText().trim();
            if (!message.isEmpty()) {
                sendMessage(message);
                pw.println("SendMessage|"+chatPartner+"|"+message);
                try {
                    String response2 = new String(br.readLine());
                    System.out.println(response2);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                messageField.clear();
            }
        });
    }

    private void sendMessage(String message) {
        // Add the message to the message list as a sent message
        messageList.getItems().add(message);

    }
}