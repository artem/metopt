public abstract class AbstractFunction {
    protected final double l, r;

    protected AbstractFunction() {
        this.l = Double.NEGATIVE_INFINITY;
        this.r = Double.POSITIVE_INFINITY;
    }

    protected AbstractFunction(double l, double r) {
        this.l = l;
        this.r = r;
    }

    public final double eval(double x) {
        if (!inBounds(x)) {
            throw new IllegalArgumentException("x is out of bounds");
        }
        return evalImpl(x);
    }

    private boolean inBounds(double x) {
        return x >= l && x <= r;
    }

    protected abstract double evalImpl(double x);
}
