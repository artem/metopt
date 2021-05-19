import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.stream.IntStream;

public abstract class Matrix {
    abstract int getSize();
    abstract double get(int i, int j);
    abstract void set(int i, int j, double value);
    abstract Matrix transpose();

    public Matrix add(Matrix other) {
        int size = getSize();
        if (size != other.getSize()) {
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

    public Matrix T() throws MatrixException {
        try {

            Class<? extends Matrix> clazz = this.getClass();
            Constructor<? extends Matrix> constructor = clazz.getConstructor(Matrix.class);
            Matrix res = constructor.newInstance(this);
            res.transpose();
            return res;
        } catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new MatrixException(e.getMessage());
        }
    }

    public Matrix addBy(Matrix other) {
        int size = getSize();
        if (size != other.getSize()) {
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
        StringBuilder result = new StringBuilder(String.format("Matrix %dx%d\n", getSize(), getSize()));
        for (int i = 0; i < getSize(); ++i) {
            for (int j = 0; j < getSize(); j++) {
                result.append(get(i, j)).append(" ");
            }
            result.append("\n");
        }
        return result.toString();
    }
}
