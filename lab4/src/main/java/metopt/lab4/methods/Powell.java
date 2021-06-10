package metopt.lab4.methods;

import metopt.lab4.matrices.Matrix;
import metopt.lab4.matrices.Vector;

public class Powell extends QuasiAbstract {

    @Override
    public Matrix nextG(final Matrix G, final Vector dx, final Vector dw) {
        Vector dxt = dx.add(G.mul(dw));
        Matrix subtrahend = dxt.mul(dxt).mul(1 / dw.scalar(dxt)).negBy();
        return G.add(subtrahend);
    }

    @Override
    public String name() {
        return "Метод Пауэлла";
    }
}
