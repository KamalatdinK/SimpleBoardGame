package org.example;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {

    @FXML
    private void switchToNames(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/InputNames.fxml"));
        stage.setTitle("Players' name");
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    private void switchToTopPlayers(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/topPlayers.fxml"));
        stage.setTitle("Top Players");
        stage.setScene(new Scene(root));
        stage.show();
    }

}