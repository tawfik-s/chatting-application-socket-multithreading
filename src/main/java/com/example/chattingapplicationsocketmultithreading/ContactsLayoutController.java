package com.example.chattingapplicationsocketmultithreading;

import com.example.chattingapplicationsocketmultithreading.UsersData.CardData;
//import com.example.chattingapplicationsocketmultithreading.UsersData.UsersData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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


    public void initializeUI() {

        // set the background color of the header to a suitable shade of green
        headerOfContacts.setStyle("-fx-background-color: #007f69; -fx-padding: 10px;");
        titleLabel.setText("Contacts");
        String message = "LoadUsers";


        List<CardData> cardDataList = new ArrayList<>();
        try {
            var br = new BufferedReader( new InputStreamReader( socket.getInputStream()) ) ;
            var pw = new PrintWriter(socket.getOutputStream(),true);

            pw.println(message);

            String response = new String(br.readLine());

            System.out.println(message);
            System.out.println(response);

            if(response == null) {
                alertError("Error in loud users",response);
            }

            List<String> tokens = Arrays.stream(response.split("\\|")).toList();
            for(var token:tokens) {
                cardDataList.add(new CardData(token, "Chat With me"));
            }

        } catch (IOException exception) {
            exception.printStackTrace();
        }




        double layoutY = 0.0;
        int insertIndex = cardDataList.size() / 2; // insert cards in the middle
        for (int i = 0; i < cardDataList.size(); i++) {
            CardData cardData = cardDataList.get(i);
            try {
                FXMLLoader loader = new FXMLLoader(Client.class.getResource("CardItem.fxml"));
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
        cardContainer.setStyle("-fx-border-color: #F2F2F2; -fx-border-radius: 15px;");
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

    private void alertError(String title, String alMessage)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(alMessage);
        alert.showAndWait();
    }
}