package metopt.lab4.methods;

import metopt.lab4.Result;
import metopt.lab4.functions.AbstractFunction;
import metopt.lab4.matrices.FullMatrix;
import metopt.lab4.matrices.Matrix;
import metopt.lab4.matrices.Vector;

public class BFS extends QuasiAbstract {

    @Override
    public Matrix nextG(Matrix G, Vector dx, Vector dw) {
        Vector v = G.mul(dw);
        double rho = v.scalar(dw);
        double sxw = 1 / dw.scalar(dx);

        Vector r = v.mul(1 / rho).sub(dx.mul(sxw));
        Matrix first = dx.mul(dx).mul(sxw).negBy();
        Matrix second = v.mul(dw).mul(G.transpose()).mul(1 / rho).negBy();
        Matrix third = r.mul(rho).mul(r);
        return G.add(first).add(second).add(third);
    }
}
