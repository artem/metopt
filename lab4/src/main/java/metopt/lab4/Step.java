package metopt.lab4;

import metopt.lab4.matrices.Vector;

public class Step {
    private final int index;
    private final double a;
    private final Vector x;
    private final Vector p;
    private final double value;

    public Step(final int index, final double a, final Vector x, final Vector p, final double value) {
        this.index = index;
        this.a = a;
        this.x = x;
        this.p = p;
        this.value = value;
    }

    public double getA() {
        return a;
    }

    public Vector getX() {
        return x;
    }

    public Vector getP() {
        return p;
    }

    public double getValue() {
        return value;
    }

    public String texView(final boolean inDetail) {
        return inDetail ? String.format("%d & %.7f & %s & %s & %.7f", index, a, x.texView(), p.texView(), value)
                        : String.format("%d & %s & %.7f", index, x.texView(), value);
    }
}
