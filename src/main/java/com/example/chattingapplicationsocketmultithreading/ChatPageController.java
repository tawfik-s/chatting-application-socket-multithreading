package com.example.chattingapplicationsocketmultithreading;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

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

    @FXML
    private Button refreshsButton;

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



        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(4), event -> {
            // Clear the message list and fetch the latest messages from the server
            System.out.println("refresh");
            messageList.getItems().clear();
            pw.println(messageToServer);

            String response2;
            try {
                response2 = br.readLine();
                List<String> tokens = Arrays.stream(response2.split("\\|")).toList();
                for (var token : tokens) {
                    sendMessage(token);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            setFocusToEnd();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();


        System.out.println(messageToServer);
//        System.out.println(response.get());

        /**
         * we need to edit here to know how send to how
         */
        List<String> tokens = Arrays.stream(response.split("\\|")).toList();
        for (var token : tokens) {
            sendMessage(token);
        }

        setFocusToEnd();
//        Platform.runLater(() -> messageField.requestFocus());

        // Add event handler for the back button
        backButton.setOnAction(e -> {
//            thread.interrupt();
            client.showContactsPage(socket, username);
        });

        // Add event handler for the send button
        sendButton.setOnAction(event -> {
            String message = messageField.getText().trim();
            if (!message.isEmpty()) {
                sendMessage(message);
                pw.println("SendMessage|"+chatPartner+"|"+message);


                messageField.clear();
            }
            referech();
        });
        refreshsButton.setOnAction(event -> {
            referech();


        });

        // Add event handler for the message field
        messageField.setOnAction(event -> {
            String message = messageField.getText().trim();
            if (!message.isEmpty()) {
                sendMessage(message);
                pw.println("SendMessage|"+chatPartner+"|"+message);

                messageField.clear();
            }
            referech();
        });
    }

    private void sendMessage(String message) {
        // Add the message to the message list as a sent message
        messageList.getItems().add(message);

    }

    public void setFocusToEnd() {
        int lastIndex = messageList.getItems().size() - 1;
        messageList.scrollTo(lastIndex);
        messageList.getSelectionModel().select(lastIndex);
        messageList.getFocusModel().focus(lastIndex);
    }

    public void referech() {
        FXMLLoader loader = new FXMLLoader(Client.class.getResource("ChatPage.fxml"));
        ChatPageController controller = new ChatPageController();
        controller.chatPartner = chatPartner;
        controller.setclientApp(client);
        controller.setSocket(socket);
        controller.setUsername(username);
//        controller.initialize();
        loader.setController(controller);

        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) headerBox.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}