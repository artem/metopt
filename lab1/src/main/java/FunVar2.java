public class FunVar2 extends AbstractFunction {
    public FunVar2() {
        super(-1, 1);
    }

    @Override
    protected double evalImpl(double x) {
        return x*x*x*x - 3 * Math.atan(x) / 2;
    }
}
