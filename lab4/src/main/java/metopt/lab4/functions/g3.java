package metopt.lab4.functions;

import metopt.lab4.matrices.FullMatrix;
import metopt.lab4.matrices.Matrix;
import metopt.lab4.matrices.Vector;

import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.UnaryOperator;

public class g3 extends UnfunnyFunction {
    private static final double eps = 1e-7;

    private final static ToDoubleFunction<Vector> function = v -> {
        final double x = v.get(0);
        final double y = v.get(1);
        return Math.sqrt(Math.pow(x - 2.0, 2) + 1.0) * Math.exp(Math.pow((2.0 * y - x), 2) / 100000.0);
    };

    private final static UnaryOperator<Vector> gradient = v -> {
        final Vector result = new Vector(2);
        final double f0 = function.applyAsDouble(v);
        for (int i = 0; i < 2; i++) {
            final Vector vi = new Vector(v);
            vi.set(i, v.get(i) + eps);
            result.set(i, (function.applyAsDouble(vi) - f0) / eps);
        }
        return result;
    };

    private final static Function<Vector, Matrix> hessian = v -> {
        final int size = 2;
        final Matrix result = new FullMatrix(size, size);
        final double f0 = function.applyAsDouble(v);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final Vector vi = new Vector(v);
                vi.set(i, v.get(i) + eps);
                final Vector vj = new Vector(v);
                vj.set(j, v.get(j) + eps);
                final Vector vij = new Vector(vi);
                vij.set(j, vi.get(j) + eps);
                final double fi = function.applyAsDouble(vi);
                final double fj = function.applyAsDouble(vj);
                final double fij = function.applyAsDouble(vij);
                result.set(i, j, (fij - fi - fj + f0) / (eps * eps));
            }
        }
        return result;
    };

    public g3() {
        super(function, gradient, hessian, new Vector(2), "g3");
    }
}
