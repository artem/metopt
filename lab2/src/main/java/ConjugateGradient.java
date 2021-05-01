public class ConjugateGradient implements Method {
    private final AbstractFunction f;
    private final double eps;

    public ConjugateGradient(AbstractFunction f) {
        this(f, 1e-5);
    }

    public ConjugateGradient(AbstractFunction f, double eps) {
        this.f = f;
        this.eps = eps;
    }

    public Trace process() throws MatrixException {
        final Trace result = new Trace(f);

        Matrix x = new Matrix(f.x0);
        Matrix grad = f.gradient(x);
        Matrix p = grad.negated();
        double gradLen = grad.len();
        double gradLenPrev;

        result.add(new Matrix(x));

        while (p.len() > eps) {
            final Matrix Ap = f.A.mul(p);
            final double a = gradLen * gradLen / Ap.scalar(p);
            x.addBy(p.mul(a));
            grad.addBy(Ap.mul(a));
            gradLenPrev = gradLen;
            gradLen = grad.len();
            double b = gradLen / gradLenPrev;
            b *= b;
            p = grad.negated().addBy(p.mul(b));

            result.add(new Matrix(x));
        }

        return result;
    }
}