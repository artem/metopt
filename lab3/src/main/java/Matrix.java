import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.stream.IntStream;

public abstract class Matrix {
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

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(String.format("Matrix %dx%d\n", size(), size()));
        for (int i = 0; i < size(); ++i) {
            for (int j = 0; j < size(); j++) {
                result.append(get(i, j)).append(" ");
            }
            result.append("\n");
        }
        return result.toString();
    }
}
