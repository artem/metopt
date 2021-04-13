import java.util.function.UnaryOperator;

public class SteepestDescent {
    private static final int MAX_ITERATIONS = 3000;

    private final AbstractFunction f;
    private final double eps;

    public SteepestDescent(final AbstractFunction f, final double eps) {
        this.f = f;
        this.eps = eps;
    }

    double maxFactor(Matrix factor, Matrix product) {
        double result = 0;
        for (int i = 0; i < product.m; ++i) {
            double f1 = Math.abs(product.get(i, 0) / factor.get(i, 0));
            if (f1 > result && !Double.isInfinite(f1)) {
                result = f1;
            }
        }
        return result;
    }

    public Matrix process() throws MatrixException {
        final Trace result = new Trace(f);

        Matrix x = Matrix.mul(Matrix.sum(f.start, f.end), 0.5);
        Matrix p = f.gradient(x).invert();
        result.add(x);

        for (int i = 0; i < MAX_ITERATIONS; ++i) {
            if (p.len() < eps) {
                return x;
            }
            Matrix finalX = x;
            Matrix finalP = p;
            UnaryOperator<Double> g = z -> f.eval(Matrix.sum(finalX, Matrix.mul(finalP, z)));
            double aUpperBound = Math.max(maxFactor(p, Matrix.sum(f.start, x.invert())),
                                          maxFactor(p, Matrix.sum(f.end, x.invert())));
            double a = SingleDimensionMethods.fib(g, eps, 0, aUpperBound); // TODO
            x = Matrix.sum(x, Matrix.mul(p, a));
            p = f.gradient(x).invert();
            result.add(x);
        }

        throw new AssertionError("Processing timeout");
    }

    public static void main(String[] args)  {
        try {
            System.out.println(new SteepestDescent(new Function2(), 1e-5).process());
        } catch (MatrixException e) {
            e.printStackTrace();
        }
    }
}
