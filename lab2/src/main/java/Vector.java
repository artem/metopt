import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Vector extends Matrix {
    public Vector(int n) {
        super(n, 1);
    }

    public Vector(List<Double> coord) {
        super(coord.stream().map(List::of).collect(Collectors.toList()));
    }
}
