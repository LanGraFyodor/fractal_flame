package backend.academy;

import java.awt.Color;

/**
 * Класс для управления цветовой палитрой.
 */
public class ColorMap {
    private final Color[] colors;

    public ColorMap(int size) {
        colors = new Color[size];
        generateColors();
    }

    /**
     * Генерирует яркие цвета для палитры.
     */
    private void generateColors() {
        for (int i = 0; i < colors.length; i++) {
            float hue = (float) i / colors.length;
            // Используем полную яркость и насыщенность для ярких цветов
            colors[i] = Color.getHSBColor(hue, 1.0f, 1.0f);
        }
    }

    /**
     * Возвращает цвет по значению (от 0 до 1).
     */
    public Color getColor(double value) {
        int index = (int) (value * (colors.length - 1));
        index = Math.max(0, Math.min(colors.length - 1, index));
        return colors[index];
    }
}
