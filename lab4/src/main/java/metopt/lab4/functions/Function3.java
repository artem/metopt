package metopt.lab4.functions;

import metopt.lab4.matrices.FullMatrix;
import metopt.lab4.matrices.Matrix;
import metopt.lab4.matrices.Vector;

import java.util.List;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.UnaryOperator;

public class Function3 extends UnfunnyFunction {
    private final static ToDoubleFunction<Vector> function = (x) ->
            Math.pow(Math.pow(x.x(), 2) + x.y() - 11, 2) +
                    Math.pow(x.x() + Math.pow(x.y(), 2) - 7, 2);

    private final static UnaryOperator<Vector> gradient = (x) -> new Vector(List.of(
            4 * Math.pow(x.x(), 3) + 4 * x.x() * x.y() - 42 * x.x() + 2 * Math.pow(x.y(), 2) - 14,
            2 * Math.pow(x.x(), 2) + 4 * x.x() * x.y() + 4 * Math.pow(x.y(), 3) - 26 * x.y() - 22));

    private final static Function<Vector, Matrix> hessian = (x) -> new FullMatrix(List.of(
            new Vector(List.of(
                    12 * Math.pow(x.x(), 2) + 4 * x.y() - 42,
                    4 * x.x() + 4 * x.y()
            )),
            new Vector(List.of(
                    4 * x.x() + 4 * x.y(),
                    4 * x.x() + 12 * x.y() - 26
            ))
    ));

    public Function3() {
        super(function, gradient, hessian, new Vector(List.of(-1.2, 1.)));
    }
}
