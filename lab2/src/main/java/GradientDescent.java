public class GradientDescent {

    AbstractFunction fn;
    double gradEps;
    double valueEps;
    double stepEps;

    public GradientDescent(AbstractFunction fn, double gradEps, double valueEps, double stepEps) {
        this.fn = fn;
        this.gradEps = gradEps;
        this.valueEps = valueEps;
        this.stepEps = stepEps;
    }

    // не тестировал
    public double process(double lambda, boolean normalGrad) throws MatrixException {
        Matrix x = Matrix.mul(Matrix.sum(fn.start, fn.end), .5);
        while (true) {
            Matrix gradient = fn.gradient(x);
            double fx = fn.eval(x);
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
    }

}
