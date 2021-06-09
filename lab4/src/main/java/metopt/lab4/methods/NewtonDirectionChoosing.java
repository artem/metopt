package metopt.lab4.methods;

import metopt.lab4.Result;
import metopt.lab4.Utils;
import metopt.lab4.functions.AbstractFunction;
import metopt.lab4.matrices.Matrix;
import metopt.lab4.matrices.ProfileMatrix;
import metopt.lab4.matrices.Vector;

public class NewtonDirectionChoosing implements Method {
    public Result run(final AbstractFunction function, final Vector x0, double eps) {
        int iterations = 0;
        Vector x = x0;
        Vector antiGrad = function.gradient(x).negBy();
        Vector finalAntiGrad = antiGrad;
        double r = SingleDimensionMethods.goldenRatio(z -> function.eval(x0.add(finalAntiGrad.mul(z))), eps, -20, 20);
        Vector s = antiGrad.mul(r);
        x.addBy(s);
        while (true) {
            iterations++;
            Vector gradient = function.gradient(x);
            Matrix hessian = function.hessian(x);
            s = Utils.gauss(hessian, gradient.neg());
            if (s.scalar(gradient) < 0) {
                antiGrad = s;
            } else {
                antiGrad = gradient.neg();
            }
            Vector finalAntiGrad1 = antiGrad;
            r = SingleDimensionMethods.goldenRatio(z -> function.eval(x.add(finalAntiGrad1.mul(z))), eps, -20, 20);
            s = antiGrad.mulBy(r);
            x.addBy(s);
            if (s.norm() <= eps) {
                return new Result(x, iterations);
            }
        }
    }
}
