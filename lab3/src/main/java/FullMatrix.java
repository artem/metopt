import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FullMatrix extends Matrix {
    protected int n;
    protected int m;
    protected List<List<Double>> data;

    public FullMatrix(int n, int m) {
        this(IntStream.range(0, n)
                .mapToObj(i -> new ArrayList<>(Collections.nCopies(m, 0.)))
                .collect(Collectors.toList()));
    }

    public FullMatrix(List<List<Double>> data) {
        if (data == null) {
            throw new IllegalArgumentException("null data of new matrix.");
        }
        n = data.size();
        m = n == 0 ? 0 : data.get(0).size();
        for (int i = 0; i < n; ++i) {
            if (data.get(i).size() != m) {
                throw new IllegalArgumentException("Not matrix.");
            }
        }
        this.data = new ArrayList<>(Collections.nCopies(n, null));
        for (int i = 0; i < n; ++i) {
            this.data.set(i, new ArrayList<>(List.copyOf(data.get(i))));
        }
    }

    public FullMatrix(FullMatrix other) {
        this(IntStream.range(0, other.n)
                .mapToObj(i -> new ArrayList<>(other.data.get(i)))
                .collect(Collectors.toList()));
    }

    @Override
    public int getSize() {
        if (n != m)
            throw new MatrixException("n != m");
        return n;
    }

    public double get(int i, int j) {
        return data.get(i).get(j);
    }

    public void set(int i, int j, double value) {
        data.get(i).set(j, value);
    }

    public FullMatrix T() {
        FullMatrix result = new FullMatrix(m, n);
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                result.set(j, i, get(i, j));
            }
        }
        return result;
    }

    public FullMatrix negated() {
        FullMatrix result = new FullMatrix(n, m);
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                result.set(i, j, -get(i, j));
            }
        }
        return result;
    }

    public FullMatrix add(FullMatrix other) {
        if (n != other.n || m != other.m) {
            throw new IllegalArgumentException("Incompatible matrices.");
        }
        FullMatrix result = new FullMatrix(n, m);
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                result.set(i, j, get(i, j) + other.get(i, j));
            }
        }
        return result;
    }

    public FullMatrix mul(double k) {
        FullMatrix result = new FullMatrix(n, m);
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                result.set(i, j, get(i, j) * k);
            }
        }
        return result;
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

    public FullMatrix negate() {
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                set(i, j, -get(i, j));
            }
        }
        return this;
    }

    public FullMatrix addBy(FullMatrix other) {
        if (n != other.n || m != other.m) {
            throw new IllegalArgumentException("Incompatible matrices.");
        }
        IntStream.range(0, n).forEach(i -> IntStream.range(0, m).forEach(j -> set(i, j, get(i, j) + other.get(i, j))));
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
