import java.util.List;

public class DiagonalMatrix extends Matrix {
    public DiagonalMatrix(List<Double> diag) {
        super(List.of(diag));
    }

    public Matrix mul(Matrix other) {
        if (m != other.n) {
            throw new IllegalArgumentException("Incompatible matrices.");
        }
        Matrix result = new Matrix(other.n, other.m);
        for (int j = 0; j < other.m; ++j) {
            for (int i = 0; i < other.n; ++i) {
                result.set(i, j, get(0, i) * other.get(i, j));
            }
        }
        return result;
    }
}
