package backend.academy;

import java.util.List;

/**
 * Класс для аффинного преобразования с вариациями.
 */
public class AffineTransformation implements Transformation {
    private final double a, b, c, d, e, f;
    private final List<VariationFunction> variations;
    private final List<Double> weights;
    private final double probability;

    public AffineTransformation(double a, double b, double c, double d, double e, double f,
        List<VariationFunction> variations, List<Double> weights, double probability) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
        this.variations = variations;
        this.weights = weights;
        this.probability = probability;
    }

    @Override
    public Point apply(Point p) {
        // Аффинное преобразование
        double x = a * p.x + b * p.y + c;
        double y = d * p.x + e * p.y + f;
        Point result = new Point(x, y, p.color);

        // Применяем вариации с заданными весами
        Point vPoint = new Point(0, 0, p.color);
        double totalWeight = 0;
        for (int i = 0; i < variations.size(); i++) {
            double weight = weights.get(i);
            Point vp = variations.get(i).apply(result);
            vPoint.x += weight * vp.x;
            vPoint.y += weight * vp.y;
            totalWeight += weight;
        }
        vPoint.x /= totalWeight;
        vPoint.y /= totalWeight;

        // Обновляем цвет
        vPoint.color = (p.color + probability) / 2.0;
        return vPoint;
    }

    @Override
    public double getProbability() {
        return probability;
    }
}
