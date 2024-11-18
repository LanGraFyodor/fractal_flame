package backend.academy;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Основной класс для запуска генерации фрактала.
 */
public class Main {
    public static void main(String[] args) {
        String configFile = "config.json";

        try {
            // Читаем конфигурацию из JSON
            ObjectMapper mapper = new ObjectMapper();
            Configuration config = mapper.readValue(new File(configFile), Configuration.class);

            // Создаем трансформации из конфигурации
            List<AffineTransformation> transformations = createTransformationsFromConfig(config.getTransformations());

            // Создание генератора для однопоточной версии
            FlameFractalGenerator singleThreadedGenerator = new FlameFractalGenerator(
                config.getWidth(),
                config.getHeight(),
                config.getIterations(),
                transformations,
                config.getSymmetry(),
                config.getGamma(),
                false,
                1
            );

            System.out.println("Начинаем генерацию фрактала в однопоточном режиме...");
            long startTimeSingle = System.currentTimeMillis();

            singleThreadedGenerator.generate();

            long endTimeSingle = System.currentTimeMillis();
            double totalTimeSecondsSingle = (endTimeSingle - startTimeSingle) / 1000.0;
            System.out.println("Однопоточная генерация завершена за " + totalTimeSecondsSingle + " секунд.");

            // Сохраняем изображение однопоточной версии
            BufferedImage imageSingle = singleThreadedGenerator.getImage();
            String outputFileSingle = "single_thread_" + config.getOutputFile();
            ImageIO.write(imageSingle, "PNG", new File(outputFileSingle));
            System.out.println("Однопоточное изображение сохранено как " + outputFileSingle);

            // Создание генератора для многопоточной версии
            FlameFractalGenerator multiThreadedGenerator = new FlameFractalGenerator(
                config.getWidth(),
                config.getHeight(),
                config.getIterations(),
                transformations,
                config.getSymmetry(),
                config.getGamma(),
                true,
                config.getThreadCount()
            );

            System.out.println("Начинаем генерацию фрактала в многопоточном режиме...");
            long startTimeMulti = System.currentTimeMillis();

            multiThreadedGenerator.generate();

            long endTimeMulti = System.currentTimeMillis();
            double totalTimeSecondsMulti = (endTimeMulti - startTimeMulti) / 1000.0;
            System.out.println("Многопоточная генерация завершена за " + totalTimeSecondsMulti + " секунд.");

            BufferedImage imageMulti = multiThreadedGenerator.getImage();
            String outputFileMulti = "multi_thread_" + config.getOutputFile();
            ImageIO.write(imageMulti, "PNG", new File(outputFileMulti));
            System.out.println("Многопоточное изображение сохранено как " + outputFileMulti);

            double speedup = totalTimeSecondsSingle / totalTimeSecondsMulti;
            System.out.println(String.format("Ускорение при использовании многопоточности: %.2f раз(а).", speedup));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<AffineTransformation> createTransformationsFromConfig(List<TransformationConfig> configs) {
        List<AffineTransformation> transformations = new ArrayList<>();
        for (TransformationConfig tc : configs) {
            List<VariationFunction> variationFunctions = new ArrayList<>();
            List<Double> weights = new ArrayList<>();
            for (VariationConfig vc : tc.getVariations()) {
                VariationFunction vf = Variation.getVariationByName(vc.getName());
                variationFunctions.add(vf);
                weights.add(vc.getWeight());
            }
            AffineTransformation at = new AffineTransformation(
                tc.getA(), tc.getB(), tc.getC(),
                tc.getD(), tc.getE(), tc.getF(),
                variationFunctions, weights,
                tc.getProbability()
            );
            transformations.add(at);
        }
        return transformations;
    }
}
