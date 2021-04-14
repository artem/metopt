import java.util.List;

public class Function4 extends AbstractFunction {
    public Function4() {
        super(new Matrix(List.of(
                List.of(2 * 1., 0., 0.),
                List.of(0., 2 * 1000., 0.),
                List.of(0., 0., 2 * 1.))),
                new Vector(List.of(0., 0., 0.)),
                0,
                new Vector(List.of(100., 100., 100.)));
    }
}
