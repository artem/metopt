public class ConjugateGradient {
    private final AbstractFunction f;

    public ConjugateGradient(AbstractFunction f) {
        this.f = f;
    }

    public ConjugateGradient(AbstractFunction f, double eps) {
        this(f);
    }

    public Trace process() throws MatrixException {
        final Trace result = new Trace(f);

        Matrix x = f.x0;
        Matrix grad = f.gradient(x);
        Matrix p = grad.invert();
        double gradLen = grad.len();
        double gradLenPrev;

        result.add(x);

        for (int i = 0; i < x.m; ++i) {
            final Matrix Ap = Matrix.mul(f.A, p);
            final double a = gradLen * gradLen / Matrix.scalar(Ap, p);
            x = Matrix.sum(x, Matrix.mul(p, a));
            grad = Matrix.sum(grad, Matrix.mul(Ap, a));
            gradLenPrev = gradLen;
            gradLen = grad.len();
            double b = gradLen / gradLenPrev;
            b *= b;
            p = Matrix.sum(grad.invert(), Matrix.mul(p, b));

            result.add(x);
        }

        return result;
    }
}