package metopt.lab4.functions;

import metopt.lab4.matrices.FullMatrix;
import metopt.lab4.matrices.Vector;

import java.util.List;

public class Function1 extends QuadraticFunction {
    public Function1() {
        super(new FullMatrix(List.of(
                new Vector(List.of(1., -0.6)),
                new Vector(List.of(-0.6, 1.))
        )), new Vector(2), 0., new Vector(List.of(4., 1.)), "Function1");
    }
}
