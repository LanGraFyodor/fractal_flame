import backend.academy.Point;
import backend.academy.Variation;
import backend.academy.VariationFunction;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для класса Variation.
 */
public class VariationTest {

    @Test
    void testLinearVariation() {
        Point p = new Point(1.0, 2.0, 0.5);
        Point result = Variation.linear(p);

        assertEquals(p.x, result.x, 1e-10);
        assertEquals(p.y, result.y, 1e-10);
        assertEquals(p.color, result.color, 1e-10);
    }

    @Test
    void testSinusoidalVariation() {
        Point p = new Point(Math.PI / 2, Math.PI, 0.5);
        Point result = Variation.sinusoidal(p);

        assertEquals(Math.sin(p.x), result.x, 1e-10);
        assertEquals(Math.sin(p.y), result.y, 1e-10);
        assertEquals(p.color, result.color, 1e-10);
    }

    @Test
    void testSphericalVariation() {
        Point p = new Point(1.0, 1.0, 0.5);
        Point result = Variation.spherical(p);

        double r2 = p.x * p.x + p.y * p.y + 1e-6;
        assertEquals(p.x / r2, result.x, 1e-6);
        assertEquals(p.y / r2, result.y, 1e-6);
        assertEquals(p.color, result.color, 1e-10);
    }

    @Test
    void testSwirlVariation() {
        Point p = new Point(1.0, 0.0, 0.5);
        Point result = Variation.swirl(p);

        double r2 = p.x * p.x + p.y * p.y;
        double sinr2 = Math.sin(r2);
        double cosr2 = Math.cos(r2);

        assertEquals(p.x * sinr2 - p.y * cosr2, result.x, 1e-6);
        assertEquals(p.x * cosr2 + p.y * sinr2, result.y, 1e-6);
        assertEquals(p.color, result.color, 1e-10);
    }

    @Test
    void testHorseshoeVariation() {
        Point p = new Point(1.0, 1.0, 0.5);
        Point result = Variation.horseshoe(p);

        double r = Math.sqrt(p.x * p.x + p.y * p.y) + 1e-6;
        double xExpected = (p.x - p.y) * (p.x + p.y) / r;
        double yExpected = 2 * p.x * p.y / r;

        assertEquals(xExpected, result.x, 1e-6);
        assertEquals(yExpected, result.y, 1e-6);
        assertEquals(p.color, result.color, 1e-10);
    }

    @Test
    void testExponentialVariation() {
        Point p = new Point(1.0, 1.0, 0.5);
        Point result = Variation.exponential(p);

        double expx = Math.exp(p.x - 1.0);
        double xExpected = expx * Math.cos(Math.PI * p.y);
        double yExpected = expx * Math.sin(Math.PI * p.y);

        assertEquals(xExpected, result.x, 1e-6);
        assertEquals(yExpected, result.y, 1e-6);
        assertEquals(p.color, result.color, 1e-10);
    }

    @Test
    void testWavesVariation() {
        Point p = new Point(1.0, 2.0, 0.5);
        double b = 0.05;
        double c = 0.05;
        Point result = Variation.waves(p);

        double xExpected = p.x + b * Math.sin(p.y / (c * c));
        double yExpected = p.y + b * Math.sin(p.x / (c * c));

        assertEquals(xExpected, result.x, 1e-6);
        assertEquals(yExpected, result.y, 1e-6);
        assertEquals(p.color, result.color, 1e-10);
    }

    @Test
    void testJuliaVariation() {
        Point p = new Point(1.0, 1.0, 0.5);
        Point result = Variation.julia(p);

        double r = Math.sqrt(p.x * p.x + p.y * p.y) + 1e-6;
        double theta = Math.atan2(p.y, p.x) / 2.0;
        double sinTheta = Math.sin(theta);
        double cosTheta = Math.cos(theta);
        double xExpected = Math.sqrt(r) * cosTheta;
        double yExpected = Math.sqrt(r) * sinTheta;

        assertEquals(xExpected, result.x, 1e-6);
        assertEquals(yExpected, result.y, 1e-6);
        assertEquals(p.color, result.color, 1e-10);
    }
}
