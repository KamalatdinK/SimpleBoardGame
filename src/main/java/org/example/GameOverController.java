package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class GameOverController {
    @FXML
    private Label winnerLabel;

    @FXML
    private void switchToTopPlayers(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/topPlayers.fxml"));
        stage.setTitle("Top Players");
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    private void switchToMainMenu(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/MainMenu.fxml"));
        stage.setTitle("Game Over");
        stage.setScene(new Scene(root));
        stage.show();
    }
    public void setWinnerLabel(String winner){
        if (winner=="Draw")
            winnerLabel.setText(winner);
        else{
            winnerLabel.setText("Winner:" + winner);
        }

    }

}
