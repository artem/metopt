package metopt.lab4.matrices;

import java.util.stream.IntStream;

public abstract class Matrix {
    public int n;
    public int m;

    abstract public int size();
    abstract public double get(int i, int j);
    abstract public void set(int i, int j, double value);
    abstract public Matrix transpose();

    public Matrix add(Matrix other) {
        int size = size();
        if (size != other.size()) {
            throw new IllegalArgumentException("Incompatible matrices.");
        }
        FullMatrix result = new FullMatrix(size, size);
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                result.set(i, j, get(i, j) + other.get(i, j));
            }
        }
        return result;
    }

    public FullMatrix add(final double k) {
        FullMatrix ret = new FullMatrix(this);
        for (int i = 0; i < ret.n; ++i) {
            for (int j = 0; j < ret.m; ++j) {
                ret.set(i, j, get(i, j) + k);
            }
        }
        return ret;
    }

    public Matrix addBy(Matrix other) {
        int size = size();
        if (size != other.size()) {
            throw new IllegalArgumentException("Incompatible matrices.");
        }
        IntStream.range(0, size)
                .forEach(i -> IntStream.range(0, size)
                        .filter(j -> other.get(i, j) != 0)
                        .forEach(j -> set(i, j, get(i, j) + other.get(i, j))));
        return this;
    }

    public FullMatrix mul(Matrix other) {
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

    public Matrix negBy() {
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                set(i, j, -get(i, j));
        return this;
    }

    public Vector mul(final Vector v) {
        final int n = size();
        if (v.size() != n) {
            throw new MatrixException("Incompatible matrices");
        }
        final Vector result = new Vector(n);
        for (int i = 0; i < n; ++i) {
            double value = 0;
            for (int j = 0; j < n; ++j) {
                value += get(i, j) * v.get(j);
            }
            result.set(i, value);
        }
        return result;
    }

    public FullMatrix mul(final double k) {
        FullMatrix ret = new FullMatrix(this);
        for (int i = 0; i < ret.n; ++i) {
            for (int j = 0; j < ret.m; ++j) {
                ret.set(i, j, get(i, j) * k);
            }
        }
        return ret;
    }

    public Matrix hadamard(Matrix other) {
        return new FullMatrix(this).hadamardBy(other);
    }

    public Matrix hadamardBy(Matrix other) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                set(i, j, get(i, j) * other.get(i, j));
            }
        }
        return this;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(String.format("metopt.lab4.matrices.Matrix %dx%d\n", size(), size()));
        for (int i = 0; i < size(); ++i) {
            for (int j = 0; j < size(); j++) {
                result.append(get(i, j)).append(" ");
            }
            result.append("\n");
        }
        return result.toString();
    }

}
