package org.example.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.example.UsersFackeData.CardData;
import static org.example.UsersFackeData.DummyData.getDummyCardData;

public class ContactsLayoutController {

    @FXML
    private AnchorPane cardContainer;

    public void initialize() {
        List<CardData> cardDataList = getDummyCardData(); // get dummy data
        double layoutY = 0.0;
        for (CardData cardData : cardDataList) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/org/example/views/CardItem.fxml"));
                AnchorPane cardItem = loader.load();
                CardItemController controller = loader.getController();
                controller.setData(cardData.getTitle(), cardData.getImageUrl());
                cardItem.setLayoutY(layoutY);
                cardContainer.getChildren().add(cardItem);
                layoutY += cardItem.getPrefHeight() + 10.0; // add spacing between items
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}