package org.example.model;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import java.util.ArrayList;

public class BoardGameModel {
    /**
     * The size of the game board (number of rows and columns).
     */
    public static final int BOARD_SIZE = 4;
    private final ReadOnlyObjectWrapper<Square>[][] board = new ReadOnlyObjectWrapper[BOARD_SIZE][BOARD_SIZE];

    /**
     * Constructs a new instance of the BoardGameModel class.
     * Initializes the game board with empty squares
     */
    public BoardGameModel() {
        for (var i = 0; i < BOARD_SIZE; i++) {
            for (var j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = new ReadOnlyObjectWrapper<>(Square.NONE);
            }
        }
    }

    /**
     * Retrieves the read-only property for the square at the specified position.
     *
     * @param i The row index of the square.
     * @param j The column index of the square.
     * @return The read-only property for the square.
     */
    public ReadOnlyObjectProperty<Square> squareProperty(int i, int j) {
        return board[i][j].getReadOnlyProperty();
    }
    /**
     * Retrieves the square at the specified position.
     *
     * @param i The row index of the square.
     * @param j The column index of the square.
     * @return The square at the specified position.
     */

    public Square getSquare(int i, int j) {
        return board[i][j].get();
    }
    /**
     * Moves a stone to the specified position on the game board.
     *
     * @param i            The row index of the position.
     * @param j            The column index of the position.
     * @param currentColor The color of the stone to be placed.
     */

    public void move(int i, int j, Square currentColor) {
        board[i][j].set(currentColor);
    }
    /**
     * Checks if a move to the specified position with the given color is valid.
     * A move is valid if the position is empty, there are not more than 3 stones of the same color,
     * and there are no adjacent squares with the same color.
     *
     * @param i     The row index of the position.
     * @param j     The column index of the position.
     * @param color The color of the stone to be placed.
     * @return {@code true} if the move is valid, {@code false} otherwise.
     */

    public boolean isValidMove(int i, int j, Square color) {
        if (board[i][j].get() != Square.NONE) {
            return false;
        }
        int colorCount = 0;
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board[row][col].get() == color) {
                    colorCount++;
                }
                if (colorCount >= 3) {
                    return false;
                }
            }
        }

        // Check adjacent squares
        for (int row = Math.max(0, i - 1); row <= Math.min(BOARD_SIZE - 1, i + 1); row++) {
            for (int col = Math.max(0, j - 1); col <= Math.min(BOARD_SIZE - 1, j + 1); col++) {
                if ( board[row][col].get() == color ) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if there is a win condition on the game board.
     * A win condition is met if there are four stones of the same color in a row or column.
     *
     * @return {@code true} if there is a win condition, {@code false} otherwise.
     */
    public boolean checkForWin() {
        // Check rows for a win
        for (int row = 0; row < BOARD_SIZE; row++) {
            ArrayList<Square> squares = new ArrayList<Square>();
            for (int col = 0; col < BOARD_SIZE; col++) {
                Square square = board[row][col].get();
                if (square != Square.NONE && !squares.contains(square)) {
                    squares.add(square);
                }
            }
            if (squares.size() == BOARD_SIZE) {
                return true;
            }
        }

        // Check columns for a win
        for (int col = 0; col < BOARD_SIZE; col++) {
            ArrayList<Square> squares = new ArrayList<>();
            for (int row = 0; row < BOARD_SIZE; row++) {
                Square square = board[row][col].get();
                if (square != Square.NONE && !squares.contains(square)) {
                    squares.add(square);
                }
            }
            if (squares.size() == BOARD_SIZE) {
                return true;
            }
        }
        return false;
    }
    public static void main(String[] args) {
        var model = new BoardGameModel();
        System.out.println(model);
    }
}
