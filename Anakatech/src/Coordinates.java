/**
 * Coordinates object
 *
 * It contains the X and Y of a cell
 */
public class Coordinates {
    private final int x;
    private final int y;

    /**
     * Constructor
     *
     * It's set the X and Y
     *
     * @param x int
     * @param y int
     */
    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Method for getting X
     *
     * @return int
     */
    public int getX() {
        return x;
    }

    /**
     * Method for getting Y
     *
     * @return int
     */
    public int getY() {
        return y;
    }
}
