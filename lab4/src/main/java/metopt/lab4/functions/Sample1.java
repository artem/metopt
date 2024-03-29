package metopt.lab4.functions;

import metopt.lab4.matrices.FullMatrix;
import metopt.lab4.matrices.Vector;

import java.util.List;

public class Sample1 extends QuadraticFunction {
    public Sample1() {
        super(new FullMatrix(List.of(
                new Vector(List.of(2.*254, 506.)),
                new Vector(List.of(506., 2.*254))
        )), new Vector(List.of(50., 130.)), 111., new Vector(List.of(0., 0.)), "Sample1");
    }
}
