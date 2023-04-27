package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.example.UsersData.CardData;

import java.io.IOException;


public class CardItemController {

    @FXML
    private ImageView image;

    @FXML
    private Label title;

    @FXML
    private Label subtitle;

    private CardData cardData;

    public void setData(CardData cardData) {
        this.cardData = cardData;
        this.title.setText(cardData.getTitle());
        this.subtitle.setText(cardData.getSubtitle());
    }

    @FXML
    public void handleCardClick() throws IOException {
        System.out.println("Card clicked: " + cardData.getTitle());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/org/example/views/ChatPage.fxml"));
        ChatPageController controller = new ChatPageController();
        controller.chatPartner = cardData.getTitle();
        loader.setController(controller);

        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) title.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}