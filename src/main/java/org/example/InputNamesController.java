package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

import java.io.IOException;

public class InputNamesController {

    @FXML
    TextField nameTextField1;
    @FXML
    TextField nameTextField2;
    @FXML
    private void switchBoard(ActionEvent event) throws IOException {
        String playerName1 = nameTextField1.getText();
        String playerName2 = nameTextField2.getText();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Board.fxml"));
        Parent root = loader.load();
        BoardGameController boardGameController = loader.getController();

        boardGameController.displayName1(playerName1);
        boardGameController.displayName2(playerName2);
//        boardGameController.setPlayerName(playerName1);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //Parent root = FXMLLoader.load(getClass().getResource("/Board.fxml"));
        stage.setTitle("Board Game");
        stage.setScene(new Scene(root));
        stage.show();
    }



}