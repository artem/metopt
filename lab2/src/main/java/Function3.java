import java.util.List;

public class Function3 extends AbstractFunction {
    public Function3() {
        super(new Matrix(List.of(
                List.of(2 * 15.0, 3.0),
                List.of(3.0, 2 * 7.0))),
                new Vector(List.of(-78.0, -57.0)),
                42.0,
                new Vector(List.of(10.0, 10.0))
        );
    }
}
