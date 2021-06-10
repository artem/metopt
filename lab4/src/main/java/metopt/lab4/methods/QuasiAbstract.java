package metopt.lab4.methods;

import metopt.lab4.Result;
import metopt.lab4.functions.AbstractFunction;
import metopt.lab4.matrices.FullMatrix;
import metopt.lab4.matrices.Matrix;
import metopt.lab4.matrices.Vector;

public abstract class QuasiAbstract implements Method{

    public abstract Matrix nextG(final Matrix lastG, final Vector dx, final Vector dw);

    private double findAlpha(final AbstractFunction function, final Vector x, final Vector p) {
        return SingleDimensionMethods.goldenRatio(z -> function.eval(x.add(p.mul(z))), 1e-9, -20, 20);
    }

    @Override
    public Result run(final AbstractFunction function, final Vector x0, double eps) {
        Result result = new Result();
        Vector x = new Vector(x0);
        Matrix G = FullMatrix.E(x0.size());
        Vector w = function.gradient(x).negBy();
        Vector p = null;
        Vector dx = x;
        do {
            if (p != null) {
                Vector lastW = w;
                w = function.gradient(x).negBy();
                Vector dw = w.sub(lastW);
                G = nextG(G, dx, dw);
                p = G.mul(w);
            } else {
                p = w;
            }
            double alpha = findAlpha(function, x, p);
            result.list.add(p);
            result.additional.add(alpha);
            Vector lastX = x;
            x = lastX.add(p.mul(alpha));
            dx = x.sub(lastX);
            result.iterations++;
        } while (x.norm() < eps);
        result.x = x;
        return result;
    }
}
