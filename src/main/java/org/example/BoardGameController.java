package org.example;

import org.example.model.BoardGameModel;
import org.example.model.Square;
import org.example.result.GameResult;
import org.example.result.GameResultManager;
import org.example.result.JSONGameResultManager;
import javafx.beans.binding.ObjectBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import lombok.SneakyThrows;


import java.nio.file.Path;
import java.time.LocalDateTime;


import java.io.IOException;

public class BoardGameController {

    @FXML
    private GridPane board;
    @FXML
    private Label player1;
    @FXML
    private Label player2;
    @FXML
    private Label currentPlayerLabel;
    @FXML
    public RadioButton redButton, blueButton, greenButton, yellowButton;

    private BoardGameModel model = new BoardGameModel();
    private String currentPlayer;
    private Square currentColor;
    private String player1Name, player2Name;
    private int player1Moves = 0;
    private int player2Moves = 0;
    private LocalDateTime startDateTime;

    public Square getCurrentColor() throws IOException{
        if (redButton.isSelected() && !blueButton.isSelected() && !yellowButton.isSelected() && !greenButton.isSelected()){
            currentColor = Square.RED;
            return currentColor;
        } else if (blueButton.isSelected() && !redButton.isSelected() && !yellowButton.isSelected() && !greenButton.isSelected()) {
            currentColor = Square.BLUE;
            return currentColor;
        }else if (yellowButton.isSelected() && !redButton.isSelected() && !blueButton.isSelected() && !greenButton.isSelected()) {
            currentColor = Square.YELLOW;
            return currentColor;
        }else if (greenButton.isSelected() && !redButton.isSelected() && !yellowButton.isSelected() && !blueButton.isSelected()) {
            currentColor = Square.GREEN;
            return currentColor;
        }
        return Square.NONE;
    }
    @FXML
    private void initialize() {
//        currentPlayerLabel.setText(player1Name);
        startDateTime = LocalDateTime.now();
        for (var i = 0; i < board.getRowCount(); i++) {
            for (var j = 0; j < board.getColumnCount(); j++) {
                var square = createSquare(i, j);
                board.add(square, j, i);
            }
        }
    }

    void displayName1(String playerName1) {
        this.player1Name = playerName1;
        player1.setText(player1Name);
        currentPlayerLabel.setText(player1Name);
    }

    void displayName2(String playerName2) {
        this.player2Name = playerName2;
        player2.setText(player2Name);
    }



    private StackPane createSquare(int i, int j) {
        var square = new StackPane();
        square.getStyleClass().add("square");
        var piece = new Circle(50);

        piece.fillProperty().bind(
                new ObjectBinding<Paint>() {
                    {
                        super.bind(model.squareProperty(i, j));
                    }

                    @SneakyThrows
                    @Override
                    protected Paint computeValue() {
                        if (getCurrentColor()==Square.BLUE){
                            return Color.BLUE;
                        } else if (getCurrentColor() == Square.YELLOW) {
                            return Color.YELLOW;
                        } else if (getCurrentColor() == Square.RED) {
                            return Color.RED;
                        } else if (getCurrentColor() == Square.GREEN) {
                            return Color.GREEN;
                        }else{
                            return Color.TRANSPARENT;
                        }
                    }
                }
        );

        square.getChildren().add(piece);
        square.setOnMouseClicked(this::handleMouseClick);
        return square;
    }

    @FXML
    private void handleMouseClick(MouseEvent event)  {
        var square = (StackPane) event.getSource();
        var row = GridPane.getRowIndex(square);
        var col = GridPane.getColumnIndex(square);
        System.out.println("Current Player: " + currentPlayer);
        System.out.printf("Click on square (%d,%d)%n", row, col);

        if (model.getSquare(row, col) == Square.NONE ) {

            try {
                if (model.isValidMove(row, col, getCurrentColor())){

                    currentPlayer = currentPlayer==player1Name? player2Name:player2Name;
                    model.move(row, col, getCurrentColor());
                    if (getCurrentColor() == Square.RED || getCurrentColor() == Square.BLUE){
                        player1Moves++;
                        currentPlayerLabel.setText(player2Name);}
                    else if (getCurrentColor() == Square.GREEN || getCurrentColor() == Square.YELLOW){
                        player2Moves++;
                        currentPlayerLabel.setText(player1Name);
                    }
                    System.out.println("Player 1 moves: " + player1Moves);
                    System.out.println("Player 2 moves: " + player2Moves);}

            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
            if(model.checkForWin() == true) {
                String winnerName = player1Moves > player2Moves ? player1Name : player2Name;

                GameResult gameResult = createGameResult(winnerName);
                GameResultManager manager = new JSONGameResultManager(Path.of("results.json"));
                try {
                    manager.add(gameResult);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/GameOver.fxml"));
                    Parent root = loader.load();
                    GameOverController gameOverController = loader.getController();
                    gameOverController.setWinnerLabel(winnerName);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                    stage.setTitle("Board Game");
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (player1Moves==6 && player2Moves==6){
                String winnerName = "Draw";

                GameResult gameResult = createGameResult(winnerName);
                GameResultManager manager = new JSONGameResultManager(Path.of("results.json"));
                try {
                    manager.add(gameResult);
                }catch (IOException e){
                    e.printStackTrace();
                }
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/GameOver.fxml"));
                    Parent root = loader.load();
                    GameOverController gameOverController = loader.getController();
                    gameOverController.setWinnerLabel(winnerName);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                    stage.setTitle("Board Game");
                    stage.setScene(new Scene(root));
                    stage.show();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }else{ }
    }


    private GameResult createGameResult(String winnerName) {

        return GameResult.builder()
                .player1Name(player1Name)
                .player2Name(player2Name)
                .winnerName(winnerName)
                .player1Moves(player1Moves)
                .player2Moves(player2Moves)
                .startDateTime(startDateTime)
                .build();
    }
}






