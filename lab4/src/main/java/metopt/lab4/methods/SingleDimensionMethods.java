package metopt.lab4.methods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.UnaryOperator;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

@SuppressWarnings("unused")
public class SingleDimensionMethods {
    public static class Fibonacci {
        private static final ArrayList<Double> fib = new ArrayList<>(List.of(0.0, 1.0, 1.0));

        public static double get(final int n) {
            if (n < 0) {
                throw new IllegalArgumentException("n must be non-negative");
            }

            if (n > fib.size() - 1) {
                fib.ensureCapacity(n + 1);
                for (int i = fib.size(); i <= n; i++) {
                    fib.add(fib.get(i - 2) + fib.get(i - 1));
                }
            }

            return fib.get(n);
        }

        public static double relation(int a, int b) {
            return get(a) / get(b);
        }
    }

    public static class Point implements Comparable<Point> {
        private final double x;
        private final double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        @Override
        public int compareTo(Point p) {
            return Double.compare(x, p.x);
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Point) {
                Point p = (Point) o;
                return x == p.getX() && y == p.getY();
            }
            return false;
        }
    }


    public static double goldenRatio(UnaryOperator<Double> fun, double epsLim, double from, double to) {
        final double Tau = (Math.sqrt(5) - 1) / 2;
        double I = (to - from) * Tau;
        double[] x = new double[]{to - I, from + I};
        double[] f = new double[]{fun.apply(x[0]), fun.apply(x[1])};
        double[] ab = new double[]{from, to};
        for (double eps = (ab[1] - ab[0]) / 2; eps > epsLim; eps *= Tau) {
            I *= Tau;
            int le = f[0] <= f[1] ? 1 : 0;
            ab[le] = x[le];
            x[le] = x[1 - le];
            x[1 - le] = ab[le] + (le == 0 ? I : -I);
            f[le] = f[1 - le];
            f[1 - le] = fun.apply(x[1 - le]);
        }
        return (ab[0] + ab[1]) / 2;
    }

    public static double dichotomy(UnaryOperator<Double> fun, double epsLim, double from, double to) {
        double d;
        double[] x;
        double[] f;
        double[] ab = new double[]{from, to};
        for (double eps = (ab[1] - ab[0]) / 2; eps > epsLim; eps = (ab[1] - ab[0]) / 2) {
            d = (ab[1]-ab[0])/3;
            x = new double[]{(ab[0] + ab[1] - d) / 2, (ab[0] + ab[1] + d) / 2};
            f = new double[]{fun.apply(x[0]), fun.apply(x[1])};
            int le = f[0] <= f[1] ? 1 : 0;
            ab[le] = x[le];
        }
        return (ab[0] + ab[1]) / 2;
    }

    private static double rangeRand(double l, double r) {
        return l + Math.random() * (r - l);
    }

    private static double randomBottom(UnaryOperator<Double> f, double x1, double x3, double f1, double f3, double eps) {
        if (x3 - x1 < eps) {
            return f1 < f3 ? x1 : x3;
        }
        double x2, f2;
        do {
            x2 = rangeRand(x1, x3);
            f2 = f.apply(x2);
        } while (x2 < x1 || x3 < x2);
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

    private static double[] getParabola(Point[] p) {
        return getParabola(p[0].getX(), p[1].getX(), p[2].getX(), p[0].getY(), p[1].getY(), p[2].getY());
    }

    private static double getParabolaMin(double[] x, double[] f) {
        final Point[] p = new Point[3];
        for (int i = 0; i < p.length; ++i) {
            p[i] = new Point(x[i], f[i]);
        }
        Arrays.sort(p);
        double[] a = getParabola(p);
        return (p[0].getX() + p[1].getX() - a[1] / a[2]) / 2.;
    }

    public static double parabola(UnaryOperator<Double> fun, double epsLim, double from, double to) {
        final double ffrom = fun.apply(from);
        final double fto = fun.apply(to);

        double[] x = new double[]{from, randomBottom(fun, from, to, ffrom, fto, epsLim), to};
        double[] f = new double[]{ffrom, fun.apply(x[1]), fto};
        double x_ = getParabolaMin(x, f);
        double f_ = fun.apply(x_);

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
            x_ = getParabolaMin(x, f);
            if (Double.isNaN(x_)) {
                break;
            }
            f_ = fun.apply(x_);
        } while (Math.abs(x_ - p) > epsLim);

        return x_;
    }


    public static double brent(UnaryOperator<Double> fun, double epsLim, double from, double to) {
        final double K = (3 - sqrt(5)) / 2;

        double a = from;
        double c = to;
        double fa = fun.apply(a);
        double fc = fun.apply(c);

        double x, w, v, u = 0;
        x = w = v = a + K * (c - a);

        double fx, fw, fv, fu;
        fx = fw = fv = fun.apply(x);

        double d2, d1, d;
        d1 = d = c - a;

        while (true) {
            double b = (a + c) / 2;
            d2 = d1;
            d1 = d;
            final double t = epsLim * abs(x) + epsLim / 10;
            if (abs(x - b) + (c - a) / 2 <= 2 * t) {
                break;
            }
            boolean accepted = false;
            if (x != w && x != v && w != v && fx != fw && fx != fv && fw != fv) {
                u = getParabolaMin(new double[]{x, w, v}, new double[]{fx, fw, fv});
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
            fu = fun.apply(u);
            if (fu <= fx) {
                if (u >= x) {
                    a = x;
                } else {
                    c = x;
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
                } else {
                    a = u;
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
        return x;
    }

    public static double fib(UnaryOperator<Double> fun, double epsLim, double from, double to) {
        final double distance = to - from;
        int index = 2;

        while (distance / epsLim >= Fibonacci.get(index)) {
            index++;
        }
        final int n = index - 2;

        double[] ab = new double[]{from, to};
        double x1 = ab[0] + Fibonacci.relation(n - 1 + 1, n + 2) * distance;
        double x2 = ab[0] + Fibonacci.relation(n - 1 + 2, n + 2) * distance;

        double f1 = fun.apply(x1);
        double f2 = fun.apply(x2);
        for (int i = 1; i <= n; i++) {
            if (f1 <= f2) {
                ab[1] = x2;
                x2 = x1;
                f2 = f1;
                x1 = ab[0] + Fibonacci.relation(n - i, n + 2) * distance;
                f1 = fun.apply(x1);
            } else {
                ab[0] = x1;
                x1 = x2;
                f1 = f2;
                x2 = ab[0] + Fibonacci.relation(n - i + 1, n + 2) * distance;
                f2 = fun.apply(x2);
            }
        }
        return (ab[0] + ab[1]) / 2;
    }
}
