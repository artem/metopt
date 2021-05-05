import java.util.function.UnaryOperator;

public class SteepestDescent implements Method {
    static final int MAX_ITERATIONS = 100_000;

    private final AbstractFunction f;
    private final double eps;

    public SteepestDescent(final AbstractFunction f, final double eps) {
        this.f = f;
        this.eps = eps;
    }
    private double findMin(UnaryOperator<Double> g) {
        double t = 1.0;
        double pt = 0.0;
        double ppt = pt;
        double gt = g.apply(t);
        double pgt = g.apply(pt);
        while (gt <= pgt) {
            ppt = pt;
            pt = t;
            t *= 2;
            pgt = gt;
            gt = g.apply(t);
        }
        return SingleDimensionMethods.fib(g, eps, ppt, t);
    }

    public Trace process() throws MatrixException {
        final Trace result = new Trace(f);

        Matrix x = new Matrix(f.x0);
        Matrix p = f.gradient(x).negate();
        result.add(new Matrix(x));

        for (int i = 0; i < MAX_ITERATIONS && p.len() > eps; ++i) {
            Matrix finalP = p;
            double a = findMin(z -> f.eval(x.add(finalP.mul(z))));
            if (Double.isNaN(a) || Double.isInfinite(a)) {
                System.out.println("NaN or Inf =)");
            }
            x.addBy(p.mulBy(a)); // we don't need p anymore, so we can mulBy it
            p = f.gradient(x).negate();
//            result.add(new Matrix(x));
            result.add(null);
        }

        return result;
    }
}
