package metopt.lab4.methods;

import metopt.lab4.Result;
import metopt.lab4.functions.FunI;
import metopt.lab4.matrices.FullMatrix;
import metopt.lab4.matrices.Matrix;
import metopt.lab4.matrices.Vector;

public abstract class QuasiAbstract implements Method {
    public abstract Matrix nextG(final Matrix lastG, final Vector dx, final Vector dw);

    private double findAlpha(final FunI function, final Vector x, final Vector p) {
        return SingleDimensionMethods.goldenRatio(z -> function.eval(x.add(p.mul(z))), 1e-8, -30, 30);
    }

    @Override
    public Result run(final FunI function, final Vector x0, double eps) {
        Result result = new Result();
        Vector x = new Vector(x0);
        result.addPoint(x);
        Matrix G = FullMatrix.E(x0.size());
        Vector w = function.gradient(x).negBy();
        Vector p = null;
        do {
            if (p != null) {
                Vector lastW = w;
                w = function.gradient(x).negBy();
                Vector dw = w.sub(lastW);
                G = nextG(G, p, dw);
                p = G.mul(w);
            } else {
                p = w;
            }
            double alpha = findAlpha(function, x, p);
            Vector lastX = x;
            x = lastX.add(p.mulBy(alpha));
            result.addPoint(x);
            result.addStep(++result.iterations, alpha, x, p, function.applyAsDouble(x));
            result.additional.add(alpha);
        } while (p.norm() > eps);
        result.x = x;
        return result;
    }
}
