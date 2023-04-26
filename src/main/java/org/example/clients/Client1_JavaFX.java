package org.example.clients;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Client1_JavaFX extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        //main.java.org.example.views
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/java/org/example/views/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Hello!");
        stage.setScene(scene);

        stage.setResizable(false);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
