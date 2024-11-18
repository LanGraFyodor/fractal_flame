package backend.academy;

/**
 * Интерфейс для преобразований.
 */
public interface Transformation {
    Point apply(Point p);

    double getProbability();
}
