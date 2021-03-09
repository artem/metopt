import java.util.Locale;
import java.util.StringJoiner;

public class ParabolaStep implements ResultPart {
    private double[] a;
    private double[] x;
    private double[] f;
    private double x_;
    private double f_;

    public ParabolaStep() {}

    public ParabolaStep(double[] a, double[] x, double[] f, double x_, double f_) {
        this.a = a;
        this.x = x;
        this.f = f;
        this.x_ = x_;
        this.f_ = f_;
    }

    public void setA(double[] a) {
        this.a = a;
    }

    public void setX(double[] x) {
        this.x = x;
    }

    public void setF(double[] f) {
        this.f = f;
    }

    public void setX_(double x_) {
        this.x_ = x_;
    }

    public void setF_(double f_) {
        this.f_ = f_;
    }

    @Override
    public String toLatex() {
        return new StringJoiner(" & ", "", "\\\\ \\hline")
                .add(doubleToString(x[0]))
                .add(doubleToString(f[0]))
                .add(doubleToString(x[1]))
                .add(doubleToString(f[1]))
                .add(doubleToString(x[2]))
                .add(doubleToString(f[2]))
                .add(doubleToString(x_))
                .add(doubleToString(f_))
                .toString();
    }

    private String doubleToString(double num) {
        return String.format(Locale.ROOT, "%.7f", num);
    }

    @Override
    public String toString() {
        return new StringJoiner(" & ", "", "")
                .add(doubleToString(a[0]))
                .add(doubleToString(a[1]))
                .add(doubleToString(a[2]))
                .add(doubleToString(x[0]))
                .add(doubleToString(x[1]))
                .toString();
    }
}
