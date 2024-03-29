package metopt.lab4.functions;

import metopt.lab4.matrices.Matrix;
import metopt.lab4.matrices.Vector;

import java.util.ArrayList;
import java.util.List;

public class QuadraticFunction implements FunI {
    final private Matrix A;
    final private Vector b;
    final private double c;
    private Vector x0;
    final private String name;

    public QuadraticFunction(final Matrix a, final Vector b, final double c, final Vector x0, final String name) {
        A = a;
        this.b = b;
        this.c = c;
        this.x0 = x0;
        this.name = name;
    }

    public QuadraticFunction(final Matrix a, final Vector b, final double c, final Vector x0) {
        this(a, b, c, x0, "");
    }

    @Override
    public Matrix hessian(final Vector x) {
        return A;
    }

    @Override
    public double eval(final Vector x) {
        return A.mul(x).scalar(x) / 2 + b.scalar(x) + c;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Vector getX0() {
        return x0;
    }

    @Override
    public void setX0(final Vector x0) {
        this.x0 = x0;
    }

    @Override
    public Vector gradient(final Vector x) {
        return A.mul(x).addBy(b);
    }

    @Override
    public String toString() {
        List<String> terms = new ArrayList<>();
        if (A != null) {
            for (int i = 0; i < A.size(); ++i) {
                for (int j = i; j < A.size(); ++j) {
                    double aij = A.get(i, j);
                    if (aij != 0.) {
                        if (i != j) {
                            terms.add(FunI.shortDouble(aij) + FunI.variable(i + 1) + FunI.variable(j + 1));
                        } else {
                            terms.add(FunI.shortDouble(aij / 2) + FunI.variable(i + 1) + "^2");
                        }
                    }
                }
            }
        }
        if (b != null) {
            for (int i = 0; i < b.size(); ++i) {
                double bi = b.get(i);
                if (bi != 0.) {
                    terms.add(FunI.shortDouble(bi) + FunI.variable(i + 1));
                }
            }
        }
        if (c != 0) {
            terms.add(String.valueOf(c));
        }
        return String.join(" + ", terms);
    }
}
