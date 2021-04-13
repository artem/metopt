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
        while (true) {
            Matrix gradient = fn.gradient(x);
            System.out.println("gradient, len = " + gradient.len());
            System.out.println(gradient);
            double fx = fn.eval(x);
            System.out.println("f(x) " + fx);
            if (gradient.len() < gradEps) {
                return fx;
            }
            if (normalGrad) // замечание к алгоритму, как возможный вариант.
                gradient = Matrix.mul(gradient, 1/gradient.len());
            while (true) {
                Matrix y = Matrix.sum(x, Matrix.mul(gradient, -lambda));
                System.out.println("y:");
                System.out.println(y);
                double fy = fn.eval(y);
                System.out.println("f(y):");
                System.out.println(fy);
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
