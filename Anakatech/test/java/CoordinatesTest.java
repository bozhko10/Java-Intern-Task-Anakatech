import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordinatesTest {

    @Test
    void getX() {
        Coordinates square = new Coordinates(0, 5);

        assertEquals(0, square.getX());
    }

    @Test
    void getY() {
        Coordinates square = new Coordinates(3, 15);

        assertEquals(15, square.getY());
    }
}