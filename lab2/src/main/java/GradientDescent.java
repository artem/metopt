public class GradientDescent {
    private static final int MAX_ITERATIONS = 5000;
    private final AbstractFunction fn;
    private final double gradEps;

    public GradientDescent(final AbstractFunction fn, final double gradEps) {
        this.fn = fn;
        this.gradEps = gradEps;
    }

    // не тестировал
    public Trace process(double lambda, final boolean normalGrad) throws MatrixException {
        final Trace result = new Trace(fn);

        Matrix x = Matrix.mul(Matrix.sum(fn.start, fn.end), .5);
        double fx = fn.eval(x);

        result.add(x);

        for (int i = 0; i < MAX_ITERATIONS; i++) {
            Matrix gradient = fn.gradient(x);
            if (gradient.len() < gradEps) {
                return result;
            }
            if (normalGrad) { // замечание к алгоритму, как возможный вариант.
                gradient = Matrix.mul(gradient, 1 / gradient.len());
            }

            while (true) {
                final Matrix y = Matrix.sum(x, Matrix.mul(gradient, -lambda));
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
        }

        return result;
    }
}
