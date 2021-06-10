package metopt.lab4.functions;

import metopt.lab4.matrices.Matrix;
import metopt.lab4.matrices.Vector;

import java.util.function.ToDoubleFunction;

public interface FunI extends ToDoubleFunction<Vector> {
    Matrix hessian(final Vector x);

    double eval(final Vector x);

    @Override
    default double applyAsDouble(final Vector x) {
        return eval(x);
    }

    Vector gradient(final Vector x);

    static String variable(int i) {
        return "x_{" + i + "}";
    }

    static String shortDouble(double d) {
        return ((int) d) == d ? d == 1. ? "" : String.valueOf((int) d) : String.valueOf(d);
    }
}
