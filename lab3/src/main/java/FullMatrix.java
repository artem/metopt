import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FullMatrix extends Matrix {
    protected final int n;
    protected final int m; // square matrix??
    protected final List<Vector> data;

    public FullMatrix(int n, int m) {
        this(IntStream.range(0, n)
                .mapToObj(i -> new Vector(m))
                .collect(Collectors.toList()));
    }

    public FullMatrix(List<Vector> data) {
        if (data == null) {
            throw new IllegalArgumentException("null data of new matrix.");
        }
        n = data.size();
        m = data.get(0).size(); // size == 0 ????
        for (int i = 0; i < n; ++i) {
            if (data.get(i).size() != m) {
                throw new IllegalArgumentException("Not matrix.");
            }
        }
        this.data = new ArrayList<>(Collections.nCopies(n, null));
        for (int i = 0; i < n; ++i) {
            this.data.set(i, new Vector(data.get(i)));
        }
    }

    public FullMatrix(FullMatrix other) {
        this(other.data);
    }

    public FullMatrix(Matrix other) {
        this(other.size(), other.size());
        IntStream.range(0, n).forEach(i -> IntStream.range(0, n).forEach(j -> set(i, j, other.get(i, j))));
    }

    @Override
    public int size() {
        if (n != m)
            throw new MatrixException("n != m");
        return n;
    }

    @Override
    public double get(int i, int j) {
        return data.get(i).get(j);
    }

    @Override
    public void set(int i, int j, double value) {
        data.get(i).set(j, value);
    }

    @Override
    public Matrix transpose() {
        final Matrix ret = new FullMatrix(m, n);
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                ret.set(i, j, get(j, i));
            }
        }
        return ret;
    }

    public FullMatrix negated() {
        final FullMatrix ret = new FullMatrix(n, m);
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                ret.set(i, j, -get(i, j));
            }
        }
        return ret;
    }

    public FullMatrix mul(final double k) {
        FullMatrix ret = new FullMatrix(n, m);
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                ret.set(i, j, get(i, j) * k); // todo
            }
        }
        return ret;
    }

    public FullMatrix mul(FullMatrix other) {
        if (m != other.n) {
            throw new IllegalArgumentException("Incompatible matrices.");
        }
        FullMatrix result = new FullMatrix(n, other.m);
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < other.m; ++j) {
                double c = 0.;
                for (int k = 0; k < m; ++k) {
                    c += get(i, k) * other.get(k, j);
                }
                result.set(i, j, c);
            }
        }
        return result;
    }

    public double scalar(FullMatrix other) {
        if (m != 1 || other.m != 1) {
            throw new IllegalArgumentException("Scalar multiplication of non-vectors.");
        }
        if (n != other.n) {
            throw new IllegalArgumentException("Incompatible matrices.");
        }
        double result = 0.;
        for (int i = 0; i < n; ++i) {
            result += get(i, 0) * other.get(i, 0);
        }
        return result;
    }

    public double len() {
        return Math.sqrt(scalar(this));
    }

    public FullMatrix normalize() {
        divBy(len());
        return this;
    }

    public FullMatrix mulBy(double k) {
        data.forEach(row -> IntStream.range(0, m).forEach(i -> row.set(i, row.get(i) * k)));
        return this;
    }

    public FullMatrix divBy(double k) {
        data.forEach(row -> IntStream.range(0, m).forEach(i -> row.set(i, row.get(i) / k)));
        return this;
    }
}
