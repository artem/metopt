public abstract class AbstractFunction {
    Matrix A;
    Matrix b; // vector
    double c;

    double eval(Matrix x) throws MatrixException {
        return Matrix.scalar(Matrix.mul(A, x), x) + Matrix.scalar(b, x) + c;
    }

    Matrix gradient(Matrix x) throws MatrixException {
        return Matrix.sum(Matrix.mul(A, x), b);
    }
}
