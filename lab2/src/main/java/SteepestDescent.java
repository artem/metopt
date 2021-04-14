import java.util.function.UnaryOperator;

public class SteepestDescent {
    static final int MAX_ITERATIONS = 3000;

    private final AbstractFunction f;
    private final double eps;

    public SteepestDescent(final AbstractFunction f, final double eps) {
        this.f = f;
        this.eps = eps;
    }

    double maxFactor(Matrix factor, Matrix product) {
        double result = 0;
        for (int i = 0; i < product.m; ++i) {
            double factor1 = factor.get(i, 0);
            double prod = product.get(i, 0);
            if (factor1 > 0.) {
                double factor2 = Math.abs(prod / factor1);
                if (factor2 > result && !Double.isInfinite(factor2)) {
                    result = factor2;
                }
            }
        }
        return result;
    }

    public Trace process() throws MatrixException {
        final Trace result = new Trace(f);

        Matrix x = Matrix.mul(Matrix.sum(f.start, f.end), 0.5);
        Matrix p = f.gradient(x).invert();
        result.add(x);

        for (int i = 0; i < MAX_ITERATIONS; ++i) {
            if (p.len() < eps) {
                return result;
            }
            Matrix finalX = x;
            Matrix finalP = p;
            UnaryOperator<Double> g = z -> f.eval(Matrix.sum(finalX, Matrix.mul(finalP, z)));
            double aUpperBound = Math.max(maxFactor(p.invert(), Matrix.sum(f.start, x.invert()).invert()),
                                          maxFactor(p, Matrix.sum(f.end, x.invert())));
            double a = SingleDimensionMethods.fib(g, eps, 0, aUpperBound);
            x = Matrix.sum(x, Matrix.mul(p, a));
            p = f.gradient(x).invert();
            result.add(x);
        }

        System.err.println("Computation timeout");
        return result;
    }
}
