import java.util.List;

public class Function5 extends AbstractFunction {
    public Function5() {
        super(new Matrix(List.of(
                List.of(2 * 1.0, 0.0),
                List.of(0.0, 2 * 250.0))),
                new Vector(List.of(0.0, 0.0)),
                0,
                new Vector(List.of(0.0, 1.0)));
    }
}
