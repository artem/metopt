public class FunPolynomial extends AbstractFunction {
    FunPolynomial() {
        super(-1.5, 2);
    }

    @Override
    protected double evalImpl(double x) {
        return 1 + x * x * (-7 + x * (0.5 + 3.0 * x));
    }
}
