package metopt.lab4.functions;

import metopt.lab4.matrices.FullMatrix;
import metopt.lab4.matrices.Matrix;
import metopt.lab4.matrices.Vector;

import java.util.List;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.UnaryOperator;

public class Function5 extends UnfunnyFunction {
    private final static ToDoubleFunction<Vector> function = (x) ->
            100 - 108 / (9 * Math.pow(x.x(), 2) + 4 * Math.pow(x.y(), 2) - 18 * x.x() - 8 * x.y() + 49);

    private final static UnaryOperator<Vector> gradient = (x) -> new Vector(List.of(
            1944 * (x.x() - 1) / Math.pow(9 * Math.pow(x.x(), 2) - 18 * x.x() + 4 * Math.pow(x.y(), 2) - 8 * x.y() + 49, 2),
            864 * (x.y() - 1) / Math.pow(9 * Math.pow(x.x(), 2) - 18 * x.x() + 4 * Math.pow(x.y(), 2) - 8 * x.y() + 49, 2)
    ));

    private final static Function<Vector, Matrix> hessian = (x) -> new FullMatrix(List.of(
            new Vector(List.of(
                    1944 * (-27 * Math.pow(x.x(), 2) + 54 * x.x() + 4 * Math.pow(x.y(), 2) - 8 * x.y() + 13) / Math.pow(9 * Math.pow(x.x(), 2) - 18 * x.x() + 4 * Math.pow(x.y(), 2) - 8 * x.y() + 49, 3),
                    -31104 * (x.x() - 1) * (x.y() - 1) / Math.pow(9 * Math.pow(x.x(), 2) - 18 * x.x() + 4 * Math.pow(x.y(), 2) - 8 * x.y() + 49, 3)
            )),
            new Vector(List.of(
                    -31104 * (x.x() - 1) * (x.y() - 1) / Math.pow(9 * Math.pow(x.x(), 2) - 18 * x.x() + 4 * Math.pow(x.y(), 2) - 8 * x.y() + 49, 3),
                    -2592 * (-3 * Math.pow(x.x(), 2) + 6 * x.x() + 4 * Math.pow(x.y(), 2) - 8 * x.y() - 11) / Math.pow(9 * Math.pow(x.x(), 2) - 18 * x.x() + 4 * Math.pow(x.y(), 2) - 8 * x.y() + 49, 3)
            ))
    ));

    public Function5() {
        super(function, gradient, hessian, new Vector(List.of(-1.2, 1.)));
    }
}
