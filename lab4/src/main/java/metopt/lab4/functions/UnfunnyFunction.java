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
    private Vector x0;

    public UnfunnyFunction(ToDoubleFunction<Vector> function,
                           UnaryOperator<Vector> gradient,
                           Function<Vector, Matrix> hessian,
                           Vector x0) {
        this(function, gradient, hessian, x0, "");
    }

    public UnfunnyFunction(ToDoubleFunction<Vector> function,
                           UnaryOperator<Vector> gradient,
                           Function<Vector, Matrix> hessian,
                           Vector x0,
                           String name
                           ) {
        this.function = function;
        this.gradient = gradient;
        this.hessian = hessian;
        this.name = name;
        this.x0 = x0;
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
        return x0;
    }

    @Override
    public void setX0(final Vector x0) {
        this.x0 = x0;
    }

    @Override
    public Vector gradient(Vector x) {
        return gradient.apply(x);
    }
}
