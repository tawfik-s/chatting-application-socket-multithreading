package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.UsersData.CardData;


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
    public void handleCardClick() {
        System.out.println("Card clicked: " + cardData.getTitle());
        // Add code to handle card click event here
    }
}