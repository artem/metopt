package metopt.lab4.functions;

import metopt.lab4.matrices.FullMatrix;
import metopt.lab4.matrices.Matrix;
import metopt.lab4.matrices.Vector;

import java.util.List;

public class Function1 extends AbstractFunction{


    public Function1() {
        super(new FullMatrix(List.of(
                new Vector(List.of(2., -1.2)),
                new Vector(List.of(-1.2, 2.))
        )), new Vector(2), 0., new Vector(List.of(4., 1.)));
    }

    @Override
    public Matrix hessian(Vector x) {
        return A.add(FullMatrix.E(2).add(2.4)).mul(2.56);
    }
}
