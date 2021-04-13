import java.util.List;

public class Function2 extends AbstractFunction {

    public Function2() {
        super(
                new Matrix(List.of(List.of(2 * 254., 506.), List.of(506., 2 * 254.))),
                new Matrix(List.of(50., 130.), true),
                111,
                new Matrix(List.of(-100., -100.), true),
                new Matrix(List.of(100., 100.), true)
        );
    }
}
