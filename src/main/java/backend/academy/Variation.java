package backend.academy;

public class Variation {

    // Линейная вариация
    public static Point linear(Point p) {
        return new Point(p.x, p.y, p.color);
    }

    // Синусоидальная вариация
    public static Point sinusoidal(Point p) {
        return new Point(Math.sin(p.x), Math.sin(p.y), p.color);
    }

    // Сферическая вариация
    public static Point spherical(Point p) {
        double r2 = p.x * p.x + p.y * p.y + 1e-6; // Избегаем деления на ноль
        return new Point(p.x / r2, p.y / r2, p.color);
    }

    // Завихрение (Swirl)
    public static Point swirl(Point p) {
        double r2 = p.x * p.x + p.y * p.y;
        double sinr2 = Math.sin(r2);
        double cosr2 = Math.cos(r2);
        return new Point(p.x * sinr2 - p.y * cosr2, p.x * cosr2 + p.y * sinr2, p.color);
    }

    // Подкова (Horseshoe)
    public static Point horseshoe(Point p) {
        double r = Math.sqrt(p.x * p.x + p.y * p.y) + 1e-6;
        double x = (p.x - p.y) * (p.x + p.y) / r;
        double y = 2 * p.x * p.y / r;
        return new Point(x, y, p.color);
    }

    // Взрыв (Exponential)
    public static Point exponential(Point p) {
        double expx = Math.exp(p.x - 1.0);
        return new Point(expx * Math.cos(Math.PI * p.y), expx * Math.sin(Math.PI * p.y), p.color);
    }

    // Волны (Waves)
    public static Point waves(Point p) {
        double x = p.x + 0.05 * Math.sin(p.y / (0.05 * 0.05));
        double y = p.y + 0.05 * Math.sin(p.x / (0.05 * 0.05));
        return new Point(x, y, p.color);
    }

    // Джулия (Julia)
    public static Point julia(Point p) {
        double r = Math.sqrt(p.x * p.x + p.y * p.y) + 1e-6;
        double theta = Math.atan2(p.y, p.x) / 2.0;
        double sinTheta = Math.sin(theta);
        double cosTheta = Math.cos(theta);
        return new Point(Math.sqrt(r) * cosTheta, Math.sqrt(r) * sinTheta, p.color);
    }

    // Получение вариации по имени
    public static VariationFunction getVariationByName(String name) {
        switch (name.toLowerCase()) {
            case "linear":
                return Variation::linear;
            case "sinusoidal":
                return Variation::sinusoidal;
            case "spherical":
                return Variation::spherical;
            case "swirl":
                return Variation::swirl;
            case "horseshoe":
                return Variation::horseshoe;
            case "exponential":
                return Variation::exponential;
            case "waves":
                return Variation::waves;
            case "julia":
                return Variation::julia;
            default:
                throw new IllegalArgumentException("Unknown variation: " + name);
        }
    }
}
