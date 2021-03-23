public class FunPolynomial extends AbstractFunction {
    FunPolynomial() {
        super(1, 6);
    }

    @Override
    protected double evalImpl(double x) {
        return ((((x - 5) * x - 4) * x - 1) * x + 5) * x;
    }
}
