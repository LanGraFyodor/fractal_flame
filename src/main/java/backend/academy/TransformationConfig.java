package backend.academy;

import java.util.List;

public class TransformationConfig {
    private double a;
    private double b;
    private double c;
    private double d;
    private double e;
    private double f;
    private List<VariationConfig> variations;
    private double probability;

    public TransformationConfig() {
    }

    public double getA() { return a; }
    public void setA(double a) { this.a = a; }

    public double getB() { return b; }
    public void setB(double b) { this.b = b; }

    public double getC() { return c; }
    public void setC(double c) { this.c = c; }

    public double getD() { return d; }
    public void setD(double d) { this.d = d; }

    public double getE() { return e; }
    public void setE(double e) { this.e = e; }

    public double getF() { return f; }
    public void setF(double f) { this.f = f; }

    public List<VariationConfig> getVariations() { return variations; }
    public void setVariations(List<VariationConfig> variations) { this.variations = variations; }

    public double getProbability() { return probability; }
    public void setProbability(double probability) { this.probability = probability; }
}
