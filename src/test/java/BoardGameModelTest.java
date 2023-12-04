
import org.example.model.BoardGameModel;
import org.example.model.Square;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardGameModelTest {

    private BoardGameModel model;

    @BeforeEach
    void setUp() {
        model = new BoardGameModel();
    }

    @Test
    void testInitialBoard() {
        for (int i = 0; i < BoardGameModel.BOARD_SIZE; i++) {
            for (int j = 0; j < BoardGameModel.BOARD_SIZE; j++) {
                assertEquals(Square.NONE, model.getSquare(i, j));
            }
        }
    }

    @Test
    void testMove() {
        int i = 0;
        int j = 0;
        Square currentColor = Square.RED;

        model.move(i, j, currentColor);
        assertEquals(currentColor, model.getSquare(i, j));
    }

    @Test
    void testIsValidMove_ValidMove() {
        int i = 0;
        int j = 0;
        Square color = Square.BLUE;

        assertTrue(model.isValidMove(i, j, color));
    }

    @Test
    void testIsValidMove_InvalidMove_SameColorAdjacent() {
        int i = 0;
        int j = 0;
        Square color = Square.BLUE;

        model.move(i, j + 1, color);

        assertFalse(model.isValidMove(i, j, color));
    }

    @Test
    void testIsValidMove_InvalidMove_ThreeStonesInARow() {
        int i = 0;
        int j = 0;
        Square color = Square.RED;

        model.move(i, j, color);
        model.move(i + 1, j, color);
        model.move(i + 2, j, color);

        assertFalse(model.isValidMove(i + 3, j, color));
    }

    @Test
    void testCheckForWin_NoWin() {
        assertFalse(model.checkForWin());
    }

    @Test
    void testCheckForWin_WinInRow() {
        int i = 0;

        model.move(i, 0, Square.GREEN);
        model.move(i, 1, Square.YELLOW);
        model.move(i, 2, Square.BLUE);
        model.move(i, 3, Square.RED);

        assertTrue(model.checkForWin());
    }

    @Test
    void testCheckForWin_WinInColumn() {
        int j = 0;

        model.move(0, j, Square.GREEN);
        model.move(1, j, Square.YELLOW);
        model.move(2, j, Square.BLUE);
        model.move(3, j, Square.RED);

        assertTrue(model.checkForWin());
    }



}
