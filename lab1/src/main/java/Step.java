import java.util.StringJoiner;

public class Step extends AbstractStep {
    final double x1, x2, f1, f2, a, b;
    final Step prev;

    public Step(double a, double b, double x1, double x2, double f1, double f2, Step prev) {
        this.x1 = x1;
        this.x2 = x2;
        this.f1 = f1;
        this.f2 = f2;
        this.a = a;
        this.b = b;
        this.prev = prev;
    }

    @Override
    public String toString() {
        return x1 + " " + f1 + " " + x2 + " " + f2;
    }

    @Override
    public String toLatex() {
        return new StringJoiner(" & ", "", "\\\\ \\hline")
                .add(doubleToString(a))
                .add(doubleToString(b))
                .add(doubleToString(x1))
                .add(doubleToString(f1))
                .add(doubleToString(x2))
                .add(doubleToString(f2))
                .add(doubleToString(prev == null ? 1.0 : (b - a) / (prev.b - prev.a)))
                .toString();
    }
}