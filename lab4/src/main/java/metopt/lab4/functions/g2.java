package metopt.lab4.functions;

import metopt.lab4.matrices.FullMatrix;
import metopt.lab4.matrices.Matrix;
import metopt.lab4.matrices.Vector;

import java.util.List;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.UnaryOperator;

public class g2 extends UnfunnyFunction {
    private final static ToDoubleFunction<Vector> function = v -> {
        final double x = v.get(0);
        final double y = v.get(1);
        return Math.pow(x - 2.0, 2) + Math.pow((x * y + 1.0) / 20.0, 2);
    };

    private final static UnaryOperator<Vector> gradient = v -> {
        final double x = v.get(0);
        final double y = v.get(1);
        final double grad1 = (x * y + 1.0) * y / 200.0 + 2.0 * (x - 2.0);
        final double grad2 = (x * y + 1.0) * x / 200.0;
        return new Vector(List.of(grad1, grad2));
    };

    private final static Function<Vector, Matrix> hessian = v -> {
        final double x = v.get(0);
        final double y = v.get(1);
        final double hess11 = Math.pow(y, 2) / 200.0 + 2.0;
        final double hess12 = x * y / 200.0 + (x * y + 1) / 200.0;
        final double hess22 = Math.pow(x, 2) / 200.0;
        return new FullMatrix(List.of(new Vector(List.of(hess11, hess12)),
                                      new Vector(List.of(hess12, hess22))));
    };

    public g2() {
        super(function, gradient, hessian, new Vector(2), "g2");
    }
}
