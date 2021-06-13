package metopt.lab4.methods;

import metopt.lab4.Result;
import metopt.lab4.Utils;
import metopt.lab4.functions.FunI;
import metopt.lab4.matrices.Matrix;
import metopt.lab4.matrices.Vector;

public class NewtonSingleDimensionalSearch implements Method {
    public Result run(final FunI function, final Vector x0, double eps) {
        Result result = new Result();
        Vector x = new Vector(x0);
        result.addPoint(x);
        for (result.iterations = 1; true; result.iterations++) {
            Vector gradient = function.gradient(x);
            Matrix hessian = function.hessian(x);
            Vector p = Utils.gauss(hessian, gradient.negBy());
            double alpha = SingleDimensionMethods.goldenRatio(z -> function.eval(x.add(p.mul(z))), eps, -20, 20);
            x.addBy(p.mulBy(alpha));
            result.addPoint(x);
            result.addStep(result.iterations, alpha, x, p, function.applyAsDouble(x));
            result.additional.add(alpha);
            if (p.norm() <= eps) {
                result.x = x;
                return result;
            }
        }
    }

    @Override
    public String getFullName() {
        return "Метод Ньютона с одномерным спуском";
    }

    @Override
    public String getShortName() {
        return "one_dimension";
    }
}
