package backend.academy;

/**
 * Функциональный интерфейс для вариаций.
 */
@FunctionalInterface
public interface VariationFunction {
    Point apply(Point p);
}
