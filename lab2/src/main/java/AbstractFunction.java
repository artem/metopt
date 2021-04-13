public abstract class AbstractFunction {
    public Matrix A;
    public Matrix b; // vector
    public double c;
    public Matrix start;
    public Matrix end;

    public AbstractFunction() {
    }

    double eval(Matrix m) throws MatrixException {
        return Matrix.scalar(Matrix.mul(A, m), m)/2 + Matrix.scalar(b, m) + c;
    }

    Matrix gradient(Matrix m) throws MatrixException {
        return Matrix.sum(Matrix.mul(A, m), b);
    }
}
