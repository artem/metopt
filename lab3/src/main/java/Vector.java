import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.DoubleStream;

public class Vector {
    private final double[] data;

    public Vector(final int n) {
        data = new double[n];
    }

    public Vector(final Vector v) {
        this(v.data);
    }

    public Vector(final List<Double> d) {
        this(d.stream().mapToDouble(Double::doubleValue).toArray());
    }

    public Vector(final double[] d) {
        data = Arrays.copyOf(d, d.length);
    }

    public int size() {
        return data.length;
    }

    public double get(final int index) {
        return data[index];
    }

    public void set(final int index, final double value) {
        data[index] = value;
    }

    private Vector binOp(final BinaryOperator<Double> op, final Vector v) {
        if (size() != v.size()) {
            throw new IllegalArgumentException("size does not match");
        }

        Vector ret = new Vector(size());

        for (int i = 0; i < size(); i++) {
            ret.set(i, op.apply(get(i), v.get(i)));
        }
        return ret;
    }

    public Vector add(final Vector v) {
        return binOp(Double::sum, v);
    }

    public Vector sub(final Vector v) {
        return binOp((a, b) -> a - b, v);
    }

    public double dotprod(final Vector v) {
        final Vector temp = binOp((a, b) -> a * b, v);
        return DoubleStream.of(temp.data).sum();
    }

    public Vector mul(final double k) {
        Vector ret = new Vector(size());

        for (int i = 0; i < size(); i++) {
            ret.set(i, get(i) * k);
        }
        return ret;
    }

    public double norm() {
        return Math.sqrt(dotprod(this));
    }
}
