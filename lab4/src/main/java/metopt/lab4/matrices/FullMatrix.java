package metopt.lab4.matrices;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FullMatrix extends Matrix {
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

    public FullMatrix(Vector other) {
        this(List.of(other));
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
    public FullMatrix transpose() {
        final FullMatrix ret = new FullMatrix(m, n);
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                ret.set(j, i, get(i, j));
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

    @Override
    public Vector mul(final Vector v) {
        if (v.size() != m) {
            throw new MatrixException("Incompatible matrices");
        }
        final Vector result = new Vector(n);
        for (int i = 0; i < n; ++i) {
            double value = 0;
            for (int j = 0; j < m; ++j) {
                value += get(i, j) * v.get(j);
            }
            result.set(i, value);
        }
        return result;
    }

    public void swapRows(final int i, final int j) {
        final Vector temp = data.get(i);
        data.set(i, data.get(j));
        data.set(j, temp);
    }

    public static FullMatrix E(int n) {
        FullMatrix matrix = new FullMatrix(n, n);
        for (int i = 0; i < n; i++) {
            matrix.set(i, i, 1);
        }
        return matrix;
    }
}
