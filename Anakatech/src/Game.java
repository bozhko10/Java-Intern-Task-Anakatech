import java.util.Scanner;

/**
 * Minesweeper game by Bozhidar Nedelchev 11.08.2020
 *
 * This class contains the main method
 */
public class Game {
    public static void main(String[] args) {
        // The player should choose difficulty
        int wantedLevel = chooseDifficulty();

        // The game is created
        Minesweeper myGame = new Minesweeper(wantedLevel);
        // We start the game
        myGame.startGame();

        // Counter for the moves
        int currentMoves = 0;

        // The game is running
        while (!myGame.isItOver()) {
            System.out.println("Current Status of Board: ");

            System.out.println();
            myGame.printBoard (myGame.getPlayerBoard());

            // Takes the given input
            Coordinates givenCoord = makeMove(myGame);

            // If first move is wrong, gives an another try to the player
            if (currentMoves == 0) {
                while (myGame.isItMine(givenCoord.getX(), givenCoord.getY())) {
                    System.out.println("Sorry bad luck! Try again!");
                    givenCoord = makeMove(myGame);
                }
            }

            // Execute the given coordinates
            myGame.executeTurn(givenCoord.getX(), givenCoord.getY());

            // Increment the counter
            currentMoves++;

            // Check whether the game is not over
            if(!myGame.isItOver() && myGame.getMovesLeft() == 0) {
                System.out.println("You have won!");
                myGame.setOver(true);
                break;
            }
        }
    }

    /**
     * Method for taking the input from the user
     *
     * @param game
     * @return givenCoord
     */
    private static Coordinates makeMove(Minesweeper game) {
        System.out.println("Enter your move: (row column) -> ");
        // Scanner for the input from the terminal
        Scanner scanner = new Scanner(System.in);
        try {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            // Check whether the input is valid, if it is not try again
            while (!game.isValid(x, y)) {
                System.out.println("The coordinates must be in range of the board");
                x = scanner.nextInt();
                y = scanner.nextInt();
            }

            return new Coordinates(x, y);
        }
        catch (Exception e) {
            System.out.println("Enter an integer!");
            System.exit(0);
            return new Coordinates(-1, -1);
        }
    }

    /**
     * Method for taking the wanted level from the player
     *
     * @return given level
     */
    private static int chooseDifficulty() {
        System.out.println("Enter the Difficulty Level");
        System.out.println("Press 0 for BEGINNER (9 * 9 Cells and 10 Mines)");
        System.out.println("Press 1 for INTERMEDIATE (16 * 16 Cells and 40 Mines)");
        System.out.println("Press 2 for ADVANCED (24 * 24 Cells and 99 Mines)");

        // Scanner for the input from the terminal
        Scanner scanner = new Scanner(System.in);
        try {
            int givenLevel = scanner.nextInt();
            // Check whether the input is valid, if it is not try again
            while (givenLevel < 0 || givenLevel > 2) {
                System.out.println("The level number should be 0, 1 or 2! ");
                givenLevel = scanner.nextInt();
            }

            return givenLevel;
        }
        catch (Exception e) {System.out.println("Enter an integer!"); System.exit(0); return -1;}
    }
}
