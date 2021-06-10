package metopt.lab4.functions;

import metopt.lab4.matrices.Matrix;
import metopt.lab4.matrices.Vector;

import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.UnaryOperator;

public class UnfunnyFunction implements FunI {
    private final ToDoubleFunction<Vector> function;
    private final UnaryOperator<Vector> gradient;
    private final Function<Vector, Matrix> hessian;
    private final Vector minVector;

    public UnfunnyFunction(ToDoubleFunction<Vector> function, UnaryOperator<Vector> gradient, Function<Vector, Matrix> hessian) {
        this.function = function;
        this.gradient = gradient;
        this.hessian = hessian;
        minVector = null;
    }

    public UnfunnyFunction(ToDoubleFunction<Vector> function, UnaryOperator<Vector> gradient, Function<Vector, Matrix> hessian, Vector minVector) {
        this.function = function;
        this.gradient = gradient;
        this.hessian = hessian;
        this.minVector = minVector;
    }

    @Override
    public Matrix hessian(Vector x) {
        return hessian.apply(x);
    }

    @Override
    public double eval(Vector x) {
        return function.applyAsDouble(x);
    }

    @Override
    public Vector gradient(Vector x) {
        return gradient.apply(x);
    }
}
