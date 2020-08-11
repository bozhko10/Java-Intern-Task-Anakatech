/**
 * The game object for the Minesweeper game
 *
 * The class contains all the necessary methods for playing the game
 */
public class Minesweeper {
    // Variable for board side
    private int SIDE;
    // Variable for number of mines
    private int MINES;

    // Variables for the boards
    char[][] gameBoard;
    char[][] playerBoard;
    // Variable for keeping the coordinates of the mines
    private int[][] minesCoordinates;
    // Tells us whether the game is on/over
    private boolean isItOver;
    // Variable for the number of moves the player can make
    private int movesLeft;

    /**
     * Constructor
     *
     * Depending on the givenLevel, we set the board sides and number of mines
     * @param level int
     */
    public Minesweeper(int level) {
        switch (level) {
            case 0: SIDE = 9;
                    MINES = 10;
                    break;
            case 1: SIDE = 16;
                    MINES = 40;
                    break;
            case 2: SIDE = 24;
                    MINES = 99;
                    break;
        }
    }

    /**
     * Method for the beginning of the game
     *
     * All board initialization of boards and adding the mines are made here
     * How many moves the player can make is calculated also here
     */
    public void startGame() {
        gameBoard = new char[SIDE][SIDE];
        playerBoard = new char[SIDE][SIDE];
        minesCoordinates = new int[MINES][2];

        initialiseBoard(gameBoard);
        initialiseBoard(playerBoard);
        addMines(minesCoordinates, gameBoard);

        // How many moves the player can make
        movesLeft = SIDE * SIDE - MINES;
        isItOver = false;
    }

