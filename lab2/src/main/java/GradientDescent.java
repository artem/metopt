public class GradientDescent implements Method{
    static final int MAX_ITERATIONS = 50_000;
    private final AbstractFunction fn;
    private final double gradEps;
    private final boolean normalized;
    private double alpha;

    public GradientDescent(final AbstractFunction fn, final double gradEps, final double alpha, final boolean normalized) {
        this.fn = fn;
        this.gradEps = gradEps;
        this.alpha = alpha;
        this.normalized = normalized;
    }

    public GradientDescent(final AbstractFunction fn, final double gradEps, final double alpha) {
        this(fn, gradEps, alpha, false);
    }

    public GradientDescent(final AbstractFunction fn, final double gradEps, final double l, final double L) {
        this(fn, gradEps, 2.0 / (l + L));
    }

    public Trace process() throws MatrixException {
        final Trace result = new Trace(fn);

        Matrix x = new Matrix(fn.x0);
        Matrix gradient = fn.gradient(x);
        double fx = fn.eval(x);

        result.add(x);

        for (int i = 0; i < MAX_ITERATIONS && gradient.len() > gradEps; i++) {
            if (normalized)
                gradient.normalize();
            while (alpha != 0) {
                final Matrix y = x.add(gradient.mul(-alpha));
                double fy = fn.eval(y);
                if (fy < fx) {
                    x = y;
                    fx = fy;
                    result.add(x);
                    break;
                } else {
                    alpha /= 2;
                }
            }
            gradient = fn.gradient(x);
        }
        return result;
    }
}
