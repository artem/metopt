public class GradientDescent implements Method{
    static final int MAX_ITERATIONS = 5000;
    private final AbstractFunction fn;
    private final double gradEps;
    private double lambda;
    private boolean normalGrad;

    public GradientDescent(final AbstractFunction fn, final double gradEps, final double lambda, final boolean normalGrad) {
        this.fn = fn;
        this.gradEps = gradEps;
    }

    public Trace process() throws MatrixException {
        final Trace result = new Trace(fn);

        Matrix x = fn.x0;
        double fx = fn.eval(x);

        result.add(x);

        for (int i = 0; i < MAX_ITERATIONS; i++) {
            Matrix gradient = fn.gradient(x);
            if (gradient.len() < gradEps) {
                return result;
            }
            if (normalGrad) { // замечание к алгоритму, как возможный вариант.
                gradient.normalize();
            }

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
        }
        return result;
    }
}
