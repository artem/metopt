package metopt.lab4.functions;

import metopt.lab4.matrices.FullMatrix;
import metopt.lab4.matrices.Vector;

import java.util.List;

public class f1 extends QuadraticFunction {
    public f1() {
        super(new FullMatrix(List.of(
                new Vector(List.of(2.0, -1.2)),
                new Vector(List.of(-1.2, 2.0))
        )), new Vector(2), 0., new Vector(List.of(4., 1.)), "f1");
    }
}
