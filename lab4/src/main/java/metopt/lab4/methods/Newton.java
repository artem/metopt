package metopt.lab4.methods;

import metopt.lab4.Result;
import metopt.lab4.Utils;
import metopt.lab4.functions.AbstractFunction;
import metopt.lab4.matrices.FullMatrix;
import metopt.lab4.matrices.Matrix;
import metopt.lab4.matrices.Vector;

public class Newton implements Method {
    public Result run(final AbstractFunction function, final Vector x0, double eps) {
        Vector x = x0;
        int iterations = 0;
        while (true) {
            iterations++;
            Vector gradient = function.gradient(x);
            Matrix hessian = function.hessian(x);
            Vector p = Utils.gauss(hessian, gradient.negBy());
            x.addBy(p);
            if (p.norm() <= eps) {
                return new Result(x, iterations);
            }
        }
    }
}
