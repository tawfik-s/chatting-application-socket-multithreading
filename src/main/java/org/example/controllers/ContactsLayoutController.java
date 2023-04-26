package org.example.controllers;

import java.io.IOException;
import java.util.List;

import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.UsersData.CardData;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.example.UsersData.UsersData;

public class ContactsLayoutController {

    @FXML
    private AnchorPane cardContainer;

    @FXML
    private AnchorPane headerOfContacts;

    @FXML
    private Button backButton;

    @FXML
    private Button menuButton;

    @FXML
    private Label titleLabel;

    public void initialize() {
        // set the background color of the header to a suitable shade of green
        headerOfContacts.setStyle("-fx-background-color: #007f69; -fx-padding: 10px;");
        titleLabel.setText("Contacts");

        List<CardData> cardDataList = UsersData.getDummyCardData(); // get dummy data
        double layoutY = 0.0;
        int insertIndex = cardDataList.size() / 2; // insert cards in the middle
        for (int i = 0; i < cardDataList.size(); i++) {
            CardData cardData = cardDataList.get(i);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/org/example/views/CardItem.fxml"));
                AnchorPane cardItem = loader.load();
                CardItemController controller = loader.getController();
                controller.setData(cardData);
                cardItem.setLayoutY(layoutY);
                cardItem.setLayoutX(72);
                cardItem.setStyle("-fx-background-color: white; -fx-border-color: #ECE5DD; -fx-border-width: 1px; -fx-border-radius: 15px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 5, 0, 0, 1);");
                if (i == insertIndex) {
                    cardContainer.getChildren().addAll(cardItem, createSpacer()); // insert cards and spacer
                } else {
                    cardContainer.getChildren().add(cardItem);
                }
                layoutY += cardItem.getPrefHeight() + 10.0; // add spacing between items
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        cardContainer.setStyle("-fx-border-color: #F2F2F2;");
    }

    private Region createSpacer() {
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        return spacer;
    }

    @FXML
    private void handleBackButtonAction(ActionEvent event) {
        System.out.println("Back button clicked!");
        Scene currentScene = backButton.getScene();
        Scene prevScene = (Scene) currentScene.getUserData();
        if (prevScene != null) {
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(prevScene);
        } else {
            System.out.println("No previous scene in history!");
        }
    }

    @FXML
    private void handleMenuButtonAction(ActionEvent event) {
        System.out.println("Menu button clicked!");

        // create a ContextMenu with menu items
        MenuItem createGroupMenuItem = new MenuItem("Create Group");
        MenuItem settingsMenuItem = new MenuItem("Settings");
        MenuItem profileMenuItem = new MenuItem("Profile");
        createGroupMenuItem.setOnAction(e -> {
            System.out.println("Create Group clicked!");
            // Add your code to handle the Create Group action here
        });
        settingsMenuItem.setOnAction(e -> {
            System.out.println("Settings clicked!");
            // Add your code to handle the Settings action here
        });
        profileMenuItem.setOnAction(e -> {
            System.out.println("Profile clicked!");
            // Add your code to handle the Profile action here
        });
        ContextMenu contextMenu = new ContextMenu(createGroupMenuItem, settingsMenuItem, profileMenuItem);

        // show the ContextMenu near the MenuButton
        Node source = (Node) event.getSource();
        contextMenu.show(source, Side.BOTTOM, 0, 0);
    }
}