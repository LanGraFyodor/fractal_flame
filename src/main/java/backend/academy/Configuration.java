package backend.academy;


import java.util.List;

public class Configuration {
    private int width;
    private int height;
    private int iterations;
    private int symmetry;
    private double gamma;
    private boolean multiThreaded;
    private int threadCount;
    private String outputFile;
    private List<TransformationConfig> transformations;

    public Configuration() {
    }

    // Геттеры и сеттеры для всех полей

    public int getWidth() { return width; }
    public void setWidth(int width) { this.width = width; }

    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }

    public int getIterations() { return iterations; }
    public void setIterations(int iterations) { this.iterations = iterations; }

    public int getSymmetry() { return symmetry; }
    public void setSymmetry(int symmetry) { this.symmetry = symmetry; }

    public double getGamma() { return gamma; }
    public void setGamma(double gamma) { this.gamma = gamma; }

    public boolean isMultiThreaded() { return multiThreaded; }
    public void setMultiThreaded(boolean multiThreaded) { this.multiThreaded = multiThreaded; }

    public int getThreadCount() { return threadCount; }
    public void setThreadCount(int threadCount) { this.threadCount = threadCount; }

    public String getOutputFile() { return outputFile; }
    public void setOutputFile(String outputFile) { this.outputFile = outputFile; }

    public List<TransformationConfig> getTransformations() { return transformations; }
    public void setTransformations(List<TransformationConfig> transformations) { this.transformations = transformations; }
}
