public class ParabolaStep implements ResultPart {
    final double a1;
    final double a2;
    final double a3;
    final double x1;
    final double x2;

    public ParabolaStep(double a1, double a2, double a3, double x1, double x2) {
        this.a1 = a1;
        this.a2 = a2;
        this.a3 = a3;
        this.x1 = x1;
        this.x2 = x2;
    }

    public ParabolaStep(double[] a, double x1, double x2) {
        this(a[0], a[1], a[2], x1, x2);
    }

    @Override
    public String toString() {
        return a1 + " " + a2 + " " + a3 + " " + x1 + " " + x2;
    }
}
