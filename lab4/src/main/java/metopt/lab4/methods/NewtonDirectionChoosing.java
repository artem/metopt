package metopt.lab4.methods;

import metopt.lab4.Result;
import metopt.lab4.Utils;
import metopt.lab4.functions.FunI;
import metopt.lab4.matrices.Matrix;
import metopt.lab4.matrices.Vector;

public class NewtonDirectionChoosing implements Method {
    public Result run(final FunI function, final Vector x0, double eps) {
        Result result = new Result();
        Vector x = new Vector(x0);
        result.addPoint(x);
        for (result.iterations = 1; result.iterations <= 10_000; result.iterations++) {
            Vector gradient = function.gradient(x);
            Matrix hessian = function.hessian(x);
            Vector p = Utils.gauss(hessian, gradient.negBy());
            if (p.scalar(gradient) < 0) {
                p = gradient.neg();
            }
            final Vector finP = p;
            double alpha = SingleDimensionMethods.goldenRatio(z -> function.eval(x.add(finP.mul(z))), eps, -20, 20);
            x.addBy(p.mulBy(alpha));
            result.addPoint(x);
            result.addStep(result.iterations, alpha, x, p, function.applyAsDouble(x));
            result.additional.add(alpha);
            if (p.norm() <= eps) {
                result.x = x;
                return result;
            }
        }
        return result;
    }

    @Override
    public String getFullName() {
        return "Метод ньютона с направлением спуска";
    }

    @Override
    public String getShortName() {
        return "descend";
    }
}
