package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CardItemController {

    @FXML
    private ImageView image;

    @FXML
    private Label title;

    @FXML
    private Button button;

    public void setData(String title, String imageUrl) {
        this.title.setText(title);
        this.image.setImage(new Image(imageUrl));
    }

    @FXML
    public void handleButtonClick() {
        System.out.println("Button clicked!");
    }

}