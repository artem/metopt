import java.util.ArrayList;
import java.util.List;

public abstract class AbstractFunction {
    final public Matrix A;
    final public Matrix b; // vector
    final public double c;
    final public Matrix start;
    final public Matrix end;

    public AbstractFunction(final Matrix a, final Matrix b, final double c, final Matrix start, final Matrix end) {
        A = a;
        this.b = b;
        this.c = c;
        this.start = start;
        this.end = end;
    }

    double eval(final Matrix m) throws MatrixException {
        return Matrix.scalar(Matrix.mul(A, m), m)/2 + Matrix.scalar(b, m) + c;
    }

    Matrix gradient(final Matrix m) throws MatrixException {
        return Matrix.sum(Matrix.mul(A, m), b);
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
            for (int i = 0; i < A.m; ++i) {
                for (int j = i; j < A.n; ++j) {
                    double aij = A.get(i, j);
                    if (aij != 0.) {
                        if (i != j) {
                            terms.add(shortDouble(aij) + variable(i + 1) + variable(j + 1));
                        } else {
                            terms.add(shortDouble(aij) + variable(i + 1) + "^2");
                        }
                    }
                }
            }
        }
        if (b != null) {
            for (int i = 0; i < b.m; ++i) {
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
