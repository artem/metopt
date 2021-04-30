public class GradientDescent implements Method{
    static final int MAX_ITERATIONS = 50_000;
    private final AbstractFunction fn;
    private final double gradEps;
    private double lambda;

    public GradientDescent(final AbstractFunction fn, final double gradEps, final double lambda) {
        this.fn = fn;
        this.gradEps = gradEps;
        this.lambda = lambda;
    }

    public GradientDescent(final AbstractFunction fn, final double gradEps, final double l, final double L) {
        this(fn, gradEps, 2.0 / (l + L));
    }

    public Trace process() throws MatrixException {
        final Trace result = new Trace(fn);

        Matrix x = fn.x0;
        Matrix gradient = fn.gradient(x);
        double fx = fn.eval(x);

        result.add(x);

        for (int i = 0; i < MAX_ITERATIONS && gradient.len() > gradEps; i++) {
            while (lambda != 0) {
                final Matrix y = x.add(gradient.mul(-lambda));
                double fy = fn.eval(y);
                if (fy < fx) {
                    x = y;
                    fx = fy;
                    result.add(x);
                    break;
                } else {
                    lambda /= 2;
                }
            }
            gradient = fn.gradient(x);
        }
        return result;
    }
}
