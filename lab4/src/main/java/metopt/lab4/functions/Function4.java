package metopt.lab4.functions;

import metopt.lab4.matrices.FullMatrix;
import metopt.lab4.matrices.Matrix;
import metopt.lab4.matrices.Vector;

import java.util.List;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.UnaryOperator;

public class Function4 extends UnfunnyFunction {
    private final static ToDoubleFunction<Vector> function = (x) ->
            Math.pow(x.x() + 10 * x.y(), 2) +
                    5 * Math.pow(x.get(2) - x.get(3), 2) +
                    Math.pow(x.y() - 2 * x.get(2), 4) +
                    10 * Math.pow(x.x() - x.get(3), 4);

    private final static UnaryOperator<Vector> gradient = (x) -> new Vector(List.of(
            2 * x.x() + 20 * x.y() + 40 * Math.pow(x.x() - x.get(3), 3),
            20 * x.x() + 200 * x.y() + 4 * Math.pow(x.y() - 2 * x.get(2), 3),
            10 * x.get(2) - 10 * x.get(3) - 8 * Math.pow(x.y() - 2 * x.get(2), 3),
            -10 * x.get(2) + 10 * x.get(3) - 40 * Math.pow(x.x() - x.get(3), 3)
    ));

    private final static Function<Vector, Matrix> hessian = (x) -> new FullMatrix(List.of(
            new Vector(List.of(
                    120 * Math.pow(x.x() - x.get(3), 2) + 2,
                    20.,
                    0.,
                    -120 * Math.pow(x.x() - x.get(3), 2)
            )),
            new Vector(List.of(
                    20.,
                    12 * Math.pow(x.y() - 2 * x.get(2), 2) + 200,
                    -24 * Math.pow(x.y() - 2 * x.get(2), 2),
                    0.
            )),
            new Vector(List.of(
                    0.,
                    -24 * Math.pow(x.y() - 2 * x.get(2), 2),
                    48 * Math.pow(x.y() - 2 * x.get(2), 2) + 10,
                    -10.
            )),
            new Vector(List.of(
                    -120 * Math.pow(x.x() - x.get(3), 2),
                    0.,
                    -10.,
                    120 * Math.pow(x.x() - x.get(3), 2) + 10
            ))
    ));

    public Function4() {
        super(function, gradient, hessian, new Vector(List.of(-1.2, 1.)));
    }
}
