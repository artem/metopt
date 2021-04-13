public abstract class AbstractFunction {
    Matrix A;
    Matrix b; // vector
    double c;
    Matrix start;
    Matrix end;

    double eval(Matrix x) throws MatrixException {
        return Matrix.scalar(Matrix.mul(A, x), x)/2 + Matrix.scalar(b, x) + c;
    }

    Matrix gradient(Matrix x) throws MatrixException {
        return Matrix.sum(Matrix.mul(A, x), b);
    }
}
