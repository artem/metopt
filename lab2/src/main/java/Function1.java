import java.util.List;

public class Function1 extends AbstractFunction {

    public Function1() {
        this.start = new Matrix(List.of(-100., -100.), true);
        this.end = new Matrix(List.of(100., 100.), true);
        this.A = new Matrix(List.of(List.of(2*54.,126.),List.of(126.,2*64.)));
        this.b = new Matrix(List.of(-10., 30.), true);
        this.c = 13;
    }
}
