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
    private final String name;
    private final Vector minVector;

    public UnfunnyFunction(ToDoubleFunction<Vector> function, UnaryOperator<Vector> gradient, Function<Vector, Matrix> hessian, String name) {
        this.function = function;
        this.gradient = gradient;
        this.hessian = hessian;
        this.name = name;
        minVector = null;
    }

    public UnfunnyFunction(ToDoubleFunction<Vector> function, UnaryOperator<Vector> gradient, Function<Vector, Matrix> hessian, String name, Vector minVector) {
        this.function = function;
        this.gradient = gradient;
        this.hessian = hessian;
        this.name = name;
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
    public String getName() {
        return name;
    }

    @Override
    public Vector getX0() {
        return null; // FIXME
    }

    @Override
    public Vector gradient(Vector x) {
        return gradient.apply(x);
    }
}
