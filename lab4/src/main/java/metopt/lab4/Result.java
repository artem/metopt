package metopt.lab4;

import metopt.lab4.matrices.Vector;

import java.util.ArrayList;
import java.util.List;

public class Result {
    public Vector x;
    public final List<Vector> list;
    public final List<Double> additional;
    public int iterations;


    public Result() {
        list = new ArrayList<>();
        additional = new ArrayList<>();
    }

    public void addStep(Vector v) {
        list.add(new Vector(v));
    }
}
