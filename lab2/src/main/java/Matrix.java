import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Matrix {
    public int m; // height, rows, y
    public int n; // width, cols, x
    private List<ArrayList<Double>> data;

    Matrix() {
        n = 0;
        m = 0;
        data = new ArrayList<>();
    }

    Matrix(List<Double> vector, boolean vertical) {
        data = new ArrayList<>();
        n = vector.size();
        m = n > 0 ? 1 : 0;
        data.add(new ArrayList<>(vector));
        if (vertical) {
            transpose();
        }
    }

    Matrix(List<List<Double>> data) {
        this.data = new ArrayList<>();
        for (List<Double> sublist : data) {
            this.data.add(new ArrayList<>(sublist));
        }
        m = data.size();
        n = data.size() > 0 ? data.get(0).size() : 0;
    }

    Matrix(int m, int n) {
        data = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            data.add(IntStream.range(0, n).mapToObj(x -> 0.).collect(Collectors.toCollection(ArrayList::new)));
        }
        this.m = m;
        this.n = n;
    }

    public static double scalar(Matrix m1, Matrix m2) throws MatrixException {
        if ((m1.m != 1 || m2.m != 1 || m1.n != m2.n) && (m1.n != 1 || m2.n != 1 || m1.m != m2.m)) {
            throw new MatrixException("wrong matrix sizes");
        }
        double res = 0;
        if (m1.m == 1) {
            for (int i = 0; i < m1.n; i++)
                res += m1.get(0, i) * m2.get(0, i);
        } else {
            for (int i = 0; i < m1.m; i++)
                res += m1.get(i, 0) * m2.get(i, 0);
        }
        return res;
    }

    public static Matrix mul(Matrix m1, Matrix m2) throws MatrixException {
        if (m1.n != m2.m) {
            throw new MatrixException(String.format("wrong matrix sizes: m1.n (%d) != m2.m (%d)", m1.n, m2.m));
        }
        Matrix result = new Matrix(m1.m, m2.n);
        for (int i = 0; i < m1.m; ++i)
            for (int j = 0; j < m2.n; ++j)
                for (int k = 0; k < m1.n; ++k)
                    result.addTo(i, j, m1.get(i, k) * m2.get(k, j));
        return result;
    }

    public static Matrix mul(Matrix m1, double v) {
        Matrix result = new Matrix(m1.m, m1.n);
        for (int i = 0; i < m1.m; ++i)
            for (int j = 0; j < m1.n; ++j)
                result.set(i, j, m1.get(i, j) * v);
        return result;
    }

    public static Matrix sum(Matrix m1, Matrix m2) throws MatrixException {
        if (m1.m != m2.m || m1.n != m2.n) {
            throw new MatrixException("Matrices of different sizes");
        }
        Matrix result = new Matrix(m1.m, m1.n);
        for (int i = 0; i < m1.m; ++i)
            for (int j = 0; j < m1.n; ++j)
                result.set(i, j, m1.get(i, j) + m2.get(i, j));
        return result;
    }

    public Matrix invert() {
        Matrix result = new Matrix(m, n);
        for (int i = 0; i < m; ++i)
            for (int j = 0; j < n; ++j)
                result.set(i, j, -this.get(i, j));
        return result;
    }

    public double len() throws MatrixException {
        if (m != 1 && n != 1) {
            throw new MatrixException("Not a vector");
        }
        return Math.sqrt((m == 1 ? data : T().data).get(0).stream().map(x -> x * x).reduce(.0, Double::sum));
    }

    public Double get(int y, int x) {
        return data.get(y).get(x);
    }

    public void set(int y, int x, double value) {
        data.get(y).set(x, value);
    }

    public void addTo(int y, int x, double value) {
        data.get(y).set(x, data.get(y).get(x) + value);
    }

    public Matrix T() {
        Matrix matrix = new Matrix(n, m);
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                matrix.set(i, j, get(j, i));
        return matrix;
    }

    public void transpose() {
        data = T().data;
        int t = m;
        m = n;
        n = t;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Matrix: m=").append(m).append(", n=").append(n).append(System.lineSeparator());
        for (int i = 0; i < m; i++) {
            sb.append(data.get(i).stream().map(x -> Double.toString(x)).collect(Collectors.joining(", ")));
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        // test
        List<List<Double>> l1 = Arrays.asList(
                Arrays.asList(1., 2., 3.),
                Arrays.asList(1., 2., 3.),
                Arrays.asList(1., 2., 3.),
                Arrays.asList(1., 2., 3.)
        );
        List<List<Double>> l2 = Arrays.asList(
                Arrays.asList(4., 2., 3., 8.),
                Arrays.asList(1., 5., 3., 9.),
                Arrays.asList(1., 2., 6., 1.)
        );
        Matrix a = new Matrix(l1);
        Matrix b = new Matrix(l2);
        System.out.println(a.toString());
        System.out.println(b.toString());
        try {
            System.out.println(Matrix.mul(a, b).toString());
        } catch (MatrixException e) {
            e.printStackTrace();
        }
    }
}
