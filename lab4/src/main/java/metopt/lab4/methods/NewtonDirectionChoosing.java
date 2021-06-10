package metopt.lab4.methods;

import metopt.lab4.Result;
import metopt.lab4.Utils;
import metopt.lab4.functions.AbstractFunction;
import metopt.lab4.matrices.Matrix;
import metopt.lab4.matrices.Vector;

public class NewtonDirectionChoosing implements Method {
    public Result run(final AbstractFunction function, final Vector x0, double eps) {
        Result result = new Result();
        Vector x = x0;
        result.addStep(x);
        Vector antiGrad = function.gradient(x).negBy();
        Vector finalAntiGrad = antiGrad;
        double alpha = SingleDimensionMethods.goldenRatio(z -> function.eval(x0.add(finalAntiGrad.mul(z))), eps, -20, 20);
        Vector p = antiGrad.mul(alpha);
        x.addBy(p);
        result.addStep(x);
        for (result.iterations=2; true; result.iterations++) {
            Vector gradient = function.gradient(x);
            Matrix hessian = function.hessian(x);
            p = Utils.gauss(hessian, gradient.neg());
            if (p.scalar(gradient) < 0) {
                antiGrad = p;
            } else {
                antiGrad = gradient.neg();
            }
            Vector finalAntiGrad1 = antiGrad;
            alpha = SingleDimensionMethods.goldenRatio(z -> function.eval(x.add(finalAntiGrad1.mul(z))), eps, -20, 20);
            p = antiGrad.mulBy(alpha);
            x.addBy(p);
            result.addStep(x);
            result.additional.add(alpha);
            if (p.norm() <= eps) {
                result.x = x;
                return result;
            }
        }
    }
}
