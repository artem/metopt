package metopt.lab4.methods;

import metopt.lab4.Result;
import metopt.lab4.Utils;
import metopt.lab4.functions.AbstractFunction;
import metopt.lab4.matrices.Matrix;
import metopt.lab4.matrices.Vector;

public class Newton implements Method {
    public Result<Vector> run(final AbstractFunction function, final Vector x0, double eps) {
        Vector x = x0;
        for (Result<Vector> result = new Result<>(); true; result.iterations++) {
            Vector gradient = function.gradient(x);
            Matrix hessian = function.hessian(x);
            Vector p = Utils.gauss(hessian, gradient.negBy());
            x.addBy(p);
            result.list.add(p);
            if (p.norm() <= eps) {
                result.x = x;
                return result;
            }
        }
    }
}
