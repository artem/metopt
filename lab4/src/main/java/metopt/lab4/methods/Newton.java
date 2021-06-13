package metopt.lab4.methods;

import metopt.lab4.Result;
import metopt.lab4.Utils;
import metopt.lab4.functions.FunI;
import metopt.lab4.matrices.Matrix;
import metopt.lab4.matrices.Vector;

public class Newton implements Method {
    public Result run(final FunI function, final Vector x0, double eps) {
        Result result = new Result();
        Vector x = new Vector(x0);
        result.addPoint(x);
        for (result.iterations = 1; true; result.iterations++) {
            Matrix hessian = function.hessian(x);
            Vector p = Utils.gauss(hessian, function.gradient(x).negBy());
            x.addBy(p);
            result.addPoint(x);
            result.addStep(result.iterations, 1, x, p, function.applyAsDouble(x));
            if (p.norm() <= eps) {
                result.x = x;
                return result;
            }
        }
    }

    @Override
    public String getFullName() {
        return "Классический метод Ньютона";
    }

    @Override
    public String getShortName() {
        return "classic";
    }
}
