package metopt.lab4.methods;

import metopt.lab4.Result;
import metopt.lab4.Utils;
import metopt.lab4.functions.QuadraticFunction;
import metopt.lab4.matrices.Matrix;
import metopt.lab4.matrices.Vector;

public class Newton implements Method {
    public Result run(final QuadraticFunction function, final Vector x0, double eps) {
        Result result = new Result();
        Vector x = x0;
        result.addStep(x);
        for (result.iterations = 1; true; result.iterations++) {
            Matrix hessian = function.hessian(x);
            Vector p = Utils.gauss(hessian, function.gradient(x).negBy());
            x.addBy(p);
            result.addStep(x);
            if (p.norm() <= eps) {
                result.x = x;
                return result;
            }
        }
    }

    @Override
    public String name() {
        return "Метод ньютона";
    }
}
