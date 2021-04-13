import java.util.List;

public class Function2 extends AbstractFunction {

    public Function2() {
        this.start = new Matrix(List.of(-100., -100.), true);
        this.end = new Matrix(List.of(100., 100.), true);
        this.A = new Matrix(List.of(List.of(2*254.,506.),List.of(506.,2*254.)));
        this.b = new Matrix(List.of(50., 130.), true);
        this.c = 111;
    }
}
