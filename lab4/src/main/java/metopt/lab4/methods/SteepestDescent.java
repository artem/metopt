package metopt.lab4.methods;

import metopt.lab4.Result;
import metopt.lab4.functions.AbstractFunction;
import metopt.lab4.matrices.FullMatrix;
import metopt.lab4.matrices.Matrix;
import metopt.lab4.matrices.MatrixException;
import metopt.lab4.matrices.Vector;


import java.util.function.UnaryOperator;

public class SteepestDescent implements Method {
    private static final int MAX_ITERATIONS = 100_000;

    public Result run(AbstractFunction function, Vector x0, double eps) throws MatrixException {
        final Result result = new Result();

        Vector x = new Vector(x0);
        Vector p = function.gradient(x).negBy();
        result.addStep(new Vector(x));

        for (result.iterations = 1; result.iterations < MAX_ITERATIONS && p.norm() > eps; result.iterations++) {
            Vector finalP = p;
            double a = SingleDimensionMethods.fib(z -> function.eval(x.add(finalP.mul(z))), eps, 0, 1000);
            if (Double.isNaN(a) || Double.isInfinite(a)) {
                System.out.println("NaN or Inf =)");
            }
            x.addBy(p.mulBy(a)); // we don't need p anymore, so we can mulBy it
            p = function.gradient(x).negBy();
            result.addStep(new Vector(x));
        }
        result.x = x;
        return result;
    }

    @Override
    public String name() {
        return "Метод наискорейшего спуска";
    }
}
