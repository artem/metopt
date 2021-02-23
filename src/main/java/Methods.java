public class Methods {

    public static Result goldenRatio(Function fun, double from, double to, double epsLim) {
        final Result res = new Result();
        final double Tau = (Math.sqrt(5) - 1) / 2;
        double[] x = new double[]{from + (1 - Tau) * (to - from), from + Tau * (to - from)};
        double[] f = new double[]{fun.eval(x[0]), fun.eval(x[1])};
        double[] ab = new double[]{from, to};
        for (double eps = (ab[1] - ab[0]) / 2; eps > epsLim; eps *= Tau) {
            double b = ab[1];
            int le = f[0] <= f[1] ? 1 : 0;
            ab[le] = x[le];
            x[le] = x[1 - le];
            x[1 - le] = b - Tau * (b - ab[0]);
            f[le] = f[le - 1];
            f[1 - le] = fun.eval(x[1 - le]);
            eps *= Tau;
            res.addStep(x[0], x[1], f[0], f[1]);
        }
        double x_ = (x[0] + x[1]) / 2;
        res.setResult(x_, fun.eval(x_));
        return res;
    }
}
