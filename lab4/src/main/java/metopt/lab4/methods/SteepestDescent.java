package metopt.lab4.methods;

import metopt.lab4.Result;
import metopt.lab4.functions.FunI;
import metopt.lab4.matrices.MatrixException;
import metopt.lab4.matrices.Vector;

public class SteepestDescent implements Method {
    private static final int MAX_ITERATIONS = 100_000;

    public Result run(FunI function, Vector x0, double eps) throws MatrixException {
        final Result result = new Result();

        Vector x = new Vector(x0);
        Vector p = function.gradient(x).negBy();
        result.addPoint(new Vector(x));

        for (result.iterations = 1; result.iterations < MAX_ITERATIONS && p.norm() > eps; result.iterations++) {
            Vector finalP = p;
            double a = SingleDimensionMethods.fib(z -> function.eval(x.add(finalP.mul(z))), eps, 0, 1000);
            if (Double.isNaN(a) || Double.isInfinite(a)) {
                System.out.println("NaN or Inf =)");
            }
            x.addBy(p.mulBy(a)); // we don't need p anymore, so we can mulBy it
            p = function.gradient(x).negBy();
            result.addPoint(new Vector(x));
            result.addStep(result.iterations, a, x, p, function.applyAsDouble(x));
        }
        result.x = x;
        return result;
    }

    @Override
    public String getFullName() {
        return "Метод наискорейшего спуска";
    }

    @Override
    public String getShortName() {
        return "steepest";
    }
}
