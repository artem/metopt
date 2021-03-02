public class ParabolaStep extends Step {
    final double x3;
    final double f3;

    public ParabolaStep(double x1, double x2, double x3, double f1, double f2, double f3) {
        super(x1, x2, f1, f2);
        this.x3 = x3;
        this.f3 = f3;
    }

    public ParabolaStep(double[] x, double[] f) {
        this(x[0], x[1], x[2], f[0], f[1], f[2]);
    }

    @Override
    public String toString() {
        return super.toString() + " " + x3 + " " + f3;
    }
}
