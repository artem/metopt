package metopt.lab4;

import metopt.lab4.matrices.Vector;

import java.util.ArrayList;
import java.util.List;

public class Result {
    public Vector x;
    public final List<Vector> list;
    public final List<Double> additional;
    public int iterations;
    private final List<Step> steps = new ArrayList<>();

    public Result() {
        list = new ArrayList<>();
        additional = new ArrayList<>();
    }

    public Vector getX() {
        return x;
    }

    public void addPoint(Vector v) {
        list.add(new Vector(v));
    }

    public void addStep(final int index, final double a, final Vector x, final Vector p, final double value) {
        addStep(new Step(index, a, new Vector(x), p, value));
    }

    public void addStep(final Step step) {
        steps.add(step);
    }

    public List<Step> getSteps() {
        return steps;
    }
}
