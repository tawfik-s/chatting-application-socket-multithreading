package com.example.chattingapplicationsocketmultithreading;

import com.example.chattingapplicationsocketmultithreading.UsersData.CardData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;


public class CardItemController {

    @FXML
    private ImageView image;

    @FXML
    private Label title;

    @FXML
    private Label subtitle;

    private CardData cardData;

    private Client client;
    private Socket socket;
    private String username;

    public void setclientApp(Client client) {
        this.client = client;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setData(CardData cardData) {
        this.cardData = cardData;
        this.title.setText(cardData.getTitle());
        this.subtitle.setText(cardData.getSubtitle());
    }

    @FXML
    public void handleCardClick() throws IOException {
        System.out.println("Card clicked: " + cardData.getTitle());

        FXMLLoader loader = new FXMLLoader(Client.class.getResource("ChatPage.fxml"));
        ChatPageController controller = new ChatPageController();
        controller.chatPartner = cardData.getTitle();
        controller.setclientApp(client);
        controller.setSocket(socket);
        controller.setUsername(username);
//        controller.initialize();
        loader.setController(controller);
