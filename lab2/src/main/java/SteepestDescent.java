import java.util.function.UnaryOperator;

public class SteepestDescent {
    private final AbstractFunction f;
    private final double eps;

    public SteepestDescent(final AbstractFunction f, final double eps) {
        this.f = f;
        this.eps = eps;
    }

    public Matrix process() throws MatrixException {
        final Trace result = new Trace(f);

        Matrix x = Matrix.mul(Matrix.sum(f.start, f.end), 0.5);
        Matrix p = f.gradient(x).invert();
        result.add(x);

        while (p.len() > eps) {
            Matrix finalX = x;
            Matrix finalP = p;
            UnaryOperator<Double> g1 = z -> f.eval(Matrix.sum(finalX, Matrix.mul(finalP, z)));
            double a = SingleDimensionMethods.parabola(g1, eps, 0, 1000000); // TODO
            x = Matrix.sum(x, Matrix.mul(p, a));
            p = f.gradient(x).invert();
            result.add(x);
        }
        System.out.println("steepest descent took " + result.getSteps().size() + " iterations.");
        return x;
    }

    public static void main(String[] args)  {
        try {
            System.out.println(new SteepestDescent(new Function2(), 1e-5).process());
        } catch (MatrixException e) {
            e.printStackTrace();
        }
    }
}
