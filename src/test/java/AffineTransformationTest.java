import backend.academy.AffineTransformation;
import backend.academy.Point;
import backend.academy.Variation;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для класса AffineTransformation.
 */
public class AffineTransformationTest {

    @Test
    void testApplyWithSingleVariation() {
        AffineTransformation at = new AffineTransformation(
            1.0, 0.0, 1.0, // a, b, c
            0.0, 1.0, 1.0, // d, e, f
            Collections.singletonList(Variation::linear),
            Collections.singletonList(1.0),
            0.5 // probability
        );

        Point p = new Point(1.0, 1.0, 0.5);
        Point result = at.apply(p);

        // Аффинное преобразование
        double xAffine = 1.0 * p.x + 0.0 * p.y + 1.0;
        double yAffine = 0.0 * p.x + 1.0 * p.y + 1.0;

        // Применение линейной вариации
        double xExpected = xAffine;
        double yExpected = yAffine;

        assertEquals(xExpected, result.x, 1e-6);
        assertEquals(yExpected, result.y, 1e-6);

        // Проверяем обновление цвета
        double colorExpected = (p.color + 0.5) / 2.0;
        assertEquals(colorExpected, result.color, 1e-6);
    }

    @Test
    void testApplyWithMultipleVariations() {
        AffineTransformation at = new AffineTransformation(
            1.0, 0.0, 0.0, // a, b, c
            0.0, 1.0, 0.0, // d, e, f
            Arrays.asList(Variation::sinusoidal, Variation::spherical),
            Arrays.asList(0.6, 0.4),
            0.8 // probability
        );

        Point p = new Point(Math.PI / 2, Math.PI / 2, 0.4);
        Point result = at.apply(p);

        // Аффинное преобразование
        double xAffine = p.x;
        double yAffine = p.y;

        // Применение вариаций
        double totalWeight = 0.6 + 0.4;
        double xVariations = (0.6 * Variation.sinusoidal(new Point(xAffine, yAffine, p.color)).x +
            0.4 * Variation.spherical(new Point(xAffine, yAffine, p.color)).x) / totalWeight;

        double yVariations = (0.6 * Variation.sinusoidal(new Point(xAffine, yAffine, p.color)).y +
            0.4 * Variation.spherical(new Point(xAffine, yAffine, p.color)).y) / totalWeight;

        assertEquals(xVariations, result.x, 1e-6);
        assertEquals(yVariations, result.y, 1e-6);

        // Проверяем обновление цвета
        double colorExpected = (p.color + 0.8) / 2.0;
        assertEquals(colorExpected, result.color, 1e-6);
    }

    @Test
    void testGetProbability() {
        AffineTransformation at = new AffineTransformation(
            0.0, 0.0, 0.0,
            0.0, 0.0, 0.0,
            Collections.emptyList(),
            Collections.emptyList(),
            0.25
        );

        assertEquals(0.25, at.getProbability(), 1e-10);
    }
}
