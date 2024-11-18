import backend.academy.ColorMap;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для класса ColorMap.
 */
public class ColorMapTest {

    @Test
    void testColorMapGeneration() {
        int size = 256;
        ColorMap colorMap = new ColorMap(size);

        for (double value = 0.0; value <= 1.0; value += 0.01) {
            Color color = colorMap.getColor(value);
            assertNotNull(color);
        }
    }

    @Test
    void testColorMapBounds() {
        int size = 256;
        ColorMap colorMap = new ColorMap(size);

        // Проверка для значений ниже 0
        Color color = colorMap.getColor(-0.1);
        assertNotNull(color);

        // Проверка для значений выше 1
        color = colorMap.getColor(1.1);
        assertNotNull(color);
    }
}
