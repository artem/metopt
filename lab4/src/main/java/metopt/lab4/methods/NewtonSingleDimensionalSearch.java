package metopt.lab4.methods;

import metopt.lab4.Result;
import metopt.lab4.Utils;
import metopt.lab4.functions.AbstractFunction;
import metopt.lab4.matrices.Matrix;
import metopt.lab4.matrices.Vector;

public class NewtonSingleDimensionalSearch implements Method {
    public Result run(final AbstractFunction function, final Vector x0, double eps) {
        Vector x = x0;
        for (Result result = new Result(); true; result.iterations++) {
            Vector gradient = function.gradient(x);
            Matrix hessian = function.hessian(x);
            Vector p = Utils.gauss(hessian, gradient.negBy());
            double alpha = SingleDimensionMethods.goldenRatio(z -> function.eval(x.add(p.mul(z))), eps, -20, 20);
            p.mulBy(alpha);
            x.addBy(p);
            result.list.add(p);
            result.additional.add(alpha);
            if (p.norm() <= eps) {
                result.x = x;
                return result;
            }
        }
    }
}
