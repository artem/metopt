package metopt.lab4.functions;

import metopt.lab4.matrices.FullMatrix;
import metopt.lab4.matrices.Vector;

import java.util.List;

public class g1 extends QuadraticFunction {
    public g1() {
        super(
                new FullMatrix(List.of(
                        new Vector(List.of(6.0, 1.0)),
                        new Vector(List.of(1.0, 4.0))
                )),
                new Vector(List.of(-1.0, -4.0)),
                0.0,
                new Vector(2),
                "g1");
    }
}
