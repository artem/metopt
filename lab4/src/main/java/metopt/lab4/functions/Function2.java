package metopt.lab4.functions;

import metopt.lab4.matrices.FullMatrix;
import metopt.lab4.matrices.Matrix;
import metopt.lab4.matrices.Vector;

import java.util.List;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.UnaryOperator;

public class Function2 extends UnfunnyFunction {
    private final static ToDoubleFunction<Vector> function = (x) ->
            100 * Math.pow(x.x(), 4) +
                    Math.pow(x.x(), 2) +
                    100 * Math.pow(x.y(), 2) -
                    200 * Math.pow(x.x(), 2) * x.y() -
                    2 * x.x() +
                    1;

    private final static UnaryOperator<Vector> gradient = (x) -> new Vector(List.of(
            400 * Math.pow(x.x(), 3) - 400 * x.x() * x.y() + 2 * x.x() - 2,
            x.y() * 200 - Math.pow(x.x(), 2) * 200));

    private final static Function<Vector, Matrix> hessian = (x) -> new FullMatrix(List.of(
            new Vector(List.of(
                    1200 * Math.pow(x.x(), 2) - 400 * x.y() + 2,
                    -400 * x.x()
            )),
            new Vector(List.of(
                    -400 * x.x(),
                    200.
            ))
    ));

    public Function2() {
        super(function, gradient, hessian, new Vector(List.of(-1.2, 1.)));
    }
}
