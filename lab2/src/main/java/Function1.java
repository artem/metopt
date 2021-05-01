import java.util.List;

public class Function1 extends AbstractFunction {
    public Function1() {
        super(
                new Matrix(List.of(
                        List.of(2 * 1.0, 0.0),
                        List.of(0.0, 2 * 10.0))),
                new Vector(List.of(1.0, 2.0)),
                3.0,
                new Vector(List.of(0., 0.))
        );
    }
}
