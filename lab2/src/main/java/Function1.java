import java.util.List;

public class Function1 extends AbstractFunction {
    public Function1() {
        super(
                new Matrix(List.of(List.of(2 * 64., 126.), List.of(126., 2 * 64.))),
                new Matrix(List.of(-10., 30.), true),
                13,
                new Matrix(List.of(-100., -100.), true),
                new Matrix(List.of(100., 100.), true)
        );
    }
}
