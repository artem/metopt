import java.util.function.UnaryOperator;

public class SteepestDescent implements Method {
    static final int MAX_ITERATIONS = 25_000;

    private final AbstractFunction f;
    private final double eps;
    private final double aMax;

    public SteepestDescent(final AbstractFunction f, final double eps) {
        this(f, eps, 1e-9);
    }

    public SteepestDescent(final AbstractFunction f, final double eps, final double L) {
        this.f = f;
        this.eps = eps;
        this.aMax = 2 / L;
    }

    public Trace process() throws MatrixException {
        final Trace result = new Trace(f);

        Matrix x = f.x0;
        Matrix p = f.gradient(x).negate();
        result.add(new Matrix(x));

        for (int i = 0; i < MAX_ITERATIONS; ++i) {
            if (p.len() < eps) {
                return result;
            }
            Matrix finalP = p;
            UnaryOperator<Double> g = z -> f.eval(x.add(finalP.mul(z)));
            double a = SingleDimensionMethods.goldenRatio(g, eps, 0, aMax);
            x.addBy(p.mul(a));
            p = f.gradient(x).negate();
            result.add(new Matrix(x));
        }

        return result;
    }
}
