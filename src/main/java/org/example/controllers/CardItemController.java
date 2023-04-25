package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CardItemController {

    @FXML
    private Label titleLabel;

    @FXML
    private Label descriptionLabel;

    public void setTitle(String title) {
        titleLabel.setText(title);
    }

    public void setDescription(String description) {
        descriptionLabel.setText(description);
    }
}