import java.util.function.UnaryOperator;

public class SteepestDescent {
    static final int MAX_ITERATIONS = 5000;

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
        Matrix p = f.gradient(x).invert();
        result.add(x);

        for (int i = 0; i < MAX_ITERATIONS; ++i) {
            if (p.len() < eps) {
                return result;
            }
            Matrix finalX = x;
            Matrix finalP = p;
            UnaryOperator<Double> g = z -> f.eval(Matrix.sum(finalX, Matrix.mul(finalP, z)));
            double a = SingleDimensionMethods.goldenRatio(g, eps, 0, aMax);
            x = Matrix.sum(x, Matrix.mul(p, a));
            p = f.gradient(x).invert();
            result.add(x);
        }

        System.err.println("Computation timeout");
        return result;
    }

    public static void main(String[] args) {
        System.out.println(new SteepestDescent(new Function1(), 1e-5, 254).process().getSteps().size());
    }
}
