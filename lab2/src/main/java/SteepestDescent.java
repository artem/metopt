import java.util.List;

public class SteepestDescent {
    private final AbstractFunction f;
    private final double eps;

    public SteepestDescent(AbstractFunction f, double eps) {
        this.f = f;
        this.eps = eps;
    }

    private double goldenRatio(AbstractFunction fun, double epsLim, double from, double to) throws MatrixException {
        final double Tau = (Math.sqrt(5) - 1) / 2;
        double I = (to - from) * Tau;
        double[] x = new double[]{to - I, from + I};
        double[] f = new double[]{
                fun.eval(new Matrix(List.of(List.of(x[0])))),
                fun.eval(new Matrix(List.of(List.of(x[1]))))};
        double[] ab = new double[]{from, to};
        for (double eps = (ab[1] - ab[0]) / 2; eps > epsLim; eps *= Tau) {
            I *= Tau;
            int le = f[0] <= f[1] ? 1 : 0;
            ab[le] = x[le];
            x[le] = x[1 - le];
            x[1 - le] = ab[le] + (le == 0 ? I : -I);
            f[le] = f[1 - le];
            f[1 - le] = fun.eval(new Matrix(List.of(List.of(x[1 - le]))));
        }
        return (ab[0] + ab[1]) / 2;
    }

    public Matrix process() throws MatrixException {
        int iterations = 1;
        Matrix x = Matrix.mul(Matrix.sum(f.start, f.end), 0.5);
        Matrix p = f.gradient(x).invert();
        while (p.len() > eps) {
            Matrix finalX = x;
            Matrix finalP = p;
            AbstractFunction g = new AbstractFunction() {
                @Override
                double eval(Matrix m) throws MatrixException {
                    return f.eval(Matrix.sum(finalX, Matrix.mul(finalP, m.get(0, 0))));
                }
            };
            double a = goldenRatio(g, eps, 0, 1000000);
            x = Matrix.sum(x, Matrix.mul(p, a));
            p = f.gradient(x).invert();
            ++iterations;
        }
        System.out.println("steepest descent took " + iterations + " iterations.");
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