    /**
     * Method for drawing the given board
     *
     * @param playerBoard char[][]
     */
    public void printBoard(char[][] playerBoard) {
        System.out.print("    ");
        for (int i = 0; i < SIDE; i++)
            System.out.print(i + " ");

        System.out.println();

        for (int i = 0; i < SIDE; i++) {
            if (i > 9) System.out.print(i + "  ");
            else System.out.print(i + "   ");

            for (int j = 0; j < SIDE; j++) {
                if (j == 10) System.out.print(" " + playerBoard[i][j] + "  ");
                else if (j > 10) System.out.print(playerBoard[i][j] + "  ");
                else System.out.print(playerBoard[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Get method for gameBoard
     *
     * @return gameBoard
     */
    public char[][] getGameBoard() {
        return gameBoard;
    }

    /**
     * Get method for playerBoard
     *
     * @return playerBoard
     */
    public char[][] getPlayerBoard() {
        return playerBoard;
    }

    /**
     * Get method for mine's coordinates
     *
     * @return minesCoordinates
     */
    public int[][] getMinesCoordinates() { return minesCoordinates; }

    /**
     * Get method for the numbers of moves left to make
     *
     * @return movesLeft
     */
    public int getMovesLeft() {
        return movesLeft;
    }

    /**
     * Method for checking whether the coordinates are on mine or not
     *
     * @param row int
     * @param col int
     * @return boolean
     */
    public boolean isItMine(int row, int col) {
        return gameBoard[row][col] == '*';
    }

    /**
     * Method for checking whether the coordinates are in range of the board
     *
     * @param row int
     * @param col int
     * @return boolean
     */
    public boolean isValid(int row, int col) {
        return (row >= 0) && (row < SIDE) && (col >= 0) && (col < SIDE);
    }

    /**
     * Method for checking the status of the game
     *
     * @return isItOver
     */
    public boolean isItOver() {
        return isItOver;
    }

    /**
     * Method for setting the status of the game
     *
     * @param bool boolean
     */
    public void setOver(boolean bool) { isItOver = bool; }

    /**
     * Recursive method for execting a turn
     *
     * Given the coordinates, the method checks it's mine -> the player loses
     * if it's not the method recursively steps on all the safe adjacent cells (hence reducing the time of the game-play)
     * and displays the count of mines around the current cell
     *
     * @param row int
     * @param col int
     */
    public void executeTurn(int row, int col) {

        // The base case of the recursion
        if (playerBoard[row][col] != '-') {
            isItOver = false;
            return;
        }

        // The player loses
        if (gameBoard[row][col] == '*') {
            playerBoard[row][col] = '*';

            // Display all the mines
            for (int i = 0; i < MINES; i++)
                playerBoard[minesCoordinates[i][0]][minesCoordinates[i][1]] = '*';
            printBoard(playerBoard);
            // Message
            System.out.println("\nYou lost!");
            // The status of the game is updated
            isItOver = true;
        }
        else {
            // Finds the count around the cell
            int adjCount = findAdjacentMines(row, col);

            // Decrement cause this cell is reviled
            movesLeft--;
            // Display the count
            playerBoard[row][col] = (char) (adjCount + '0');

            // If they are no adjacent mines to this cell, recursively step on all the safe adjacent cells
            if (adjCount == 0) {
                //UP
                if (isValid(row - 1, col) && !isItMine(row - 1, col))
                        executeTurn(row - 1, col);
                //DOWN
                if (isValid(row + 1, col) && !isItMine(row + 1, col))
                        executeTurn(row + 1, col);
                //LEFT
                if (isValid(row, col - 1) && !isItMine(row, col - 1))
                        executeTurn(row, col - 1);
                //RIGHT
                if (isValid(row, col + 1) && !isItMine(row, col + 1))
                    executeTurn(row, col + 1);
                //UP-RIGHT
                if (isValid(row - 1, col + 1) && !isItMine(row - 1, col + 1))
                        executeTurn(row - 1, col + 1);
                //UP-LEFT
                if (isValid(row - 1, col - 1) && !isItMine(row - 1, col - 1))
                        executeTurn(row - 1, col - 1);
                //DOWN-LEFT
                if (isValid(row + 1, col - 1) && !isItMine(row + 1, col - 1))
                        executeTurn(row + 1, col - 1);
                //DOWN-RIGHT
                if (isValid(row + 1, col + 1) && !isItMine(row + 1, col + 1))
                    executeTurn(row + 1, col + 1);
            }

            // Update the status
            isItOver = false;
        }
        //0return;
    }

    /**
     * Method for find the count of adjacent cells which are mines
     *
     * @param row int
     * @param col int
     * @return int
     */
    private int findAdjacentMines(int row, int col) {
        // counter
        int count = 0;

        //UP
        if (isValid(row - 1, col) && isItMine(row - 1, col))
                count++;
        //DOWN
        if (isValid(row + 1, col) && isItMine(row + 1, col))
                count++;
        //LEFT
        if (isValid(row, col - 1) && isItMine(row, col - 1))
                count++;
        //RIGHT
        if (isValid(row, col + 1) && isItMine(row, col + 1))
            count++;
        //UP-RIGHT
        if (isValid(row - 1, col + 1) && isItMine(row - 1, col + 1))
                count++;
        //UP-LEFT
        if (isValid(row - 1, col - 1) && isItMine(row - 1, col - 1))
                count++;
        //DOWN-LEFT
        if (isValid(row + 1, col - 1) && isItMine(row + 1, col - 1))
                count++;
        //DOWN-RIGHT
        if (isValid(row + 1, col + 1) && isItMine(row + 1, col + 1))
            count++;

        return count;
    }

    /**
     * Method for randomly adding mines at the beginning of the game
     *
     * @param minesCoordinates int[][]
     * @param gameBoard int[][]
     */
    private void addMines(int[][] minesCoordinates, char[][] gameBoard) {
        int index = 0;
        while(index < MINES) {
            int x = (int) (Math.random() * (SIDE + 1));
            int y = (int) (Math.random() * (SIDE + 1));

            // They must in range of the board, if not try again
            if (isValid(x, y))
                if (gameBoard[x][y] == '-') {
                    minesCoordinates[index][0] = x;
                    minesCoordinates[index][1] = y;

                    gameBoard[x][y] = '*';
                    index++;
                }
        }
    }

    /**
     * Method which set all cell to '-' of a board
     *
     * @param board char[][]
     */
    private void initialiseBoard(char[][] board) {
        for (int i = 0; i < SIDE; i++)
            for (int j = 0; j < SIDE; j++)
                board[i][j] = '-';
    }

}
