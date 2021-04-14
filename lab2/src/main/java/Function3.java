import java.util.List;

public class Function3 extends AbstractFunction {
    public Function3() {
        super(new Matrix(List.of(
                List.of(2 * 10., 0.),
                List.of(0., 2 * 1.))),
                new Vector(List.of(0., 0.)),
                0,
                new Vector(List.of(10., 10.)));
    }
}
