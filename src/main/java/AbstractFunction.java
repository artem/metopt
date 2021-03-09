public abstract class AbstractFunction {
    protected final double l, r;
    int calculations;

    protected AbstractFunction() {
        this.l = Double.NEGATIVE_INFINITY;
        this.r = Double.POSITIVE_INFINITY;
    }

    protected AbstractFunction(double l, double r) {
        this.l = l;
        this.r = r;
    }

    public final double evalNoStat(double x) {
        if (!inBounds(x)) {
            System.err.format("%f is not in [%f;%f]", x, l, r);
            throw new IllegalArgumentException("x is out of bounds");
        }

        return evalImpl(x);
    }

    public final double eval(double x) {
        calculations++;
        return evalNoStat(x);
    }

    private boolean inBounds(double x) {
        return x >= l && x <= r;
    }

    protected abstract double evalImpl(double x);
}
