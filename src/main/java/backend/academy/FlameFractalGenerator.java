package backend.academy;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.LongAdder;

/**
 * Класс для генерации фрактального пламени.
 */
public class FlameFractalGenerator {
    private final int width;
    private final int height;
    private final int iterations;
    private final BufferedImage image;
    private final List<AffineTransformation> transformations;
    private final int symmetry;
    private final double gamma;
    private final boolean multiThreaded;
    private final int threadCount;
    private final ColorMap colorMap;

    // Гистограммы с использованием LongAdder для безопасного многопоточного обновления
    private final LongAdder[][] redHistogram;
    private final LongAdder[][] greenHistogram;
    private final LongAdder[][] blueHistogram;
    private final LongAdder[][] densityHistogram;

    public FlameFractalGenerator(int width, int height, int iterations, List<AffineTransformation> transformations,
        int symmetry, double gamma, boolean multiThreaded, int threadCount) {
        this.width = width;
        this.height = height;
        this.iterations = iterations;
        this.transformations = transformations;
        this.symmetry = symmetry;
        this.gamma = gamma;
        this.multiThreaded = multiThreaded;
        this.threadCount = threadCount > 0 ? threadCount : Runtime.getRuntime().availableProcessors();

        // Инициализируем гистограммы
        redHistogram = new LongAdder[width][height];
        greenHistogram = new LongAdder[width][height];
        blueHistogram = new LongAdder[width][height];
        densityHistogram = new LongAdder[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                redHistogram[x][y] = new LongAdder();
                greenHistogram[x][y] = new LongAdder();
                blueHistogram[x][y] = new LongAdder();
                densityHistogram[x][y] = new LongAdder();
            }
        }

        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        colorMap = new ColorMap(256);
    }

    public void generate() throws InterruptedException {
        if (multiThreaded) {
            generateMultiThreaded();
        } else {
            generateSingleThreaded();
        }
    }

    private void generateSingleThreaded() {
        Random random = new Random();
        Point p = new Point(0, 0, random.nextDouble());

        // Предварительные итерации для "прогрева"
        for (int i = 0; i < 20; i++) {
            p = transformPoint(p, random);
        }

        for (int i = 0; i < iterations; i++) {
            p = transformPoint(p, random);
            mapPoint(p);
        }

        renderImage();
    }

    private void generateMultiThreaded() throws InterruptedException {
        Thread[] threads = new Thread[threadCount];
        int iterationsPerThread = iterations / threadCount;
        int remainingIterations = iterations % threadCount;

        for (int t = 0; t < threadCount; t++) {
            final int threadIterations = iterationsPerThread + (t == 0 ? remainingIterations : 0);
            threads[t] = new Thread(() -> {
                Random random = ThreadLocalRandom.current();
                Point p = new Point(0, 0, random.nextDouble());

                // Предварительные итерации для "прогрева"
                for (int i = 0; i < 20; i++) {
                    p = transformPoint(p, random);
                }

                for (int i = 0; i < threadIterations; i++) {
                    p = transformPoint(p, random);
                    mapPoint(p);
                }
            });
            threads[t].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        renderImage();
    }

    /**
     * Применяет случайную трансформацию к точке.
     */
    private Point transformPoint(Point p, Random random) {
        double r = random.nextDouble();
        AffineTransformation transformation = transformations.get(0);
        double cumulativeProbability = 0.0;

        for (AffineTransformation t : transformations) {
            cumulativeProbability += t.getProbability();
            if (r <= cumulativeProbability) {
                transformation = t;
                break;
            }
        }

        return transformation.apply(p);
    }

    /**
     * Отображает точку на гистограммы.
     */
    private void mapPoint(Point p) {
        // Преобразуем координаты
        double dx = p.x;
        double dy = p.y;

        // Применяем симметрию
        for (int s = 0; s < symmetry; s++) {
            double angle = 2 * Math.PI * s / symmetry;
            double xs = dx * Math.cos(angle) - dy * Math.sin(angle);
            double ys = dx * Math.sin(angle) + dy * Math.cos(angle);

            int x = (int) ((xs + 1.0) * width / 2.0);
            int y = (int) ((ys + 1.0) * height / 2.0);

            if (x >= 0 && x < width && y >= 0 && y < height) {
                Color color = colorMap.getColor(p.color);

                redHistogram[x][y].add(color.getRed());
                greenHistogram[x][y].add(color.getGreen());
                blueHistogram[x][y].add(color.getBlue());
                densityHistogram[x][y].increment();
            }
        }
    }

    /**
     * Рендеринг изображения на основе гистограмм.
     */
    private void renderImage() {
        double maxDensity = 0.0;
        double[][] densityValues = new double[width][height];

        // Собираем значения плотности и находим максимальное
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double density = densityHistogram[x][y].doubleValue();
                densityValues[x][y] = density;
                if (density > maxDensity) {
                    maxDensity = density;
                }
            }
        }

        // Применяем логарифмическое масштабирование и гамма-коррекцию
        double gammaCorrection = 1.0 / gamma;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (densityValues[x][y] > 0) {
                    double density = Math.log(densityValues[x][y] + 1) / Math.log(maxDensity + 1);
                    density = Math.pow(density, gammaCorrection);

                    double red = redHistogram[x][y].doubleValue() / densityValues[x][y];
                    double green = greenHistogram[x][y].doubleValue() / densityValues[x][y];
                    double blue = blueHistogram[x][y].doubleValue() / densityValues[x][y];

                    int r = (int) (red * density);
                    int g = (int) (green * density);
                    int b = (int) (blue * density);
                    int a = 255;

                    r = Math.min(255, r);
                    g = Math.min(255, g);
                    b = Math.min(255, b);

                    int rgb = (a << 24) | (r << 16) | (g << 8) | b;
                    image.setRGB(x, y, rgb);
                } else {
                    image.setRGB(x, y, 0x00000000);
                }
            }
        }
    }
    public BufferedImage getImage() {
        return image;
    }
}
