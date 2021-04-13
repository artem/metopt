import static java.lang.Double.NaN;

public class GradientDescent {

    AbstractFunction fn;
    double gradEps;

    public GradientDescent(AbstractFunction fn, double gradEps) {
        this.fn = fn;
        this.gradEps = gradEps;
    }

    // не тестировал
    public double process(double lambda, boolean normalGrad) throws MatrixException {
        Matrix x = Matrix.mul(Matrix.sum(fn.start, fn.end), .5);
        double fx = fn.eval(x);
        for (int i = 0; i < 3000; i++) {
            Matrix gradient = fn.gradient(x);
            if (gradient.len() < gradEps) {
                return fx;
            }
            if (normalGrad) // замечание к алгоритму, как возможный вариант.
                gradient = Matrix.mul(gradient, 1/gradient.len());
            while (true) {
                Matrix y = Matrix.sum(x, Matrix.mul(gradient, -lambda));
                double fy = fn.eval(y);
                if (fy < fx) {
                    x = y;
                    fx = fy;
                    break;
                } else {
                    lambda /= 2;
                }
            }
        }
        return NaN;
    }
}
