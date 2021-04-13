public abstract class AbstractFunction {
    final public Matrix A;
    final public Matrix b; // vector
    final public double c;
    final public Matrix start;
    final public Matrix end;

    AbstractFunction() {
        A = null;
        b = null;
        c = 0;
        start = null;
        end = null;
    }

    public AbstractFunction(final Matrix a, final Matrix b, final double c, final Matrix start, final Matrix end) {
        A = a;
        this.b = b;
        this.c = c;
        this.start = start;
        this.end = end;
    }

    double eval(final Matrix m) throws MatrixException {
        return Matrix.scalar(Matrix.mul(A, m), m)/2 + Matrix.scalar(b, m) + c;
    }

    Matrix gradient(final Matrix m) throws MatrixException {
        return Matrix.sum(Matrix.mul(A, m), b);
    }
}
