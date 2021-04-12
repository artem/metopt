import java.util.StringJoiner;

public class BrentStep extends AbstractStep {
    double l;
    double r;
    double x;
    double fx;
    double v;
    double fv;
    double w;
    double fw;
    double frac;

    public BrentStep(double l, double r, double x, double fx, double v, double fv, double w, double fw, double frac) {
        this.l = l;
        this.r = r;
        this.x = x;
        this.fx = fx;
        this.v = v;
        this.fv = fv;
        this.w = w;
        this.fw = fw;
        this.frac = frac;
    }

    @Override
    public String toLatex() {
        return new StringJoiner(" & ", "", "\\\\ \\hline")
                .add(doubleToString(l))
                .add(doubleToString(r))
                .add(doubleToString(x))
                .add(doubleToString(fx))
                .add(doubleToString(v))
                .add(doubleToString(fv))
                .add(doubleToString(w))
                .add(doubleToString(fw))
                .add(doubleToString(frac))
                .toString();
    }

    @Override
    public String toString() {
        return new StringJoiner(" ", "", "")
                .add(doubleToString(l))
                .add(doubleToString(r))
                .add(doubleToString(x))
                .add(doubleToString(fx))
                .add(doubleToString(v))
                .add(doubleToString(fv))
                .add(doubleToString(w))
                .add(doubleToString(fw))
                .add(doubleToString(frac))
                .toString();
    }
}
