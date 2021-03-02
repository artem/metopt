public class Methods {

    public static Result goldenRatio(AbstractFunction fun, double epsLim) {
        double from = fun.l;
        double to = fun.r;
        final Result res = new Result();
        final double Tau = (Math.sqrt(5) - 1) / 2;
        double I = (to - from) * Tau;
        double[] x = new double[]{to - I, from + I};
        double[] f = new double[]{fun.eval(x[0]), fun.eval(x[1])};
        double[] ab = new double[]{from, to};
        for (double eps = (ab[1] - ab[0]) / 2; eps > epsLim; eps *= Tau) {
            I *= Tau;
            int le = f[0] <= f[1] ? 1 : 0;
            ab[le] = x[le];
            x[le] = x[1 - le];
            x[1 - le] = ab[le] + (le == 0 ? I : -I);
            f[le] = f[1 - le];
            f[1 - le] = fun.eval(x[1 - le]);
            res.addStep(x[0], x[1], f[0], f[1]);
        }
        double x_ = (ab[0] + ab[1]) / 2;
        res.setResult(x_, fun.eval(x_));
        return res;
    }

    public static Result dichotomy(AbstractFunction fun, double epsLim) {
        double from = fun.l;
        double to = fun.r;
        final Result res = new Result();
        double d = (to-from)/3;
        double[] x = new double[]{(from + to - d) / 2, (from + to + d) / 2};
        double[] f;
        double[] ab = new double[]{from, to};
        res.addStep(x[0], x[1], fun.eval(x[0]), fun.eval(x[1]));
        for (double eps = (ab[1] - ab[0]) / 2; eps > epsLim; eps = (ab[1] - ab[0]) / 2) {
            d = (ab[1]-ab[0])/3;
            x = new double[]{(ab[0] + ab[1] - d) / 2, (ab[0] + ab[1] + d) / 2};
            f = new double[]{fun.eval(x[0]), fun.eval(x[1])};
            int le = f[0] <= f[1] ? 1 : 0;
            ab[le] = x[le];
            res.addStep(x[0], x[1], f[0], f[1]);
        }
        double x_ = (ab[0] + ab[1]) / 2;
        res.setResult(x_, fun.eval(x_));
        return res;
    }

    public static Result parabola(AbstractFunction fun, double epsLim) {
        double from = fun.l;
        double to = fun.r;
        final Result res = new Result();

        return res;
    }


    public static void main(String[] args) {
        System.out.println(Methods.goldenRatio(new FunVar2(), 1e-15));
        System.out.println(Methods.dichotomy(new FunVar2(), 1e-15));
    }
}
