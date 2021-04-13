public class ConjugateGradient {
    private final AbstractFunction f;
    private final double eps;

    public ConjugateGradient(AbstractFunction f, double eps) {
        this.f = f;
        this.eps = eps;
    }

    public Matrix process() throws MatrixException {
        Matrix x = Matrix.mul(Matrix.sum(f.start, f.end), 0.5);
        Matrix grad = f.gradient(x);
        Matrix p = grad.invert();
        for (int i = 0; i < x.m; ++i) {
            double denominator = Matrix.scalar(Matrix.mul(f.A, p), p);
            double a = -Matrix.scalar(grad, p) / denominator;
            x = Matrix.sum(x, Matrix.mul(p, a));
            grad = f.gradient(x);
            double b = Matrix.scalar(Matrix.mul(f.A, grad), p) / denominator;
            p = Matrix.sum(grad.invert(), Matrix.mul(p, b));
        }
        return x;
    }

    public static void main(String[] args) {
        try {
            System.out.println(new ConjugateGradient(new Function2(), 1e-5).process());
        } catch (MatrixException e) {
            e.printStackTrace();
        }
    }
}