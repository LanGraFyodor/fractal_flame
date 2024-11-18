import backend.academy.Point;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для класса Point.
 */
public class PointTest {

    @Test
    void testPointInitialization() {
        double x = 1.0;
        double y = 2.0;
        double color = 0.5;

        Point point = new Point(x, y, color);

        assertEquals(x, point.x, 1e-10);
        assertEquals(y, point.y, 1e-10);
        assertEquals(color, point.color, 1e-10);
    }
}
