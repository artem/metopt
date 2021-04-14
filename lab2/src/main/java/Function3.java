import java.util.List;

public class Function3 extends AbstractFunction {
    public Function3() {
        super(new Matrix(List.of(
                List.of(2 * 10., 0.),
                List.of(0., 2 * 1.))),
                new Matrix(List.of(0., 0.), true),
                0,
                new Matrix(List.of(10., 10.), true));
    }
}
