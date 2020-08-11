import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MinesweeperTest {

    @Test
    void startBeginnerGame() {
        // Create easy game
        Minesweeper game = new Minesweeper(0);
        game.startGame();

        // Check whether the initialisation of boards are right
        assertEquals(9, game.getGameBoard().length);
        assertEquals(9, game.getPlayerBoard().length);
        assertEquals(9 * 9 - 10, game.getMovesLeft());

        // Check the game board is initialised and contains the right amount of mines
        int countMines = 0;
        for (int i = 0; i < game.getGameBoard().length; i++)
            for (int j = 0; j < game.getGameBoard().length; j++)
                if (game.getGameBoard()[i][j] == '*') countMines++;
        assertEquals(10, countMines);
    }

    @Test
    void startIntermediateGame() {
        // Create intermediate game
        Minesweeper game = new Minesweeper(1);
        game.startGame();

        // Check whether the initialisation of boards are right
        assertEquals(16, game.getGameBoard().length);
        assertEquals(16, game.getPlayerBoard().length);
        assertEquals(16 * 16 - 40, game.getMovesLeft());

        // Check the game board is initialised and contains the right amount of mines
        int countMines = 0;
        for (int i = 0; i < game.getGameBoard().length; i++)
            for (int j = 0; j < game.getGameBoard().length; j++)
               if (game.getGameBoard()[i][j] == '*') countMines++;
        assertEquals(40, countMines);
    }

    @Test
    void startAdvancedGame() {
        // Create hard game
        Minesweeper game = new Minesweeper(2);
        game.startGame();

        // Check whether the initialisation of boards are right
        assertEquals(24, game.getGameBoard().length);
        assertEquals(24, game.getPlayerBoard().length);
        assertEquals(24 * 24 - 99, game.getMovesLeft());

        // Check the game board is initialised and contains the right amount of mines
        int countMines = 0;
        for (int i = 0; i < game.getGameBoard().length; i++)
            for (int j = 0; j < game.getGameBoard().length; j++)
                if (game.getGameBoard()[i][j] == '*') countMines++;
        assertEquals(99, countMines);
    }

    @Test
    void isValid() {
        Minesweeper game = new Minesweeper(0);

        // Check given Valid Coord
        int validRow = 2;
        int validCol = 5;
        assertTrue(game.isValid(validRow, validCol));

        // Check given Invalid Cood
        int InvalidRow = 10;
        int InvalidCol = -3;
        assertFalse(game.isValid(InvalidRow, InvalidCol));
    }

    @Test
    void isItOver() {
        Minesweeper game = new Minesweeper(0);

        // Check whether the game is NOT over when you start
        game.startGame();
        assertFalse(game.isItOver());

        // Check whether the game is NOT over when you play a safe move
        int safeRow = 0;
        int safeCol = 0;
        for (int i = 0; i < game.getGameBoard().length; i++)
            for (int j = 0; j < game.getGameBoard().length; j++)
                if (game.getGameBoard()[i][j] == '-') { safeRow = i; safeCol = j; break;}

        game.executeTurn(safeRow, safeCol);
        assertFalse(game.isItOver());

        // Check the game is over when you choose mine
        game.executeTurn(game.getMinesCoordinates()[0][0], game.getMinesCoordinates()[0][1]);
        assertTrue(game.isItOver());

        // Check the game is over when you win the game
        Minesweeper gameWin = new Minesweeper(0);
        gameWin.startGame();
        gameWin.setOver(true);
        assertTrue(gameWin.isItOver());
    }

    @Test
    void isItMine() {
        // Create intermediate game
        Minesweeper game = new Minesweeper(0);
        game.startGame();

        // Check whether the first mine is '*'
        assertTrue(game.isItMine(game.getMinesCoordinates()[0][0], game.getMinesCoordinates()[0][1]));
    }

    @Test
    void executeTurn() {
        Minesweeper game = new Minesweeper(1);

        game.startGame();
        // Check whether the player board changes when you do safe move
        int safeRow = 0;
        int safeCol = 0;
        for (int i = 0; i < game.getGameBoard().length; i++)
            for (int j = 0; j < game.getGameBoard().length; j++)
                if (game.getGameBoard()[i][j] == '-') { safeRow = i; safeCol = j; break;}

        game.executeTurn(safeRow, safeCol);
        assertNotEquals('-', game.getPlayerBoard()[safeRow][safeCol]);

        // Check whether the player board changes when you do mine move
        int mineRow = game.getMinesCoordinates()[1][0];
        int mineCol = game.getMinesCoordinates()[1][1];

        game.executeTurn(mineRow, mineCol);
        assertEquals('*', game.getPlayerBoard()[mineRow][mineCol]);
    }

    @Test
    void getPlayerBoard() {
        Minesweeper game = new Minesweeper(0);
        game.startGame();
        char[][] wantedBoard = new char[9][9];
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                wantedBoard[i][j] = '-';

        assertArrayEquals(wantedBoard, game.getPlayerBoard());
    }
}