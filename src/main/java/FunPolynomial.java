public class FunPolynomial extends AbstractFunction {
    FunPolynomial() {
        super(1, 6);
    }

    @Override
    protected double evalImpl(double x) {
        return ((((x - 5) * x - 4) * x - 1) * x + 5) * x;
//        double x2 = x * x;
//        double x3 = x2 * x;
//        double x4 = x2 * x2;
//        double x5 = x4 * x;
//        return x5 - 5 * x4 - 4 * x3 - x2 + 5 * x;
    }
}
