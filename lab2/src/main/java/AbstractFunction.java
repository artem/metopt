import java.util.ArrayList;
import java.util.List;

public abstract class AbstractFunction {
    final public Matrix A;
    final public Vector b;
    final public double c;
    final public Vector x0;

    public AbstractFunction(final Matrix a, final Vector b, final double c, final Vector x0) {
        A = a;
        this.b = b;
        this.c = c;
        this.x0 = x0;
    }

    double eval(final Matrix x) {
        return A.mul(x).scalar(x) / 2 + b.scalar(x) + c;
    }

    Matrix gradient(final Matrix x) {
        return A.mul(x).addBy(b);
    }

    private String shortDouble(double d) {
        return ((int) d) == d ? d == 1. ? "" : String.valueOf((int) d) : String.valueOf(d);
    }

    private String variable(int i) {
        return "x_{" + i + "}";
    }

    @Override
    public String toString() {
        List<String> terms = new ArrayList<>();
        if (A != null) {
            for (int i = 0; i < A.getN(); ++i) {
                for (int j = i; j < A.getM(); ++j) {
                    double aij = A.get(i, j);
                    if (aij != 0.) {
                        if (i != j) {
                            terms.add(shortDouble(aij) + variable(i + 1) + variable(j + 1));
                        } else {
                            terms.add(shortDouble(aij / 2) + variable(i + 1) + "^2");
                        }
                    }
                }
            }
        }
        if (b != null) {
            for (int i = 0; i < b.getN(); ++i) {
                double bi = b.get(i, 0);
                if (bi != 0.) {
                    terms.add(shortDouble(bi) + variable(i + 1));
                }
            }
        }
        if (c != 0) {
            terms.add(String.valueOf(c));
        }
        return String.join(" + ", terms);
    }
}
