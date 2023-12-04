package org.example;

import org.example.result.GameResult;
import org.example.result.JSONGameResultManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.result.PlayerStatistics;
import java.io.IOException;
import java.nio.file.Path;

public class TopPlayersController {
    @FXML
    private TableView<PlayerStatistics> tableView;

    @FXML
    private TableColumn<GameResult, String> winnerName;

    @FXML
    private TableColumn<GameResult, Long> numberOfWins;

    @FXML
    private void switchToMainMenu(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/MainMenu.fxml"));
        stage.setTitle("Main Menu");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void initialize() throws IOException {
        winnerName.setCellValueFactory(new PropertyValueFactory<>("winnerName"));
        numberOfWins.setCellValueFactory(new PropertyValueFactory<>("numberOfWins"));
        ObservableList<PlayerStatistics> observableList = FXCollections.observableArrayList();
        observableList.addAll(new JSONGameResultManager(Path.of("results.json")).getBestPlayers(5));
        tableView.setItems(observableList);
    }

}