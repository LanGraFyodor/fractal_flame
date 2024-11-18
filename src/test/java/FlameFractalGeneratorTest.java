import backend.academy.AffineTransformation;
import backend.academy.FlameFractalGenerator;
import backend.academy.Variation;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Collections;

/**
 * Тесты для класса FlameFractalGenerator.
 */
public class FlameFractalGeneratorTest {

    @Test
    void testGenerateSingleThreaded() {
        // Простая конфигурация
        int width = 100;
        int height = 100;
        int iterations = 10000;

        AffineTransformation at = new AffineTransformation(
            0.5, 0.0, 0.0,
            0.0, 0.5, 0.0,
            Collections.singletonList(Variation::linear),
            Collections.singletonList(1.0),
            1.0
        );

        FlameFractalGenerator generator = new FlameFractalGenerator(
            width,
            height,
            iterations,
            Collections.singletonList(at),
            1,
            2.2,
            false,
            1
        );

        assertDoesNotThrow(() -> {
            generator.generate();
            BufferedImage image = generator.getImage();
            assertNotNull(image);
            assertEquals(width, image.getWidth());
            assertEquals(height, image.getHeight());
        });
    }

    @Test
    void testGenerateMultiThreaded() {
        // Простая конфигурация
        int width = 100;
        int height = 100;
        int iterations = 10000;
        int threadCount = 4;

        AffineTransformation at = new AffineTransformation(
            0.5, 0.0, 0.0,
            0.0, 0.5, 0.0,
            Collections.singletonList(Variation::linear),
            Collections.singletonList(1.0),
            1.0
        );

        FlameFractalGenerator generator = new FlameFractalGenerator(
            width,
            height,
            iterations,
            Collections.singletonList(at),
            1,
            2.2,
            true,
            threadCount
        );

        assertDoesNotThrow(() -> {
            generator.generate();
            BufferedImage image = generator.getImage();
            assertNotNull(image);
            assertEquals(width, image.getWidth());
            assertEquals(height, image.getHeight());
        });
    }
}
