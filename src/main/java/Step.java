public class Step implements ResultPart {
    final double x1, x2, f1, f2;

    public Step(double x1, double x2, double f1, double f2) {
        this.x1 = x1;
        this.x2 = x2;
        this.f1 = f1;
        this.f2 = f2;
    }

    @Override
    public String toString() {
        return x1 + " " + f1 + " " + x2 + " " + f2;
    }
}