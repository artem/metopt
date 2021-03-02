import java.util.Arrays;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

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

    private static double rangeRand(double l, double r) {
        return l + Math.random() * (r - l);
    }

    private static double randomBottom(AbstractFunction f, double x1, double x3, double f1, double f3, double eps) {
        double x2, f2;
        do {
            x2 = rangeRand(x1, x3);
            f2 = f.eval(x2);
        } while (x2 - x1 < eps || x3 - x2 < eps);
        if (f2 <= f1 && f2 <= f3) {
            return x2;
        } else if (f2 > f1) {
            return randomBottom(f, x1, x2, f1, f2, eps);
        } else {
            return randomBottom(f, x2, x3, f2, f3, eps);
        }
    }

    private static double[] getParabola(double x1, double x2, double x3, double f1, double f2, double f3) {
        return new double[]
                {
                        f1,
                        (f2 - f1) / (x2 - x1),
                        ((f3 - f1) / (x3 - x1) - (f2 - f1) / (x2 - x1)) / (x3 - x2)
                };
    }

    private static double[] getParabola(double[] x, AbstractFunction f) {
        return getParabola(x[0], x[1], x[2], f.eval(x[0]), f.eval(x[1]), f.eval(x[2]));
    }

    private static double getParabolaMin(double[] x, AbstractFunction f, Result result) {
        Arrays.sort(x);
        double[] a = getParabola(x, f);
        if (result != null) {
            result.addStep(new ParabolaStep(a, x[0], x[1]));
        }
        return (x[0] + x[1] - a[1] / a[2]) / 2.;
    }

    public static Result parabola(AbstractFunction fun, double epsLim) {
        final double from = fun.l;
        final double to = fun.r;
        final double ffrom = fun.eval(from);
        final double fto = fun.eval(to);

        final Result res = new Result();

        double[] x = new double[]{fun.l, randomBottom(fun, from, to, ffrom, fto, epsLim), to};
        double[] f = new double[]{ffrom, fun.eval(x[1]), fto};
        double x_ = getParabolaMin(x, fun, res);
        double f_ = fun.eval(x_);
        double p;

        do {
            if (x_ < x[1]) {
                if (f_ >= f[1]) {
                    x = new double[]{x_, x[1], x[2]};
                    f = new double[]{f_, f[1], f[2]};
                } else {
                    x = new double[]{x[0], x_, x[1]};
                    f = new double[]{f[0], f_, f[1]};
                }
            } else {
                if (f[1] >= f_) {
                    x = new double[]{x[1], x_, x[2]};
                    f = new double[]{f[1], f_, f[2]};
                } else {
                    x = new double[]{x[0], x[1], x_};
                    f = new double[]{f[0], f[1], f_};
                }
            }
            p = x_;
            x_ = getParabolaMin(x, fun, res);
            f_ = fun.eval(x_);
        } while (Math.abs(x_ - p) > epsLim);

        res.setResult(x_, f_);

        System.out.println("parabola steps:");
        for (ResultPart resultPart : res.steps) {
            System.out.println(resultPart);
        }

        return res;
    }

    public static Result brent(AbstractFunction fun, double epsLim) {
        final Result res = new Result();

        final double K = (3 - sqrt(5)) / 2;

        double a = fun.l;
        double c = fun.r;
        double fa = fun.eval(a);
        double fc = fun.eval(c);

        double x, w, v, u = 0;
        x = w = v = a + K * (c - a);

        double fx, fw, fv, fu;
        fx = fw = fv = fun.eval(x);

        double d2, d1, d;
        d1 = d = c - a;

        while (true) {
            res.addStep(a, c, fa, fc);
            double b = (a + c) / 2;
            d2 = d1;
            d1 = d;
            final double t = epsLim * abs(x) + epsLim / 10;
            if (abs(x - b) + (c - a) / 2 <= 2 * t) {
                break;
            }
            boolean accepted = false;
            if (x != w && x != v && w != v && fx != fw && fx != fv && fw != fv) {
                u = getParabolaMin(new double[]{x, w, v}, fun, null);
                if (a <= u && u <= c && abs(u - x) < d2 / 2) {
                    accepted = true;
                    if (u - a < 2 * t || c - u < 2 * t) {
                        if (x < b) {
                            u = x + t;
                        } else {
                            u = x - t;
                        }
                    }
                }
            }
            if (!accepted) {
                if (x < b) {
                    u = x + K * (c - x);
                    d1 = c - x;
                } else {
                    u = x - K * (x - a);
                    d1 = x - a;
                }
            }
            if (abs(u - x) < t) {
                if (u < x) {
                    u = x - t;
                } else {
                    u = x + t;
                }
            }
            d = abs(u - x);
            fu = fun.eval(u);
            if (fu <= fx) {
                if (u >= x) {
                    a = x;
                    fa = fx;
                } else {
                    c = x;
                    fc = fx;
                }
                v = w;
                w = x;
                x = u;
                fv = fw;
                fw = fx;
                fx = fu;
            } else {
                if (u >= x) {
                    c = u;
                    fc = fu;
                } else {
                    a = u;
                    fa = fu;
                }
                if (fu <= fw || w == x) {
                    v = w;
                    w = u;
                    fv = fw;
                    fw = fu;
                } else {
                    v = u;
                    fv = fu;
                }
            }
        }
        res.setResult(x, fx);
        return res;
    }

    public static Result brent(AbstractFunction fun, double epsLim) {
        double from = fun.l;
        double to = fun.r;
        final Result res = new Result();

        return res;
    }

    public static Result fib(AbstractFunction fun, double epsLim) {
        double from = fun.l;
        double to = fun.r;
        final Result res = new Result();

        return res;
    }


    public static void main(String[] args) {
        System.out.println(Methods.goldenRatio(new FunVar2(), 1e-15));
        System.out.println(Methods.dichotomy(new FunVar2(), 1e-15));
        System.out.println(Methods.parabola(new FunVar2(), 1e-15));
        System.out.println(Methods.brent(new FunVar2(), 1e-15));
    }
}